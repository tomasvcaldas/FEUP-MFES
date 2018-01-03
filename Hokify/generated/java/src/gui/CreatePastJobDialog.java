package gui;

import Hokify.CV;
import Hokify.Job;
import Hokify.PastJob;

import javax.swing.*;
import java.awt.event.*;

public class CreatePastJobDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField companyField;
    private JTextField endDateField;
    private JTextField positionField;
    private JTextField startDateField;
    private CVDialog parent;
    private CV cv;

    public CreatePastJobDialog(CVDialog parent, CV cv) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        this.cv = cv;
        this.parent = parent;

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
        String company = companyField.getText();
        String position = positionField.getText();
        String[] startDateVals = startDateField.getText().split("/");
        Job.Date startDate = new Job.Date(Integer.parseInt(startDateVals[2]), Integer.parseInt(startDateVals[1]), Integer.parseInt(startDateVals[0]));
        Job.Date endDate;
        if(endDateField.getText().equals("")) endDate = null;
        else {
            String[] endDateVals = endDateField.getText().split("/");
            endDate = new Job.Date(Integer.parseInt(endDateVals[2]), Integer.parseInt(endDateVals[1]), Integer.parseInt(endDateVals[0]));
        }
        PastJob j = new PastJob(company, position, startDate, endDate);
        cv.addPastJob(j);
        parent.addPastJob(j);
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
