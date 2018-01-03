package gui;

import javax.swing.*;
import java.awt.event.*;
import java.util.Arrays;

import Hokify.Hokify;
import Hokify.JobOffer;
import Hokify.quotes.*;
import org.overture.codegen.runtime.VDMSet;

public class CreateJobOfferDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField companyField;
    private JTextField positionField;
    private JSpinner noPositionsField;
    private JTextField skillsField;
    private JTextField educationField;
    private JComboBox licenseField;
    private Hokify hokify;

    public CreateJobOfferDialog(Hokify hokify) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        this.hokify = hokify;

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
        int noPositions = (int) noPositionsField.getValue();

        String[] neededSkills = skillsField.getText().split(",");
        VDMSet skillSet = new VDMSet();
        for(String s : neededSkills) {
            if(s.length() > 0) skillSet.add(s);
        }

        String[] requiredEdu = educationField.getText().split(",");
        VDMSet requiredEducation = new VDMSet();
        for(String s : requiredEdu) {
            if(s.equals("FineSecondarySchool")) requiredEducation.add(new FineSecondarySchoolQuote());
            if(s.equals("HighSchool")) requiredEducation.add(new HighSchoolQuote());
            if(s.equals("MandatorySchool")) requiredEducation.add(new MandatorySchoolQuote());
            if(s.equals("Teaching")) requiredEducation.add(new TeachingQuote());
            if(s.equals("UniversityCollege")) requiredEducation.add(new UniversityCollegeQuote());
        }

        Object requiredLicense = null;
        if(licenseField.getSelectedItem().equals("A")) requiredLicense = (new AQuote());
        if(licenseField.getSelectedItem().equals("B")) requiredLicense = (new BQuote());
        if(licenseField.getSelectedItem().equals("C")) requiredLicense = (new CQuote());
        if(licenseField.getSelectedItem().equals("D")) requiredLicense = (new DQuote());

        JobOffer newOffer = new JobOffer(company, position, noPositions, skillSet, requiredEducation, requiredLicense);
        hokify.addJobOffer(newOffer);
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
