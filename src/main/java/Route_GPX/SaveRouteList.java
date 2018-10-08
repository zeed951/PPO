package Route_GPX;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SaveRouteList implements Serializable{
    private List<Route> routeList = new LinkedList<>();
    public List<Route> getRouteList() {
        return routeList;
    }

    public void setRouteList(List<Route> routeList) {
        this.routeList = routeList;
    }

    public void detachMngr() {
        for (Route route : routeList) {
            route.setCmdMngr(null);
        }
    }
}
