package Route_GPX;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import static org.junit.Assert.*;

public class RouteWorkWindowTest{
    @org.junit.Test
    public void convertToPolyline() throws UnsupportedFlavorException, IOException{
        RouteWorkWindow window = new RouteWorkWindow();

        window.getImportField().setText("src/test/3.poly");
        window.getImportButton().doClick();
        window.getRouteField().setSelectedIndex(0);
        window.getGetPolylineButton().doClick();

        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        String actual = (String) clipboard.getContents(null).getTransferData(DataFlavor.stringFlavor);
        String expected = "_p~iF~ps|U_ulLnnqC_mqNvxq`@";

        assertEquals(expected, actual);

        MainList mainListObj = MainList.getInstance();
        mainListObj.del("src/test/3.poly");
    }

    @org.junit.Test
    public void importPolyline(){
        RouteWorkWindow window = new RouteWorkWindow();

        window.getImportField().setText("src/test/3.poly");
        window.getImportButton().doClick();

        MainList mainListObj = MainList.getInstance();

        Route route = mainListObj.get("3.poly");
        assertTrue(route != null);

        mainListObj.del("src/test/3.poly");
    }

    @org.junit.Test
    public void delRoute(){
        RouteWorkWindow window = new RouteWorkWindow();
        MainList mainListObj = MainList.getInstance();

        window.getImportField().setText("src/test/3.poly");
        window.getImportButton().doClick();
        window.getImportField().setText("src/test/33.poly");
        window.getImportButton().doClick();
        window.getRouteField().setSelectedIndex(30);
        window.getDeleteButton().doClick();

        Route route = mainListObj.get("src/test/3.poly");

        assertEquals(null, route);
    }

    @org.junit.Test
    public void plotAltMap(){
        PointWorkWindow window1 = new PointWorkWindow();
        window1.getNameField().setText("test_01");
        window1.getCreateRouteButton().doClick();

        RouteWorkWindow window = new RouteWorkWindow();
        window.getRouteField().setSelectedIndex(0);
        window.getDrawAltButton().doClick();
        String msg = window.getMsgArea().getText();

        assertTrue(msg.equals("Can't draw Altitude Map!\nTip: At least 2 points needed"));

        MainList mainListObj = MainList.getInstance();
        mainListObj.del("test_01");
    }
}