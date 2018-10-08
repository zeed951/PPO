package Route_GPX;

public class DelPointCmd implements Command {
    private int routeInd;
    private int pointInd;
    private double lat;
    private double lon;
    private double alt;

    public DelPointCmd(int routeInd, int pointInd) {
        MainList mainListObj = MainList.getInstance();
        Route route = mainListObj.get(routeInd);
        Point point = route.getPoint(pointInd);
        this.routeInd = routeInd;
        this.pointInd = pointInd;
        this.lat = point.getLat();
        this.lon = point.getLon();
        this.alt = point.getAlt();
    }

    @Override
    public boolean execute() {
        MainList mainListObj = MainList.getInstance();
        Route route = mainListObj.get(routeInd);
        if (route == null) {
            return false;
        }
        route.deletePoint(pointInd);
        route.setLength(route.culcDistance());
        return true;
    }

    @Override
    public boolean cancel() {
        MainList mainListObj = MainList.getInstance();
        Route route = mainListObj.get(routeInd);
        if (route == null) {
            return false;
        }
        route.addPoint(pointInd, lat, lon, alt);
        route.setLength(route.culcDistance());
        return true;
    }
}
