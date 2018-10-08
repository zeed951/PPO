package Route_GPX;

import java.io.Serializable;

public class Point implements Serializable{
    private double lat;
    private double lon;
    private double alt;

    public double getAlt() {
        return alt;
    }

    public void setAlt(double alt) {
        this.alt = alt;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public Point(double lat, double lon, double alt) {
        this.lat = lat;
        this.lon = lon;
        this.alt = alt;
    }

    public Point(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
        this.alt = 0;
    }

    public int getLatitudeE6() {
        return (int)(this.lat * 1000000);
    }

    public int getLongitudeE6() {
        return (int)(this.lon * 1000000);
    }
}
