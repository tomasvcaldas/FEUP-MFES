package gui;

import Hokify.*;
import org.overture.codegen.runtime.VDMSet;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.function.Function;

public class UserListDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTable userTable;
    private Hokify hokify;
    private ArrayList<User> userList;

    public UserListDialog(Hokify hokify) {

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        this.hokify = hokify;
        this.userList = new ArrayList<>();


        VDMSet users = hokify.getUsers();
        DefaultTableModel dfm = (DefaultTableModel) userTable.getModel();
        for(Object o : users) {
            User u = (User) o;
            this.userList.add(u);

            Object[] userParams = new Object[7];
            userParams[0] = (u.getFirstName());
            userParams[1] = (u.getLastName());
            userParams[2] = (u.getEmail());
            userParams[3] = (u.getPhoneNumber());
            userParams[4] = (u.getAppliances());
            userParams[5] = ("View/Edit CV");
            userParams[6] = ("Delete");

            dfm.addRow(userParams);
            userTable.getColumn("CV").setCellRenderer(new ButtonRenderer());
            userTable.getColumn("CV").setCellEditor(
                    new ButtonEditor(new JCheckBox(), u1 -> {
                        if(u1.getCV() == null) u1.createCV();
                        CVDialog dialog = new CVDialog(u1.getCV());
                        dialog.pack();
                        dialog.setVisible(true);
                        return u1;
                    }, u));

            userTable.getColumn("Delete").setCellRenderer(new ButtonRenderer());
            userTable.getColumn("Delete").setCellEditor(
                     new ButtonEditor(new JCheckBox(), u1 -> {
                        hokify.removeUser(u1);
                        return null;
                    }, u));
        }

        userTable.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                JTable table =(JTable) mouseEvent.getSource();
                Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                if (mouseEvent.getClickCount() == 2) {
                    User clickedUser = userList.get(row);
                    UserAppliancesDialog dialog = new UserAppliancesDialog(hokify, clickedUser);
                    dialog.pack();
                    dialog.setVisible(true);
                    // your valueChanged overridden method
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

    public static void main(String[] args) {
        UserListDialog dialog = new UserListDialog(new Hokify());
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        Object[] header = {"First Name", "Last Name", "Email", "Phone Number", "Appliances", "CV", "Delete"};
        userTable = new JTable(new DefaultTableModel(header, 0));

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
