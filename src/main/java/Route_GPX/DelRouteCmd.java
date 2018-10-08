package Route_GPX;

public class DelRouteCmd implements Command{
    private int routeInd;
    private Route route;

    public DelRouteCmd(int routeInd) {
        MainList mainListObj = MainList.getInstance();
        this.routeInd = routeInd;
        this.route = mainListObj.get(routeInd);
    }

    @Override
    public boolean execute() {
        MainList mainListObj = MainList.getInstance();
        return mainListObj.del(routeInd);
    }

    @Override
    public boolean cancel() {
        MainList mainListObj = MainList.getInstance();
        return mainListObj.addRoute(routeInd, route);
    }
}
