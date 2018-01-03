package gui;

import Hokify.Hokify;
import Hokify.JobOffer;
import Hokify.User;
import org.overture.codegen.runtime.VDMSet;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.function.Function;

public class JobOfferAppliancesDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTable appliancesTable;
    private Hokify hokify;
    private JobOffer offer;
    private ArrayList<User> userList;

    public JobOfferAppliancesDialog(Hokify hokify, JobOffer offer) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        this.hokify = hokify;
        this.offer = offer;
        this.userList = new ArrayList<>();

        VDMSet appliances = offer.getAppliances();
        DefaultTableModel dfm = (DefaultTableModel) appliancesTable.getModel();
        for (Object o : appliances) {
            User u = (User) o;

            this.userList.add(u);

            Object[] userParams = new Object[7];
            userParams[0] = (u.getFirstName());
            userParams[1] = (u.getLastName());
            userParams[2] = (u.getEmail());
            userParams[3] = (u.getPhoneNumber());
            userParams[4] = ("View CV");
            userParams[5] = ("Accept Application");
            userParams[6] = ("Decline Application");

            dfm.addRow(userParams);

            appliancesTable.getColumn("CV").setCellRenderer(new JobOfferAppliancesDialog.ButtonRenderer());
            appliancesTable.getColumn("CV").setCellEditor(
                    new JobOfferAppliancesDialog.ButtonEditor(new JCheckBox(), u1 -> {
                        if (u1.getCV() == null) u1.createCV();
                        CVDialog dialog = new CVDialog(u1.getCV());
                        dialog.pack();
                        dialog.setVisible(true);
                        return u1;
                    }, u));

            appliancesTable.getColumn("Accept").setCellRenderer(new JobOfferAppliancesDialog.ButtonRenderer());
            appliancesTable.getColumn("Accept").setCellEditor(
                    new JobOfferAppliancesDialog.ButtonEditor(new JCheckBox(), u1 -> {
                        if (u1.getCV() == null) u1.createCV();
                        hokify.acceptAppliance(u1, offer);
                        dispose();
                        return u1;
                    }, u));

            appliancesTable.getColumn("Decline").setCellRenderer(new JobOfferAppliancesDialog.ButtonRenderer());
            appliancesTable.getColumn("Decline").setCellEditor(
                    new JobOfferAppliancesDialog.ButtonEditor(new JCheckBox(), u1 -> {
                        if (u1.getCV() == null) u1.createCV();
                        hokify.declineAppliance(u1, offer);
                        dispose();
                        return u1;
                    }, u));
        }

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
        Object[] header = {"First Name", "Last Name", "Email", "Phone Number", "CV", "Accept", "Decline"};
        appliancesTable = new JTable(new DefaultTableModel(header, 0));
    }

    class ButtonRenderer extends JButton implements TableCellRenderer {

        public ButtonRenderer() {
            setOpaque(true);
        }

        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            if (isSelected) {
                setForeground(table.getSelectionForeground());
                setBackground(table.getSelectionBackground());
            } else {
                setForeground(table.getForeground());
                setBackground(UIManager.getColor("Button.background"));
            }
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    /**
     * @version 1.0 11/09/98
     */

    class ButtonEditor extends DefaultCellEditor {
        protected JButton button;

        private String label;

        private boolean isPushed;

        private Function<User, User> f;
        private User u;

        public ButtonEditor(JCheckBox checkBox, Function<User, User> f, User u) {
            super(checkBox);
            this.f = f;
            this.u = u;
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                }
            });
        }

        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            if (isSelected) {
                button.setForeground(table.getSelectionForeground());
                button.setBackground(table.getSelectionBackground());
            } else {
                button.setForeground(table.getForeground());
                button.setBackground(table.getBackground());
            }
            label = (value == null) ? "" : value.toString();
            button.setText(label);
            isPushed = true;
            return button;
        }

        public Object getCellEditorValue() {
            if (isPushed) {
                f.apply(u);
            }
            isPushed = false;
            return new String(label);
        }

        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }

        protected void fireEditingStopped() {
            super.fireEditingStopped();
        }
    }
}
