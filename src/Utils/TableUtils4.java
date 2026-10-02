/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author client
 */
public class TableUtils4 {
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
}
