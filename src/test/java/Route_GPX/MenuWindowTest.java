package Route_GPX;

import org.junit.Test;

import static org.junit.Assert.*;

public class MenuWindowTest {

    @Test
    public void deserialize() {
        new MenuWindow();
        MainList mainListObj = MainList.getInstance();

        int len = mainListObj.getRouteList().size();

        assertTrue(len > 0);
    }
}