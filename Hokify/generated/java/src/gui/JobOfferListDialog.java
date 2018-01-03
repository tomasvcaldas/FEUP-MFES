package gui;

import Hokify.Hokify;
import Hokify.JobOffer;
import org.overture.codegen.runtime.VDMSet;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.function.Function;

public class JobOfferListDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTable offersTable;
    private Hokify hokify;
    private ArrayList<JobOffer> offerList;

    public JobOfferListDialog(Hokify hokify) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        this.hokify = hokify;
        this.offerList = new ArrayList<>();

        VDMSet offers = hokify.getJobOffers();
        DefaultTableModel dfm = (DefaultTableModel) offersTable.getModel();
        for(Object o : offers) {
            JobOffer offer = (JobOffer) o;
            offerList.add(offer);

            Object[] params = new Object[8];
            params[0] = offer.getCompany();
            params[1] = offer.getPosition();
            params[2] = offer.getOpenPositions();
            params[3] = offer.getNeededSkills();
            params[4] = offer.getRequiredEducation();
            params[5] = offer.getRequiredDriversLicense();
            params[6] = offer.getAppliances();
            params[7] = "Delete";

            dfm.addRow(params);
            offersTable.getColumn("Delete").setCellRenderer(new JobOfferListDialog.ButtonRenderer());
            offersTable.getColumn("Delete").setCellEditor(
                    new JobOfferListDialog.ButtonEditor(new JCheckBox(), o1 -> {
                        hokify.removeJobOffer(o1);
                        return null;
                    }, offer));
        }

        offersTable.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                JTable table =(JTable) mouseEvent.getSource();
                Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                if (mouseEvent.getClickCount() == 2) {
                    JobOffer clickedOffer = offerList.get(row);
                    JobOfferAppliancesDialog dialog = new JobOfferAppliancesDialog(hokify, clickedOffer);
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

    private void createUIComponents() {
        // TODO: place custom component creation code here
        Object[] header = {"Company", "Position", "Number Positions", "Skills", "Education", "Drivers License", "Appliances", "Delete"};
        offersTable = new JTable(new DefaultTableModel(header, 0));
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

        private Function<JobOffer, JobOffer> f;
        private JobOffer o;

        public ButtonEditor(JCheckBox checkBox, Function<JobOffer, JobOffer> f, JobOffer o) {
            super(checkBox);
            this.f = f;
            this.o = o;
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
                f.apply(o);
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
