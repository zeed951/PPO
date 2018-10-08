package Route_GPX;

import javax.swing.*;

public class PointWorkPresenter {
    private PointWorkWindow window;  // Указатель на соответствующее окно
    private Route currRoute;
    private int routeIndex;

    PointWorkPresenter(PointWorkWindow window) {
        this.window = window;
    }

    public void resetRouteList() {
        MainList mainListObj = MainList.getInstance();
        window.showRoutes(mainListObj);
    }

    public void resetPointList() {
        window.showPoints(currRoute);
    }

    private Point pointCheckin() {
        String latText = window.getLatField().getText();
        String lonText = window.getLonField().getText();
        String altText = window.getAltField().getText();

        latText = latText.replaceAll("[^0-9]", "");
        lonText = lonText.replaceAll("[^0-9]", "");
        altText = altText.replaceAll("[^0-9-]", "");

        if ((latText.length() == 0) || (lonText.length() == 0) || (altText.length() == 0)) {
            window.getMsgArea().setText("Incorrect input!!!");
            window.getMsgArea().append("\nTip: -180 =< lat,lon =< 180");
            window.getMsgArea().append("\n     -10994 =< alt =< 8848");
            return null;
        }

        double lat = Double.parseDouble(latText);
        double lon = Double.parseDouble(lonText);
        double alt = Double.parseDouble(altText);

        if ((Math.abs(lat) > 180) || (Math.abs(lon) > 180)
                || (alt > 8848) || (alt < -10994)) {
            window.getMsgArea().setText("Incorrect input!!!");
            window.getMsgArea().append("\nTip: -180 =< lat,lon =< 180");
            window.getMsgArea().append("\n     -10994 =< alt =< 8848");
            return null;
        }

        return new Point(lat, lon, alt);
    }

    public void routeFieldSelection() {
        int index = window.getRouteField().getSelectedIndex();

        if (index == -1) {
            index = routeIndex;
        }
        else {
            routeIndex = index;
        }

        MainList mainListObj = MainList.getInstance();
        currRoute = mainListObj.get(index);

        DefaultListModel listModel = currRoute.printPoints();
        window.getPointField().setModel(listModel);

        // You can now append POINTS
        window.enablePointWork();

        //Undo-Redo buttons fix
        if (currRoute.getCmdMngr().getDoneStack().isEmpty()) {
            window.getUndoButton().setEnabled(false);
        }
        else window.getUndoButton().setEnabled(true);

        if (currRoute.getCmdMngr().getUndoneStack().isEmpty()) {
            window.getRedoButton().setEnabled(false);
        }
        else window.getRedoButton().setEnabled(true);
    }

    public void pointFieldSelection() {
        window.enablePointWork();
    }

    public void createRoute() {
        String name = window.getNameField().getText();
        name = name.replaceAll("[#$*&?+=@;:!{}~^]", "");
        window.getMsgArea().setText(null);
        if (name.length() == 0) {
            window.getMsgArea().append("You should NAME your new Route!!!");
            window.getMsgArea().append("\nTip: Special symbols: #$*&?+=@;:!{}~^ will be IGNORED.");
            return;
        }

        if (name.length() > 30) {
            window.getMsgArea().setText("Your name is to long!!\nTip: name-length = 1..10 symbols");
            return;
        }

        MainList mainListObj = MainList.getInstance();
        if (mainListObj.contains(name) == true) {
            window.getMsgArea().setText("Route with THIS NAME is already EXISTS!!!");
            return;
        }

        // Executing: AddRoute
        int ind = mainListObj.getRouteList().size();
        AddRouteCmd cmd = new AddRouteCmd(ind, name);
        if (mainListObj.getCmdMngr().add(cmd) == false) {
            window.getMsgArea().setText("Error: Route NOT Added!!!");
            return;
        }
        currRoute = mainListObj.getRouteList().get(ind);

        window.getMsgArea().setText("Route added!");

        resetRouteList();
    }


    public void addPoint() {
        Point point = pointCheckin();
        if (point == null) {
            return;
        }
        double lat = point.getLat();
        double lon = point.getLon();
        double alt = point.getAlt();

        MainList mainListObj = MainList.getInstance();
        currRoute = mainListObj.get(routeIndex);

        int pointInd = window.getPointField().getSelectedIndex() + 1;
        if (pointInd == 0) {
            pointInd = currRoute.pointListSize();
        }

        // Executing: addPoint
        AddPointCmd cmd = new AddPointCmd(routeIndex, pointInd, lat, lon, alt);
        if (currRoute.getCmdMngr().add(cmd) == false) {
            window.getMsgArea().setText("Error: Point NOT Added!!!");
            return;
        }

        window.getUndoButton().setEnabled(true);
        window.getRedoButton().setEnabled(false);
        window.disablePointWork();

        resetPointList();
        resetRouteList();

        window.getMsgArea().setText("Point Added!");
    }

    public void deleteButtonAct() {
        int pointInd = window.getPointField().getSelectedIndex();

        // Executing: DelPoint
        DelPointCmd cmd = new DelPointCmd(routeIndex, pointInd);
        if (currRoute.getCmdMngr().add(cmd) == false) {
            window.getMsgArea().setText("Error: Point NOT deleted!!!");
            return;
        }

        window.disablePointWork();

        resetPointList();
        resetRouteList();

        window.getMsgArea().setText("Point Deleted!");
    }

    public void editPoint() {
        Point point = pointCheckin();
        if (point == null) {
            return;
        }
        double lat = point.getLat();
        double lon = point.getLon();
        double alt = point.getAlt();

        int pointInd = window.getPointField().getSelectedIndex();

        // Executing: editPoint
        EditPointCmd cmd = new EditPointCmd(routeIndex, pointInd, lat, lon, alt);
        if (currRoute.getCmdMngr().add(cmd) == false) {
            window.getMsgArea().setText("Error: Point NOT edited!!!");
            return;
        }

        window.disablePointWork();

        resetPointList();
        resetRouteList();

        window.getMsgArea().setText("Point edited!");
    }

    public void undo() {
        // Manager Undo Cmd
        if (currRoute.getCmdMngr().undo() == false) {
            window.getMsgArea().setText("Error: Undo failed!");
            return;
        }

        resetRouteList();
        resetPointList();

        window.disablePointWork();

        if (currRoute.getCmdMngr().getDoneStack().isEmpty()) {
            window.getUndoButton().setEnabled(false);
        }
        window.getRedoButton().setEnabled(true);

        window.getMsgArea().setText("Undo done!");
    }

    public void redo() {
        // Manager Redo Cmd
        if (currRoute.getCmdMngr().redo() == false) {
            window.getMsgArea().setText("Error: Undo failed!");
            return;
        }

        resetRouteList();
        resetPointList();

        window.disablePointWork();

        if (currRoute.getCmdMngr().getUndoneStack().isEmpty()) {
            window.getRedoButton().setEnabled(false);
        }
        window.getUndoButton().setEnabled(true);

        window.getMsgArea().setText("Redo done!");
    }
}
