package Route_GPX;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;

public class PointWorkWindow {
    private JPanel createPanel;
    private JTextField nameField;
    private JButton createRouteButton;
    private JTextArea msgArea;
    private JButton addPointButton;
    private JTextField latField;
    private JTextField lonField;
    private JList routeField;
    private JButton deleteButton;
    private JButton editButton;
    private JList pointField;
    private JButton undoButton;
    private JButton redoButton;
    private JScrollPane pointScroll;
    private JTextField altField;
    private JScrollPane routeScroll;
    private PointWorkPresenter Presenter = new PointWorkPresenter(this);

    public PointWorkWindow() {
        Presenter.resetRouteList();

        routeField.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                Presenter.routeFieldSelection();
            }
        });

        pointField.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                Presenter.pointFieldSelection();
            }
        });

        createRouteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Presenter.createRoute();
            }
        });

        addPointButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Presenter.addPoint();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Presenter.deleteButtonAct();
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Presenter.editPoint();
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

    public void showPoints(Route route) {
        DefaultListModel listModel = route.printPoints();
        pointField.setModel(listModel);
    }

    public void enablePointWork() {
        latField.setEditable(true);
        lonField.setEditable(true);
        altField.setEditable(true);
        addPointButton.setEnabled(true);
        deleteButton.setEnabled(true);
        editButton.setEnabled(true);
    }

    public void disablePointWork() {
        deleteButton.setEnabled(false);
        editButton.setEnabled(false);
    }


    public JTextField getNameField() {
        return nameField;
    }

    public JTextArea getMsgArea() {
        return msgArea;
    }

    public JTextField getLatField() {
        return latField;
    }

    public JTextField getLonField() {
        return lonField;
    }

    public JList getRouteField() {
        return routeField;
    }

    public JList getPointField() {
        return pointField;
    }

    public JButton getUndoButton() {
        return undoButton;
    }

    public JButton getRedoButton() {
        return redoButton;
    }

    public JTextField getAltField() {
        return altField;
    }

    public JPanel getCreatePanel() {
        return createPanel;
    }

    public JButton getCreateRouteButton() {
        return createRouteButton;
    }

    public JButton getAddPointButton() {
        return addPointButton;
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }

    public JButton getEditButton() {
        return editButton;
    }

}
