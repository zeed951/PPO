package Route_GPX;

public class AddPointCmd implements Command {
    private int routeInd;
    private int pointInd;
    private double lat;
    private double lon;
    private double alt;

    public AddPointCmd(int routeInd, int pointInd, double lat, double lon, double alt) {
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
        route.addPoint(pointInd, lat, lon, alt);
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
        route.deletePoint(pointInd);
        route.setLength(route.culcDistance());
        return true;
    }
}
