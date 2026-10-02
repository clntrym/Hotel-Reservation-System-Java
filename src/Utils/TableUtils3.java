/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.util.EventObject;
import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author client
 */
public class TableUtils3 {
    // Call this to style table (optional: change colors/fonts)
    public static void styleTable(JTable table) {

        table.setRowHeight(38);
    table.setFont(new Font("Segoe UI", Font.PLAIN, 13));

    // Table colors
    table.setBackground(Color.WHITE);             // Table background
    table.setForeground(Color.BLACK);             // Text color
    table.setSelectionBackground(new Color(52, 152, 219)); // Highlighted row background
    table.setSelectionForeground(Color.WHITE);   // Highlighted row text

    // Remove grid lines for clean look
    table.setShowGrid(false);
    table.setShowVerticalLines(false);

    // Table header styling (plain text)
    JTableHeader header = table.getTableHeader();
    header.setFont(new Font("Segoe UI", Font.BOLD, 14));
    header.setPreferredSize(new Dimension(header.getWidth(), 35));
    header.setBackground(new Color(0, 0, 0, 0)); // transparent
    header.setOpaque(false);
    header.setForeground(new Color(60, 60, 60));
    header.setBorder(null);
    }
    
    // -------------------- STATUS RENDERER --------------------
    public static class StatusRenderer extends JPanel implements TableCellRenderer {

        private JLabel label = new JLabel();

        public StatusRenderer() {
            setLayout(new GridBagLayout());
            label.setOpaque(true);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setFont(new Font("Segoe UI", Font.BOLD, 12));
            label.setBorder(BorderFactory.createEmptyBorder(4,12,4,12));

            add(label);
            setOpaque(false);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {

            String status = value == null ? "" : value.toString();
            label.setText(status);

            switch(status){

                case "Confirmed" -> {
                    label.setBackground(new Color(52,152,219)); // blue
                    label.setForeground(Color.WHITE);
                }

                case "Checked-in" -> {
                    label.setBackground(new Color(39,174,96)); // green
                    label.setForeground(Color.WHITE);
                }

                case "Checked-out" -> {
                    label.setBackground(new Color(149,165,166)); // gray
                    label.setForeground(Color.WHITE);
                }

                case "Cancelled" -> {
                    label.setBackground(new Color(231,76,60)); // red
                    label.setForeground(Color.WHITE);
                }

                default -> {
                    label.setBackground(Color.GRAY);
                    label.setForeground(Color.WHITE);
                }
            }
            return this;
        }
    }

    // -------------------- ACTION RENDERER --------------------
    public static class ActionRenderer extends JPanel implements TableCellRenderer {

        private JButton btnCheckin = new JButton();
        private JButton btnCancel = new JButton();
        private JButton btnCheckout = new JButton();

        public ActionRenderer() {

            setLayout(new FlowLayout(FlowLayout.CENTER,5,0));
            
            ImageIcon checkin = new ImageIcon(getClass().getResource("/img/check-in.png"));
            Image imgcheckin = checkin.getImage().getScaledInstance(18,18,Image.SCALE_SMOOTH);
            btnCheckin.setIcon(new ImageIcon(imgcheckin));

            ImageIcon checkout = new ImageIcon(getClass().getResource("/img/checkout.png"));
            Image imgcheckout = checkout.getImage().getScaledInstance(18,18,Image.SCALE_SMOOTH);
            btnCheckout.setIcon(new ImageIcon(imgcheckout));
            
            ImageIcon cancel = new ImageIcon(getClass().getResource("/img/cross-button.png"));
            Image imgcancel = cancel.getImage().getScaledInstance(18,18,Image.SCALE_SMOOTH);
            btnCancel.setIcon(new ImageIcon(imgcancel));
            
            
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {

            removeAll();

            int statusColumn = table.getColumnModel().getColumnIndex("Status");
            String status = table.getValueAt(row, statusColumn).toString();

            switch(status){

                case "Confirmed":
                    add(btnCheckin);
                    add(btnCancel);
                    break;

                case "Checked-in":
                    add(btnCheckout);
                    break;

                case "Checked-out":
                case "Cancelled":
                    // no buttons
                    break;
            }

            return this;
        }
    }

    // -------------------- ACTION EDITOR --------------------
    public static class ActionEditor extends AbstractCellEditor implements TableCellEditor {

        private JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER,5,0));
        private JButton btnCheckin = new JButton();
        private JButton btnCancel = new JButton();
        private JButton btnCheckout = new JButton();

        private JTable table;

        public ActionEditor(JTable table, ActionHandler handler){

            this.table = table;

            // CHECK-IN ICON
            ImageIcon checkin = new ImageIcon(getClass().getResource("/img/check-in.png"));
            Image imgcheckin = checkin.getImage().getScaledInstance(18,18,Image.SCALE_SMOOTH);
            btnCheckin.setIcon(new ImageIcon(imgcheckin));

            // CHECKOUT ICON
            ImageIcon checkout = new ImageIcon(getClass().getResource("/img/checkout.png"));
            Image imgcheckout = checkout.getImage().getScaledInstance(18,18,Image.SCALE_SMOOTH);
            btnCheckout.setIcon(new ImageIcon(imgcheckout));

            // CANCEL ICON
            ImageIcon cancel = new ImageIcon(getClass().getResource("/img/cross-button.png"));
            Image imgcancel = cancel.getImage().getScaledInstance(18,18,Image.SCALE_SMOOTH);
            btnCancel.setIcon(new ImageIcon(imgcancel));
            
            
            panel.add(btnCheckin);
            panel.add(btnCancel);

            btnCheckin.addActionListener(e -> {

                int row = table.getEditingRow();
                handler.onCheckin(row);

                fireEditingStopped();
                table.repaint();
            });

            btnCancel.addActionListener(e -> {

                int row = table.getEditingRow();
                handler.onCancel(row);

                fireEditingStopped();
                table.repaint();
            });

            btnCheckout.addActionListener(e -> {

                int row = table.getEditingRow();
                handler.onCheckout(row);

                fireEditingStopped();
                table.repaint();
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table,
                Object value, boolean isSelected, int row, int column) {

            panel.removeAll();

            int statusColumn = table.getColumnModel().getColumnIndex("Status");
            String status = table.getValueAt(row, statusColumn).toString();

            switch(status){

                case "Confirmed":
                    panel.add(btnCheckin);
                    panel.add(btnCancel);
                    break;

                case "Checked-in":
                    panel.add(btnCheckout);
                    break;

                case "Checked-out":
                case "Cancelled":
                    break;
            }

            return panel;
        }

        @Override
        public Object getCellEditorValue(){
            return null;
        }

        public interface ActionHandler{

            void onCheckin(int row);
            void onCancel(int row);
            void onCheckout(int row);

        }
    }
}
