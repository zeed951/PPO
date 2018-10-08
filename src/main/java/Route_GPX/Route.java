package Route_GPX;

import javax.swing.*;
import java.io.Serializable;
import java.util.ArrayList;

public class Route implements Serializable {
    private String name;
    private double length;
    private String date;
    private ArrayList<Point> pointList = new ArrayList<Point>();
    private CmdManager cmdMngr = new CmdManager();

    public boolean addPoint(int index, double lat, double lon, double alt) {
        if ((index >= 0) && (index <= pointList.size())) {
            Point point = new Point(lat, lon, alt);
            pointList.add(index, point);
            return true;
        }
        return false;
    }

    public boolean addPoint(int index, double lat, double lon) {
        if ((index >= 0) && (index <= pointList.size())) {
            Point point = new Point(lat, lon);
            pointList.add(index, point);
            return true;
        }
        return false;
    }

    public Point getPoint(int index) {
        if (index < 0 || index > pointList.size() - 1) {
            return null;
        }
        return pointList.get(index);
    }

    //формулa Хаверсина.
    public final static double AVERAGE_RADIUS_OF_EARTH_KM = 6371;
    public int calcDistKM(double userLat, double userLng,
                          double venueLat, double venueLng) {

        double latDistance = Math.toRadians(userLat - venueLat);
        double lngDistance = Math.toRadians(userLng - venueLng);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(userLat)) * Math.cos(Math.toRadians(venueLat))
                * Math.sin(lngDistance / 2) * Math.sin(lngDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return (int) (Math.round(AVERAGE_RADIUS_OF_EARTH_KM * c));
    }

    public double culcDistance() {
        double distance = 0;
        if (pointList.size() == 0) {
            return 0;
        }
        Point prevPoint = pointList.get(0);
        Point curPoint;

        for (int i = 1; i < pointList.size(); i++) {
            curPoint = pointList.get(i);
            distance += Math.sqrt(Math.pow(calcDistKM(prevPoint.getLat(), prevPoint.getLon(),
                    curPoint.getLat(), curPoint.getLon()), 2) +
                                      Math.pow((curPoint.getAlt() - prevPoint.getAlt()), 2));
            prevPoint = curPoint;
        }

        return distance;
    }

    public DefaultListModel printPoints() {
        DefaultListModel listModel = new DefaultListModel();
        StringBuffer output = new StringBuffer();

        if (pointList.isEmpty()) {
            listModel.addElement("");
            return listModel;
        }

        int i = 1;
        for (Point point : pointList) {
            output.append(i);
            i++;
            output.append(".   lat: ");
            output.append(point.getLat());
            output.append("    lon: ");
            output.append(point.getLon());
            output.append("    alt: ");
            output.append(point.getAlt());
            listModel.addElement(output.toString());
            output.delete(0, output.length());
        }

        return listModel;
    }

    public boolean deletePoint(int index) {
        if ((index >= 0) && (index < pointList.size())) {
            pointList.remove(index);
            return true;
        }
        return false;
    }

    public int pointListSize() {
        return pointList.size();
    }

    public void editPoint(int index, double lat, double lon, double alt) {
        Point point = pointList.get(index);
        point.setLat(lat);
        point.setLon(lon);
        point.setAlt(alt);
    }

    public String getPolyline(){
        return GooglePolyline.encode(pointList, 10);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<Point> getPointList() {
        return pointList;
    }

    public void setPointList(ArrayList<Point> pointList) {
        this.pointList = pointList;
    }

    public CmdManager getCmdMngr() {
        return cmdMngr;
    }

    public void setCmdMngr(CmdManager cmdMngr) {
        this.cmdMngr = cmdMngr;
    }

    public Route(String name) {
        this.name = name;
    }
}
