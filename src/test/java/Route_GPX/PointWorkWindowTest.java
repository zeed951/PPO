package Route_GPX;

import static org.junit.Assert.*;

public class PointWorkWindowTest {

    @org.junit.Test
    public void createNewRoute(){
        PointWorkWindow window = new PointWorkWindow();

        window.getNameField().setText("test_01");
        window.getCreateRouteButton().doClick();

        MainList mainListObj = MainList.getInstance();

        Route route = mainListObj.get("test_01");

        assertTrue(route != null);

        mainListObj.del("test_01");
    }

    @org.junit.Test
    public void addPoint(){
        PointWorkWindow window = new PointWorkWindow();

        window.getNameField().setText("test_01");
        window.getCreateRouteButton().doClick();
        window.getRouteField().setSelectedIndex(0);
        window.getLatField().setText("1");
        window.getLonField().setText("2");
        window.getAltField().setText("3");
        window.getAddPointButton().doClick();

        MainList mainListObj = MainList.getInstance();
        Route route = mainListObj.get("test_01");

        Point point = route.getPointList().get(0);

        assertTrue(point.getLat() == 1);
        assertTrue(point.getLon() == 2);
        assertTrue(point.getAlt() == 3);

        mainListObj.del("test_01");
    }

    @org.junit.Test
    public void delPoint(){
        PointWorkWindow window = new PointWorkWindow();

        window.getNameField().setText("test_01");
        window.getCreateRouteButton().doClick();
        window.getRouteField().setSelectedIndex(0);
        window.getLatField().setText("1");
        window.getLonField().setText("1");
        window.getAltField().setText("1");
        window.getAddPointButton().doClick();

        window.getPointField().setSelectedIndex(0);
        window.getDeleteButton().doClick();

        MainList mainListObj = MainList.getInstance();
        Route route = mainListObj.get("test_01");

        assertTrue(route.getPointList().isEmpty());

        mainListObj.del("test_01");
    }

    @org.junit.Test
    public void editPoint(){
        PointWorkWindow window = new PointWorkWindow();

        window.getNameField().setText("test_01");
        window.getCreateRouteButton().doClick();
        window.getRouteField().setSelectedIndex(0);
        window.getLatField().setText("1");
        window.getLonField().setText("2");
        window.getAltField().setText("3");
        window.getAddPointButton().doClick();

        window.getLatField().setText("4");
        window.getLonField().setText("5");
        window.getAltField().setText("6");
        window.getPointField().setSelectedIndex(0);
        window.getEditButton().doClick();

        MainList mainListObj = MainList.getInstance();
        Route route = mainListObj.get("test_01");

        Point point = route.getPointList().get(0);

        assertTrue(point.getLat() == 4);
        assertTrue(point.getLon() == 5);
        assertTrue(point.getAlt() == 6);

        mainListObj.del("test_01");
    }

}