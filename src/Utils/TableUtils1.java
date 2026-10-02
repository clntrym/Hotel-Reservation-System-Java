 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

/**
 *
 * @author client
 */
import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.*;
import java.util.EventObject;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

public class TableUtils1 {
    
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

                case "Available" -> {
                    label.setBackground(new Color(39,174,96));
                    label.setForeground(Color.WHITE);
                }

                case "Occupied" -> {
                    label.setBackground(new Color(231,76,60));
                    label.setForeground(Color.WHITE);
                }

                case "Maintenance" -> {
                    label.setBackground(new Color(241,196,15));
                    label.setForeground(Color.BLACK);
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
        private final JButton btnDelete = new JButton();

        public ActionRenderer() {
            setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
            
            ImageIcon editIcon = new ImageIcon(getClass().getResource("/img/edit.png"));
            Image imgEdit = editIcon.getImage().getScaledInstance(18,18,Image.SCALE_SMOOTH);
            btnEdit.setIcon(new ImageIcon(imgEdit));

            ImageIcon deleteIcon = new ImageIcon(getClass().getResource("/img/delete.png"));
            Image imgDelete = deleteIcon.getImage().getScaledInstance(18,18,Image.SCALE_SMOOTH);
            btnDelete.setIcon(new ImageIcon(imgDelete));
            
            btnEdit.setBorderPainted(false);
            btnDelete.setBorderPainted(false);

            /*btnEdit.setContentAreaFilled(false);
            btnDelete.setContentAreaFilled(false);*/
        
            add(btnEdit);
            add(btnDelete);
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
        private JButton btnDelete = new JButton();

        public ActionEditor(JTable table, ActionHandler handler) {
            
            ImageIcon editIcon = new ImageIcon(getClass().getResource("/img/edit.png"));
            Image imgEdit = editIcon.getImage().getScaledInstance(18,18,Image.SCALE_SMOOTH);
            btnEdit.setIcon(new ImageIcon(imgEdit));

            ImageIcon deleteIcon = new ImageIcon(getClass().getResource("/img/delete.png"));
            Image imgDelete = deleteIcon.getImage().getScaledInstance(18,18,Image.SCALE_SMOOTH);
            btnDelete.setIcon(new ImageIcon(imgDelete));
            
            btnEdit.setBorderPainted(false);
            btnDelete.setBorderPainted(false);

            /*btnEdit.setContentAreaFilled(false);
            btnDelete.setContentAreaFilled(false);*/
            
            panel.add(btnEdit);
            panel.add(btnDelete);

            btnEdit.addActionListener(e -> {
                fireEditingStopped(); // 🔥 stop first
                int row = table.getSelectedRow(); // safer
                handler.onEdit(row);
            });

            btnDelete.addActionListener(e -> {
                fireEditingStopped(); // 🔥 stop first
                int row = table.getSelectedRow(); // safer
                handler.onDelete(row);
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
            void onDelete(int row);
        }
    }

}
