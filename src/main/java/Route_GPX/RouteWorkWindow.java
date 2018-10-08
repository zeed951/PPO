package Route_GPX;

import org.math.plot.Plot2DPanel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RouteWorkWindow {
    private JPanel routeWorkPanel;
    private JList routeField;
    private JScrollPane RouteScroll;
    private JTextArea msgArea;
    private JButton importButton;
    private JButton getPolylineButton;
    private JTextField newNameField;
    private JButton renameButton;
    private JTextField importField;
    private JButton deleteButton;
    private JButton undoButton;
    private JButton redoButton;
    private JButton drawAltButton;
    private RouteWorkPresenter Presenter = new RouteWorkPresenter(this);

    public RouteWorkWindow() {
        Presenter.start();

        routeField.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                Presenter.routeSelection();
            }
        });

        renameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Presenter.renameRoute();
            }
        });

        drawAltButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Presenter.showAltMap();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Presenter.deleteRoute();
            }
        });

        importButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Presenter.importRoute();
            }
        });

        getPolylineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Presenter.getPolyline();
            }
        });

        undoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Presenter.undo();
            }
        });

        redoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Presenter.redo();
            }
        });
    }

    public void showRoutes(MainList routeList) {
        DefaultListModel listModel = routeList.printRoutes();
        routeField.setModel(listModel);
    }

    public void enableRouteWork() {
        newNameField.setEnabled(true);
        renameButton.setEnabled(true);
        getPolylineButton.setEnabled(true);
        deleteButton.setEnabled(true);
        drawAltButton.setEnabled(true);
    }

    public void disableRouteWork() {
        newNameField.setEnabled(false);
        renameButton.setEnabled(false);
        getPolylineButton.setEnabled(false);
        deleteButton.setEnabled(false);
        drawAltButton.setEnabled(false);
    }

    public void createAltMapFrame(String title, int width, int height, Plot2DPanel plot){
        JFrame plotFrame = new JFrame(title);
        plotFrame.setContentPane(plot);
        plotFrame.setPreferredSize(new Dimension(width, height));
        plotFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        plotFrame.pack();
        plotFrame.setVisible(true);
    }


    public JPanel getRouteWorkPanel() {
        return routeWorkPanel;
    }

    public JList getRouteField() {
        return routeField;
    }

    public JTextArea getMsgArea() {
        return msgArea;
    }

    public JTextField getNewNameField() {
        return newNameField;
    }

    public JButton getUndoButton() {
        return undoButton;
    }

    public JButton getRedoButton() {
        return redoButton;
    }

    public JTextField getImportField() {
        return importField;
    }

    public JButton getImportButton() {
        return importButton;
    }

    public JButton getGetPolylineButton() {
        return getPolylineButton;
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }

    public JButton getDrawAltButton() {
        return drawAltButton;
    }

}
