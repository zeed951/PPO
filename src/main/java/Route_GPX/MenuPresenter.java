package Route_GPX;

import java.io.*;

public class MenuPresenter {
    private MenuWindow window;
    private String path = "src/for_serialize.txt";

    MenuPresenter(MenuWindow window) {
        this.window = window;
    }

    public void deserialize() {
        //Importing saved routeList (if found by path)
        try(FileInputStream fis = new FileInputStream(path);
            ObjectInputStream oin = new ObjectInputStream(fis)) {
            SaveRouteList svList = (SaveRouteList) oin.readObject();
            MainList mainListObj = MainList.getInstance();
            mainListObj.importRoutes(svList.getRouteList());
        }
        catch (IOException | ClassNotFoundException exc) {
            exc.printStackTrace();
        }
    }

    public void runRouteWork() {
        window.createPointWorkFrame();
        if (window.getRouteWorkFrame() != null) {
            window.getRouteWorkFrame().setVisible(false);
        }
        window.getSaveLabel().setText("");
    }

    public void runPointWork() {
        window.createRouteWorkFrame();
        if (window.getPointWorkFrame() != null) {
            window.getPointWorkFrame().setVisible(false);
        }
        window.getSaveLabel().setText("");
    }

    public void saveAll(){
        try(FileOutputStream fos = new FileOutputStream(path);
            ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            MainList mainListObj = MainList.getInstance();

            SaveRouteList svList = new SaveRouteList();
            svList.setRouteList(mainListObj.getRouteList());
            svList.detachMngr();
            oos.writeObject(svList);

            window.getSaveLabel().setText("Saved!");
        }
        catch (IOException exc) {
            window.getSaveLabel().setText("Saving Failed :(");
        }
    }
}