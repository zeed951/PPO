package Route_GPX;

import org.junit.Test;

import static org.junit.Assert.*;

public class RouteTest {

    @Test
    public void addPoint() {
        Route route = new Route("example");
        for (int i = 0; i < 100; i++) {
            route.addPoint(i, i, i, i);
        }

        assertEquals(100, route.getPointList().size());
    }

    @Test
    public void deletePoint() {
        Route route = new Route("example");
        for (int i = 0; i < 100; i++) {
            route.addPoint(i, i, i, i);
        }

        for (int i = 99; i >= 50; i--) {
            route.deletePoint(i);
        }

        assertEquals(50, route.getPointList().size());
    }

    @Test
    public void editPoint() {
        Route route = new Route("example");
        route.addPoint(0, 1, 2, 3);
        route.editPoint(0, 4, 5, 6);

        assertEquals(4, (int) route.getPointList().get(0).getLat());
        assertEquals(5, (int) route.getPointList().get(0).getLon());
        assertEquals(6, (int) route.getPointList().get(0).getAlt());
    }
}