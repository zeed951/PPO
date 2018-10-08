package Route_GPX;

import io.jenetics.jpx.GPX;
import io.jenetics.jpx.Track;
import io.jenetics.jpx.TrackSegment;
import io.jenetics.jpx.WayPoint;
import org.math.plot.Plot2DPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class RouteWorkPresenter {
    private RouteWorkWindow window;
    private Route currRoute;
    private int routeIndex;

    RouteWorkPresenter(RouteWorkWindow window) {
        this.window = window;
    }


    private void resetRouteList() {
        MainList mainListObj = MainList.getInstance();
        window.showRoutes(mainListObj);
    }

    //Getting Route name for polyline files
    private String getNamePoly(String path) {
        int index = path.lastIndexOf("/");
        return path.substring(index + 1);
    }

    //Importing GPX-routes from file
    private boolean importGPX(String filePath) {
        List<WayPoint> pointList = new ArrayList<WayPoint>();
        List<Track> trackList = new ArrayList<Track>();
        MainList mainListObj = MainList.getInstance();
        int ptInd;
        String trackname;

        //Getting tracks(Routes) from a file
        try {
            GPX.read(filePath).tracks().collect(Collectors.toCollection(() -> trackList));
        } catch (IOException exc) {
            return false;
        }

        for (Track track : trackList) {
            //Replace - if this track is already exists
            trackname = track.getName().isPresent() ? track.getName().get() : "untitled";
            if (mainListObj.contains(trackname)) {
                mainListObj.del(trackname);
            }

            // Executing: AddRoute
            int ind = mainListObj.getRouteList().size();
            AddRouteCmd cmd = new AddRouteCmd(ind, trackname);
            if (mainListObj.getCmdMngr().add(cmd) == false) {
                return false;
            }
            currRoute = mainListObj.get(ind);

            //Filling Route's pointlist
            track.segments().flatMap(TrackSegment::points).collect(Collectors.toCollection(() -> pointList));
            ptInd = 0;
            for (WayPoint point : pointList) {
                double lat = point.getLatitude().doubleValue();
                double lon = point.getLongitude().doubleValue();
                double alt = 0;

                if (point.getElevation().isPresent()) {
                    alt = point.getElevation().get().doubleValue();
                }

                currRoute.addPoint(ptInd, lat, lon, alt);
                ptInd++;
            }
            currRoute.setLength(currRoute.culcDistance());
        }

        return true;
    }

    public void start() {
        resetRouteList();

        MainList mainListObj = MainList.getInstance();
        if (mainListObj.getCmdMngr().getDoneStack().isEmpty() == false) {
            window.getUndoButton().setEnabled(true);
        }
        if (mainListObj.getCmdMngr().getUndoneStack().isEmpty() == false) {
            window.getRedoButton().setEnabled(true);
        }
    }

    public void routeSelection() {
        window.enableRouteWork();

        int index = window.getRouteField().getSelectedIndex();

        if (index == -1) {
            index = routeIndex;
        } else {
            routeIndex = index;
        }
        MainList mainListObj = MainList.getInstance();
        currRoute = mainListObj.get(index);
    }

    public void renameRoute() {
        String name = window.getNewNameField().getText();
        name = name.replaceAll("[#$*&?+=@;:!{}~^]", "");

        if (name.isEmpty() == true) {
            window.getMsgArea().setText("You should enter the name below!!");
            window.getMsgArea().append("\nTip: Special symbols: #$*&?+=@;:!{}~^ will be IGNORED.");
            return;
        }

        if (name.length() > 10) {
            window.getMsgArea().setText("Your name is to long!!");
            window.getMsgArea().append("\nTip: name-length = 1..10 symbols");
            return;
        }

        currRoute.setName(name);
        resetRouteList();

        window.disableRouteWork();
    }

    public void showAltMap() {
        if (currRoute.getPointList().size() < 2) {
            window.getMsgArea().setText("Can't draw Altitude Map!");
            window.getMsgArea().append("\nTip: At least 2 points needed");
            return;
        }

        Dimension sSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = sSize.width, height = sSize.height;

        ArrayList<Point> pointList = currRoute.getPointList();
        double[] xPoints = new double[width];
        double[] yPoints = new double[width];

        int step;
        int widthMap;

        if (pointList.size() < width) {
            step = 1;
            widthMap = pointList.size();
        }
        else {
            step = pointList.size()/width;
            widthMap = width;
        }
        int j = 0;

        for(int i = 0; i < widthMap; i++) {
            yPoints[i] = pointList.get(j).getAlt();
            xPoints[i] = i;
            j += step;
        }

        Plot2DPanel plot = new Plot2DPanel();

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String title = "Altitude Map:    " + currRoute.getName() +
                "    " + dateFormat.format(date);

        plot.addLinePlot(title, xPoints, yPoints);
        plot.setAxisLabels("Distance, km", "Height, m");
        //plot.setBounds(0, 0, pointList.size(), pointList.size());

        window.createAltMapFrame(title, width, height, plot);
    }

    public void deleteRoute() {
        MainList mainListObj = MainList.getInstance();

        // Executing: DelRoute
        DelRouteCmd cmd = new DelRouteCmd(routeIndex);
        if (mainListObj.getCmdMngr().add(cmd) == false) {
            window.getMsgArea().setText("Error: Route NOT Deleted!!!");
            return;
        }

        window.getUndoButton().setEnabled(true);

        resetRouteList();
        window.disableRouteWork();
        window.getRedoButton().setEnabled(false);
    }

    public void importRoute() {
        String input = window.getImportField().getText();
        FileReader fr;
        BufferedReader br;
        String formatCheck;

        //If it exists
        try {
            fr = new FileReader(input);
        } catch (FileNotFoundException exc) {
            window.getMsgArea().setText("File not found!!");
            window.getMsgArea().append("\nTip: input path might be incorrect");
            return;
        }

        //Reading 1 line to find out if it's gpx or polyline format
        try {
            br = new BufferedReader(fr);
            formatCheck = br.readLine();
        } catch (IOException exc) {
            window.getMsgArea().setText("Reading failed!!");
            window.getMsgArea().append("\nTip: :(");
            return;
        }

        //IsEmpty
        if (formatCheck == null) {
            window.getMsgArea().setText("Input file is empty!!");
            return;
        }

        //GPX import
        if (formatCheck.contains("<?xml ")) {
            if (importGPX(input) == false) {
                window.getMsgArea().setText("Import failed :(");
                return;
            }
        }
        //It should be a polyline!
        else {
            MainList mainListObj = MainList.getInstance();
            ArrayList<Point> pointList = GooglePolyline.decode(formatCheck, 1);
            if (pointList.isEmpty()) {
                window.getMsgArea().setText("Import failed :(");
                window.getMsgArea().append("Polyline might be incorrect");
                return;
            }
            // Executing: AddRoute
            int ind = mainListObj.getRouteList().size();
            AddRouteCmd cmd = new AddRouteCmd(ind, getNamePoly(input));
            if (mainListObj.getCmdMngr().add(cmd) == false) {
                window.getMsgArea().setText("Import failed :(");
                return;
            }
            currRoute = mainListObj.get(ind);
            currRoute.setPointList(pointList);
            currRoute.setLength(currRoute.culcDistance());
        }
        window.getUndoButton().setEnabled(true);
        window.getRedoButton().setEnabled(false);

        window.getMsgArea().setText("Import DONE");
        resetRouteList();
    }

    public void getPolyline() {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        String polyline;

        polyline = currRoute.getPolyline();
        if (polyline.isEmpty()) {
            window.getMsgArea().setText("Can't get a Polyline!");
            window.getMsgArea().append("\nThat Route might be Empty");
            return;
        }
        //Copying to the ClipBoard
        StringSelection contents = new StringSelection(polyline);
        clipboard.setContents(contents, null);

        window.getMsgArea().setText("Polyline COPIED to your clipboard!");

        window.disableRouteWork();
    }

    public void undo() {
        MainList mainListObj = MainList.getInstance();
        // Manager Undo Cmd
        if (mainListObj.getCmdMngr().undo() == false) {
            window.getMsgArea().setText("Error: Undo failed!");
            return;
        }

        resetRouteList();

        //Message
        window.getMsgArea().setText("Undo done!");

        resetRouteList();

        if (mainListObj.getCmdMngr().getDoneStack().isEmpty()) {
            window.getUndoButton().setEnabled(false);
        }
        window.getRedoButton().setEnabled(true);
    }

    public void redo() {
        MainList mainListObj = MainList.getInstance();
        // Manager Redo Cmd
        if (mainListObj.getCmdMngr().redo() == false) {
            window.getMsgArea().setText("Error: Undo failed!");
            return;
        }

        resetRouteList();

        //Message
        window.getMsgArea().setText("Redo done!");

        resetRouteList();

        if (mainListObj.getCmdMngr().getUndoneStack().isEmpty()) {
            window.getRedoButton().setEnabled(false);
        }
        window.getUndoButton().setEnabled(true);
    }
}
