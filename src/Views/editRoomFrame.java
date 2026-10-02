/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Views;

import Utils.RoundedBorder;
import Utils.UIStyles;
import Controllers.RoomsController;
import Models.Rooms;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Toolkit;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JOptionPane;

/**
 *
 * @author client
 */
public class editRoomFrame extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(editRoomFrame.class.getName());

    private int roomId;
    private String originalRoomNumber;
    private Runnable onUpdate;
    public editRoomFrame( Rooms room, int roomId, Runnable onUpdate) {
        initComponents();
        this.roomId = roomId;
        this.originalRoomNumber = room.getRoomNumber();
         this.onUpdate = onUpdate;
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        String roomNumber = room.getRoomNumber();
        String floor = roomNumber.substring(0,1);
        String roomNum = roomNumber.substring(1);

        txtRnumber.setText(roomNum);
        cbFnum.setSelectedItem(floor);
        txtRnumber.setText(room.getRoomNumber());
        cbFnum.setSelectedItem(room.getRoomFloor());
        cbCategory.setSelectedItem(room.getCategory());
        cbStatus.setSelectedItem(room.getStatus());
        txtPrice.setText(String.valueOf(room.getPrice()));
        txtCapacity.setText(room.getCapacity());
        
        setIconImage();
        txtRnumber.setOpaque(false);
        txtRnumber.setBackground(Color.WHITE);
        txtRnumber.setBorder(new RoundedBorder(18));
        txtPrice.setBorder(new RoundedBorder(18));
        txtCapacity.setBorder(new RoundedBorder(18));
        styleComboBox(cbFnum);
        styleComboBox(cbCategory);
        styleComboBox(cbStatus);
        UIStyles.applyRoundedButton(btnEdit);
    }
    
    private void setIconImage(){
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/leche.png")));
    }
    
    private void styleComboBox(JComboBox<String> combo) {

        combo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        combo.setBackground(Color.WHITE);
        combo.setForeground(Color.BLACK);
        combo.setFocusable(false);
        combo.setOpaque(false); // IMPORTANT

        combo.setUI(new javax.swing.plaf.basic.BasicComboBoxUI() {

            @Override
            public void paintCurrentValueBackground(Graphics g,
                                                    Rectangle bounds,
                                                    boolean hasFocus) {
                g.setColor(Color.WHITE);
                g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
            }

            @Override
            protected JButton createArrowButton() {
                JButton button = new JButton("▼");
                button.setBackground(Color.WHITE);
                button.setBorder(BorderFactory.createEmptyBorder());
                button.setFocusPainted(false);
                button.setContentAreaFilled(false);
                button.setForeground(new Color(0,102,204));
                return button;
            }
        });

        combo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(
                    JList<?> list,
                    Object value,
                    int index,
                    boolean isSelected,
                    boolean cellHasFocus) {

                super.getListCellRendererComponent(
                        list, value, index, isSelected, cellHasFocus);

                setBackground(Color.WHITE);
                setForeground(Color.BLACK);

                if (isSelected) {
                    setBackground(new Color(255,204,0));
                    setForeground(Color.BLACK);
                }

                return this;
            }
        });

        combo.setBorder(new RoundedBorder(20));
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtRnumber = new javax.swing.JTextField();
        cbFnum = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        cbCategory = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        cbStatus = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        txtCapacity = new javax.swing.JTextField();
        txtPrice = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        btnEdit = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("NCST Hotel Management System");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Arial Narrow", 1, 22)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(16, 53, 113));
        jLabel1.setText("Update Room");

        jLabel3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(63, 67, 70));
        jLabel3.setText("Room Number");

        cbFnum.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9" }));

        jLabel5.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(63, 67, 70));
        jLabel5.setText("Floor Number");

        cbCategory.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Standard", "Deluxe", "Suite" }));

        jLabel4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(63, 67, 70));
        jLabel4.setText("Category");

        jLabel6.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(63, 67, 70));
        jLabel6.setText("Status");

        cbStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Available", "Occupied", "Maintenance" }));

        jLabel8.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(63, 67, 70));
        jLabel8.setText("Capacity");

        jLabel7.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(63, 67, 70));
        jLabel7.setText("Price/Night (₱)");

        btnEdit.setText("Update Room");
        btnEdit.addActionListener(this::btnEditActionPerformed);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtRnumber, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel4)))
                            .addComponent(cbCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCapacity, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel8)))
                            .addComponent(cbFnum, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(btnEdit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel5)
                                .addGap(134, 134, 134)))))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel1)
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCapacity, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel5))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtRnumber, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cbFnum, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(jLabel4)
                                .addGap(46, 46, 46))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(cbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cbCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(26, 26, 26)
                .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(31, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        String Rnum = txtRnumber.getText().trim();
        String Fnum = cbFnum.getSelectedItem() == null ? "" : cbFnum.getSelectedItem().toString().trim();
        String category = cbCategory.getSelectedItem() == null ? "" : cbCategory.getSelectedItem().toString().trim();
        String status = cbStatus.getSelectedItem() == null ? "" : cbStatus.getSelectedItem().toString().trim();
        String price = txtPrice.getText().trim();
        String capacity = txtCapacity.getText().trim();
        String roomText = txtRnumber.getText().trim();

        RoomsController roomcon = new RoomsController();

        if (Rnum.isEmpty() || Fnum.isEmpty() || category.isEmpty()
            || status.isEmpty() || price.isEmpty() || capacity.isEmpty()) {

            JOptionPane.showMessageDialog(this,
                "All fields are required!",
                "Validation Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            //int room = Integer.parseInt(Rnum);
            int room = Integer.parseInt(roomText.substring(roomText.length() - 2));
            int floor = Integer.parseInt(Fnum);
            int prices = Integer.parseInt(price);
            int capacitys = Integer.parseInt(capacity);
            
            if (room < 1 || room > 10) {
                JOptionPane.showMessageDialog(this,
                    "Room number must be between 01 and 10!",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (floor < 1 || floor > 10) {
                JOptionPane.showMessageDialog(this,
                    "Floor number must be between 1 and 10!",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (prices < 1500 || prices > 10000) {
                JOptionPane.showMessageDialog(this,
                    "Price must be between 1500 and 10000!",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (capacitys < 1 || capacitys > 15) {
                JOptionPane.showMessageDialog(this,
                    "Room capacity must be between 1 and 15 persons!",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            String finalRoomNumber = floor + String.format("%02d", room);

            if(roomcon.isRoomNumberExistsExceptId(finalRoomNumber, roomId)){

                JOptionPane.showMessageDialog(this,
                    "Room number already exists!",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);

                return;
            }

            Rooms roomData = new Rooms();
            roomData.setRoomNumber(finalRoomNumber);
            roomData.setRoomFloor(String.valueOf(floor));
            roomData.setCategory(category);
            roomData.setStatus(status);
            roomData.setPrice(prices);
            roomData.setCapacity(capacity);

            roomcon.updateRoom(roomData, roomId);

            JOptionPane.showMessageDialog(this, "Room updated successfully!");
            
            if(onUpdate != null){
                onUpdate.run();
            }
            this.dispose();

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(this,
                "Room, Floor, Price and Capacity must be numbers!",
                "Validation Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnEditActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        //java.awt.EventQueue.invokeLater(() -> new editRoomFrame().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEdit;
    private javax.swing.JComboBox<String> cbCategory;
    private javax.swing.JComboBox<String> cbFnum;
    private javax.swing.JComboBox<String> cbStatus;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField txtCapacity;
    private javax.swing.JTextField txtPrice;
    private javax.swing.JTextField txtRnumber;
    // End of variables declaration//GEN-END:variables
}
