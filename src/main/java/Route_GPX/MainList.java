package Route_GPX;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.*;

// Singleton
public class MainList{
    private static MainList instance = new MainList();
    private List<Route> routeList = new LinkedList<>();
    private CmdManager cmdMngr = new CmdManager();

    public List<Route> getRouteList() {
        return routeList;
    }

    public void setRouteList(List<Route> routeList) {
        this.routeList = routeList;
    }

    public CmdManager getCmdMngr() {
        return cmdMngr;
    }

    public void setCmdMngr(CmdManager cmdMngr) {
        this.cmdMngr = cmdMngr;
    }

    private MainList() {}

    public static MainList getInstance() {
        return instance;
    }

    public void importRoutes(List<Route> routeList) {
        this.routeList = routeList;
        for (Route route : this.routeList) {
            route.setCmdMngr(new CmdManager());
        }
    }

    public boolean contains(String name) {
        for (Route route : routeList) {
            if (route.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public Route addRoute(String name) {
        Route route = new Route(name);
        route.setDate(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime()));
        if (routeList.add(route)) {
            return route;
        }
        return null;
    }

    public boolean addRoute(int index, Route route) {
        if (index < 0 || index > routeList.size()) {
            return false;
        }
        routeList.add(index, route);
        return true;
    }


    public boolean del(int index) {
        if (index < 0 || index > routeList.size()) {
            return false;
        }
        routeList.remove(index);

        return true;
    }

    public boolean del(String name) {
        for (Route route : routeList) {
            if (route.getName().equals(name)) {
                routeList.remove(route);
                return true;
            }
        }
        return false;
    }

    public DefaultListModel printRoutes() {
        DefaultListModel listModel = new DefaultListModel();
        StringBuffer output = new StringBuffer();

        if (routeList.isEmpty()) {
            listModel.addElement("");
            return listModel;
        }
        else {
            int i = 1;
            for (Route route : routeList) {
                output.append(i + ".  ");
                output.append(route.getName());
                output.append("    ");
                output.append((float) route.getLength());
                output.append("    ");
                output.append(route.getDate());
                listModel.addElement(output.toString());
                output.delete(0, output.length());
                i++;
            }
        }

        return listModel;
    }

    public Route get(int index) {
        if ((routeList.size() < index) || (index < 0)) {
            return null;
        }
        return routeList.get(index);
    }

    public Route get(String name) {
        for (Route route : routeList) {
            if (route.getName().equals(name)) {
                return route;
            }
        }
        return null;
    }
}
