package Route_GPX;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class GooglePolylineTest {
    private double eps =  1E-6;

    @org.junit.Test
    public void encode() {
        String expected = "_p~iF~ps|U_ulLnnqC_mqNvxq`@";
        String actual;

        ArrayList<Point> pointList = new ArrayList<Point>();
        pointList.add(new Point(38.5, -120.2));
        pointList.add(new Point(40.7, -120.95));
        pointList.add(new Point(43.252, -126.453));

        actual = GooglePolyline.encode(pointList, 10);

        assertEquals(expected, actual);
    }

    @org.junit.Test
    public void decode() {
        ArrayList<Point> actual;
        ArrayList<Point> expected = new ArrayList<Point>();
        expected.add(new Point(38.5, -120.2));
        expected.add(new Point(40.7, -120.95));
        expected.add(new Point(43.252, -126.453));

        String polyline = "_p~iF~ps|U_ulLnnqC_mqNvxq`@";

        actual = GooglePolyline.decode(polyline, 1);

        for (int i = 0; i < expected.size(); i++) {
            assertTrue(Math.abs(expected.get(i).getLat() - actual.get(i).getLat()) < eps);
            assertTrue(Math.abs(expected.get(i).getLon() - actual.get(i).getLon()) < eps);
        }

    }

}