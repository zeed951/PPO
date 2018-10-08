package Route_GPX;

import javax.swing.*;
import java.awt.Dimension;

public class Main {
    public static void main(String[] args)
    {
        JFrame menuFrame = new JFrame("Polyline");
        menuFrame.setContentPane(new MenuWindow().getMenuPanel());
        menuFrame.setPreferredSize(new Dimension(300, 300));
        menuFrame.setLocation(450, 250);
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.pack();
        menuFrame.setVisible(true);
    }
}
