package Route_GPX;

public class EditPointCmd implements Command {
    private int routeInd;
    private int pointInd;
    private double lat;
    private double lon;
    private double alt;

    public EditPointCmd(int routeInd, int pointInd, double lat, double lon, double alt) {
        this.routeInd = routeInd;
        this.pointInd = pointInd;
        this.lat = lat;
        this.lon = lon;
        this.alt = alt;
    }

    @Override
    public boolean execute() {
        MainList mainListObj = MainList.getInstance();
        Route route = mainListObj.get(routeInd);
        if (route == null) {
            return false;
        }
        Point point = route.getPoint(pointInd);
        if (point == null) {
            return false;
        }
        double prevLat = point.getLat();
        double prevLon = point.getLon();
        double prevAlt = point.getAlt();
        route.editPoint(pointInd, this.lat, this.lon, this.alt);
        this.lat = prevLat;
        this.lon = prevLon;
        this.alt = prevAlt;
        route.setLength(route.culcDistance());
        return true;
    }

    @Override
    public boolean cancel() {
        return execute();
    }
}
