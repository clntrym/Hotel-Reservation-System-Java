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
public class TableUtils2 {
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
    header.setPreferredSize(new Dimension(header.getWidth(), 37));
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

                case "Active" -> {
                    label.setBackground(new Color(39,174,96));
                    label.setForeground(Color.WHITE);
                }

                case "Inactive" -> {
                    label.setBackground(new Color(231,76,60));
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
        private final JButton btnEdit = new JButton();

        public ActionRenderer() {
            setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));

            ImageIcon editIcon = new ImageIcon(getClass().getResource("/img/edit.png"));
            Image imgEdit = editIcon.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH);
            btnEdit.setIcon(new ImageIcon(imgEdit));

            btnEdit.setBorderPainted(false);
            add(btnEdit);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            return this;
        }
    }

    // -------------------- ACTION EDITOR --------------------
    public static class ActionEditor extends AbstractCellEditor implements TableCellEditor {
        private JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        private JButton btnEdit = new JButton();

        public ActionEditor(JTable table, ActionHandler handler) {
            
            ImageIcon editIcon = new ImageIcon(getClass().getResource("/img/edit.png"));
            Image imgEdit = editIcon.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH);
            btnEdit.setIcon(new ImageIcon(imgEdit));

            btnEdit.setBorderPainted(false);
            panel.add(btnEdit);

            btnEdit.addActionListener(e -> {
                int row = table.getEditingRow();
                handler.onEdit(row);
                fireEditingStopped();
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
            return panel;
        }

        @Override
        public Object getCellEditorValue() {
            return null;
        }

        @Override
        public boolean isCellEditable(EventObject e) {
            return true;
        }

        // Interface to handle Edit/Delete clicks
        public interface ActionHandler {
            void onEdit(int row);
        }
    }
}
