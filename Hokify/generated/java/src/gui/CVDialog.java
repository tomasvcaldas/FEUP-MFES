package gui;

import Hokify.CV;
import Hokify.PastJob;
import Hokify.quotes.*;

import javax.swing.*;
import java.awt.event.*;

public class CVDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel driversLicense;
    private JList hobbies;
    private JList skills;
    private JList workExperience;
    private JTextArea education;
    private CV cv;
    private CVDialog me;

    public void addPastJob(PastJob j) {
        DefaultListModel jobsList = (DefaultListModel) workExperience.getModel();
        jobsList.addElement(j);
    }

    public CVDialog(CV cv) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        this.cv = cv;
        me = this;

        DefaultListModel hobbiesList = (DefaultListModel) hobbies.getModel();
        for (Object o : cv.getHobbies()) {
            String hobby = (String) o;
            hobbiesList.addElement(hobby);
        }

        DefaultListModel skillsList = (DefaultListModel) skills.getModel();
        for (Object o : cv.getSkills()) {
            String skill = (String) o;
            skillsList.addElement(skill);
        }

        DefaultListModel jobsList = (DefaultListModel) workExperience.getModel();
        for (Object o : cv.getWorkExperience()) {
            PastJob pastJob = (PastJob) o;
            jobsList.addElement(pastJob);
        }

        education.setText(cv.getEducation().toString());

        if (cv.getDriversLicense() != null)
            driversLicense.setText(cv.getDriversLicense().toString());
        else driversLicense.setText("No Drivers License");

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
        hobbies.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getClickCount() == 2) {
                    String hobby = JOptionPane.showInputDialog("Hobby name: ", "");
                    cv.addHobbie(hobby);
                    hobbiesList.addElement(hobby);
                }
            }
        });
        skills.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getClickCount() == 2) {
                    String skill = JOptionPane.showInputDialog("Skill name: ", "");
                    cv.addSkill(skill);
                    skillsList.addElement(skill);
                }
            }
        });
        workExperience.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getClickCount() == 2) {
                    CreatePastJobDialog dialog = new CreatePastJobDialog(me, cv);
                    dialog.pack();
                    dialog.setVisible(true);
                }
            }
        });

        education.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getClickCount() == 2) {
                    String[] validEducations = {"FineSecondarySchool", "HighSchool", "MandatorySchool", "Teaching",
                            "UniversityCollege"};
                    String educationStr = (String) JOptionPane.showInputDialog(me,
                            "Education to add:",
                            "Education",
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            validEducations,
                            validEducations[0]);
                    if(educationStr.equals("FineSecondarySchool")) cv.addEducation(new FineSecondarySchoolQuote());
                    if(educationStr.equals("HighSchool")) cv.addEducation(new HighSchoolQuote());
                    if(educationStr.equals("MandatorySchool")) cv.addEducation(new MandatorySchoolQuote());
                    if(educationStr.equals("Teaching")) cv.addEducation(new TeachingQuote());
                    if(educationStr.equals("UniversityCollege")) cv.addEducation(new UniversityCollegeQuote());
                    education.setText(cv.getEducation().toString());
                }
            }
        });
        driversLicense.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getClickCount() == 2) {
                    String[] validLicenses = {"A", "B", "C", "D"};
                    String licenseStr = (String) JOptionPane.showInputDialog(me,
                            "Drivers License to set:",
                            "Drivers License",
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            validLicenses,
                            validLicenses[0]);
                    if(licenseStr.equals("A")) cv.setDriversLicense(new AQuote());
                    if(licenseStr.equals("B")) cv.setDriversLicense(new BQuote());
                    if(licenseStr.equals("C")) cv.setDriversLicense(new CQuote());
                    if(licenseStr.equals("D")) cv.setDriversLicense(new DQuote());
                    driversLicense.setText(cv.getDriversLicense().toString());
                }
            }
        });
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
        hobbies = new JList(new DefaultListModel());
        skills = new JList(new DefaultListModel());
        workExperience = new JList(new DefaultListModel());
    }
}
