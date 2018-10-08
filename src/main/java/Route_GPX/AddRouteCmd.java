package Route_GPX;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddRouteCmd implements Command {
    private int routeInd;
    private Route route;

    public AddRouteCmd(int routeInd, String name) {
        this.routeInd = routeInd;
        this.route = new Route(name);
        this.route.setDate(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime()));
    }

    @Override
    public boolean execute() {
        MainList mainListObj = MainList.getInstance();
        return mainListObj.addRoute(routeInd, route);
    }

    @Override
    public boolean cancel() {
        MainList mainListObj = MainList.getInstance();
        return mainListObj.del(routeInd);
    }
}
