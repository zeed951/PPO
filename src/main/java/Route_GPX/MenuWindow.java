package Route_GPX;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuWindow {
    private JPanel menuPanel;
    private JButton openPointWorkButton;
    private JButton openRouteWorkButton;
    private JButton saveButton;
    private JLabel saveLabel;
    private JFrame routeWorkFrame;
    private JFrame pointWorkFrame;

    private MenuPresenter Presenter = new MenuPresenter(this);

    public MenuWindow(){
        Presenter.deserialize();

        openPointWorkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Presenter.runRouteWork();
            }
        });

        openRouteWorkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Presenter.runPointWork();
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                Presenter.saveAll();
            }
        });
    }

    public void createPointWorkFrame() {
        pointWorkFrame = new JFrame("Polyline/Points");
        pointWorkFrame.setContentPane(new PointWorkWindow().getCreatePanel());
        pointWorkFrame.setPreferredSize(new Dimension(1000, 500));
        pointWorkFrame.setLocation(200, 100);
        pointWorkFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        pointWorkFrame.pack();
        pointWorkFrame.setVisible(true);
    }

    public void createRouteWorkFrame() {
        routeWorkFrame = new JFrame("Polyline/Route");
        routeWorkFrame.setContentPane(new RouteWorkWindow().getRouteWorkPanel());
        routeWorkFrame.setPreferredSize(new Dimension(820, 600));
        routeWorkFrame.setLocation(300, 100);
        routeWorkFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        routeWorkFrame.pack();
        routeWorkFrame.setVisible(true);
    }

    public JLabel getSaveLabel() {
        return saveLabel;
    }

    public JFrame getRouteWorkFrame() {
        return routeWorkFrame;
    }

    public JFrame getPointWorkFrame() {
        return pointWorkFrame;
    }

    public JPanel getMenuPanel() {
        return menuPanel;
    }
}
