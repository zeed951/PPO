package Route_GPX;

import org.junit.Test;

import static org.junit.Assert.*;

public class MainListTest {

    @Test
    public void delByIndex() {
        MainList mainListObj = MainList.getInstance();
        Route route1 = new Route("test1");
        Route route2 = new Route("test2");
        Route route3 = new Route("test3");

        mainListObj.addRoute(0, route1);
        mainListObj.addRoute(1, route2);
        mainListObj.addRoute(2, route3);

        mainListObj.del(1);

        assertTrue(mainListObj.get(1).getName() == "test3");
    }

    @Test
    public void de1ByName() {
        MainList mainListObj = MainList.getInstance();
        Route route1 = new Route("test1");
        Route route2 = new Route("test2");
        Route route3 = new Route("test3");

        mainListObj.addRoute(0, route1);
        mainListObj.addRoute(1, route2);
        mainListObj.addRoute(2, route3);

        mainListObj.del("test2");

        assertTrue(mainListObj.get(1).getName() == "test3");
    }

    @Test
    public void addRoute() {
        MainList mainListObj = MainList.getInstance();
        mainListObj.addRoute("test_01");
        mainListObj.addRoute("test_02");

        assertTrue(mainListObj.get(1).getName() == "test_02");
    }
}