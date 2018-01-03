package gui;

import Hokify.Hokify;
import Hokify.User;
import Hokify.JobOffer;
import org.overture.codegen.runtime.VDMSet;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class UserAppliancesDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTable offersTable;
    private Hokify hokify;
    private User user;
    private ArrayList<JobOffer> offerList;

    public UserAppliancesDialog(Hokify hokify, User user) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        this.hokify = hokify;
        this.user = user;
        this.offerList = new ArrayList<JobOffer>();

        if(user.getCV() == null) user.createCV();
        VDMSet offers = this.hokify.getMatchingOffersForUser(user);
        DefaultTableModel dfm = (DefaultTableModel) offersTable.getModel();
        for (Object o : offers) {
            JobOffer offer = (JobOffer) o;
            offerList.add(offer);

            Object[] params = new Object[7];
            params[0] = offer.getCompany();
            params[1] = offer.getPosition();
            params[2] = offer.getOpenPositions();
            params[3] = offer.getNeededSkills();
            params[4] = offer.getRequiredEducation();
            params[5] = offer.getRequiredDriversLicense();
            params[6] = offer.getAppliances();

            dfm.addRow(params);
        }

        offersTable.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                JTable table = (JTable) mouseEvent.getSource();
                Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                if (mouseEvent.getClickCount() == 2) {
                    hokify.addAppliance(user, offerList.get(row));
                    dispose();
                }
            }
        });

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        Object[] header = {"Company", "Position", "Number Positions", "Skills", "Education", "Drivers License", "Appliances"};
        offersTable = new JTable(new DefaultTableModel(header, 0));
    }
}
