package Views;

import Utils.RoundedBorder;
import Utils.UIStyles;
import Controllers.UserController;
import Controllers.RoomsController;
import Controllers.ReservationController;
import Models.Reservations;
import Models.Rooms;
import Models.Users;
import Utils.TableUtils1;
import Utils.TableUtils3;
import java.util.List;
import java.util.ArrayList;
import static Views.AdminDashboard.setLabelImage;
import java.awt.AlphaComposite;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

public class frontDeskFrame extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(frontDeskFrame.class.getName());
    private javax.swing.JButton selectedButton = null;
    
    private int sessionUserId;
    private String sessionFirstname;
    private String sessionLastname;
    private String firstname;
    private String lastname;
    private int id;
    private List<Rooms> roomList;
    private Rooms selectedRoom;
    public frontDeskFrame(String firstname, String lastname, int id) {
        initComponents();
        setTitle("NCST Hotel Management System (FrontDesk)");
        this.sessionFirstname = firstname;
        this.sessionLastname = lastname;
        this.sessionUserId = id;
        txt_sessionFirstname.setText(firstname + " " + lastname);
        loadUserInformation();
        loadRoomCounts();
        loadRooms();
        initRoomTable();
        loadRoomTable();
        initReservationTable();
        loadReservationTable();
        loadPaymentTable();
        loadWeeklyRevenueChart();
        loadReservationStatusChart();
        
        //enterimg();
        setIconImage();
        setupSidebarButton(btnFdDashboard);
        setupSidebarButton(btnFdWalkin);
        setupSidebarButton(btnFdRooms);
        setupSidebarButton(btnFdReservation);
        setupSidebarButton(btnFdPayment);
        setupSidebarButton(btnFdLogout);
        setupSidebarButton(btnFdSettings);
        
        AutoIconResizer.setIcon(btnFdDashboard, "/img/dashboard2.png");
        AutoIconResizer.setIcon(btnFdWalkin, "/img/group2.png");
        AutoIconResizer.setIcon(btnFdRooms, "/img/bed2.png");
        AutoIconResizer.setIcon(btnFdReservation, "/img/bed2.png");
        AutoIconResizer.setIcon(btnFdPayment, "/img/bed2.png");
        AutoIconResizer.setIcon(btnFdSettings, "/img/setting.png");
        
        setLabelImage(stUser, "/img/stUser.png");
        setLabelImage(wiUser, "/img/add-user.png");
        setLabelImage(wiBook, "/img/calendar.png");
        setLabelImage(wiSummary, "/img/wallet.png");
        setLabelImage(rmBox1, "/img/standardbed.png");
        setLabelImage(rmBox2, "/img/deluxebed.png");
        setLabelImage(rmBox3, "/img/suitebed.png");
        setLabelImage(rmBox4, "/img/money.png");
        setLabelImage(rmBox5, "/img/online-payment.png");
        setLabelImage(rmBox6, "/img/credit-card.png");
        setLabelImage(logo, "/img/leche.png");
        setLabelImage(dbed, "/img/bed.png");
        setLabelImage(dAvail, "/img/check-mark.png");
        setLabelImage(dNoAvail, "/img/wrong.png");
        setLabelImage(dRev, "/img/money.png");
        
        styleDatePicker(jdCheckin);
        jdCheckin.setDateFormatString("MM/dd/yyyy");
        styleDatePicker(jdCheckout);
        jdCheckout.setDateFormatString("MM/dd/yyyy");
        jdCheckin.addPropertyChangeListener("date", evt -> computeTotal());
        jdCheckout.addPropertyChangeListener("date", evt -> computeTotal());
        
        rmB1.setBorder(new RoundedBorder(20));
        rmB2.setBorder(new RoundedBorder(20));
        rmB3.setBorder(new RoundedBorder(20));
        rmB4.setBorder(new RoundedBorder(20));
        rmB5.setBorder(new RoundedBorder(20));
        rmB6.setBorder(new RoundedBorder(20));
        rmB7.setBorder(new RoundedBorder(20));
        rmB8.setBorder(new RoundedBorder(20));
        rmB9.setBorder(new RoundedBorder(20));
        rmB10.setBorder(new RoundedBorder(20));
        rmB11.setBorder(new RoundedBorder(20));
        rmB12.setBorder(new RoundedBorder(20));
        rmB13.setBorder(new RoundedBorder(20));
        rmB14.setBorder(new RoundedBorder(20));
        psProfile.setBorder(new RoundedBorder(20));
        psProfile.setBackground(Color.WHITE);
        psPass.setBorder(new RoundedBorder(20));
        psPass.setBackground(Color.WHITE);
        jpCpass.setBorder(new RoundedBorder(20));
        jpNpass.setBorder(new RoundedBorder(20));
        jpNCpass.setBorder(new RoundedBorder(20));
        tfName.setBorder(new RoundedBorder(20));
        tfLastname.setBorder(new RoundedBorder(20));
        tfGmail.setBorder(new RoundedBorder(20));
        tfUname.setBorder(new RoundedBorder(20));
        tfRole.setBorder(new RoundedBorder(20));
        txtFname.setBorder(new RoundedBorder(20));
        txtLname.setBorder(new RoundedBorder(20));
        txtEmail.setBorder(new RoundedBorder(20));
        txtPhone.setBorder(new RoundedBorder(20));
        txtNumber.setBorder(new RoundedBorder(20));
        txtAmount.setBorder(new RoundedBorder(20));
        wiPanel.setBorder(new RoundedBorder(20));
        wiBilling.setBorder(new RoundedBorder(20));
        roomSearch.setBorder(new RoundedBorder(20));
        revSearch.setBorder(new RoundedBorder(20));
        paySearch.setBorder(new RoundedBorder(20));
        
        styleComboBox(cbType);
        styleComboBox(cbRoom);
        styleComboBox(cbPayment);
        
        UIStyles.applyRoundedButton(btnSaveProfile);
        UIStyles.applyRoundedButton(btnUpass);
        UIStyles.applyRoundedButton(btnCSummary);
    }
    
    private void loadWeeklyRevenueChart(){

        ReservationController rc = new ReservationController();
        DefaultCategoryDataset dataset = rc.getWeeklyRevenueDataset();

        JFreeChart chart = ChartFactory.createLineChart(
                "Weekly Revenue",
                "Day",
                "₱ Revenue",
                dataset
        );

        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.WHITE);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(413,322));
        chartPanel.setOpaque(false);

        panelChart.setBorder(new Utils.RoundedBorder(25));

        panelChart.removeAll();
        panelChart.setLayout(new BorderLayout());
        panelChart.add(chartPanel, BorderLayout.CENTER);

        panelChart.revalidate();
        panelChart.repaint();
    }
    private void loadReservationStatusChart(){

        ReservationController rc = new ReservationController();
        DefaultPieDataset dataset = rc.getReservationStatusDataset();

        JFreeChart chart = ChartFactory.createPieChart(
                "Reservation Status Distribution",
                dataset,
                true,
                true,
                false
        );

        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setBackgroundPaint(Color.WHITE);

        plot.setSectionPaint("Confirmed", new Color(52,152,219));
        plot.setSectionPaint("Checked-in", new Color(46,204,113));
        plot.setSectionPaint("Checked-out", new Color(149,165,166));
        plot.setSectionPaint("Cancelled", new Color(231,76,60));

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(413,322));
        chartPanel.setOpaque(false);

        panelPieChart.setBorder(new Utils.RoundedBorder(25));

        panelPieChart.removeAll();
        panelPieChart.setLayout(new BorderLayout());
        panelPieChart.add(chartPanel, BorderLayout.CENTER);

        panelPieChart.revalidate();
        panelPieChart.repaint();
    }
    
    private void loadRoomCounts() {

        RoomsController rc = new RoomsController();
        
        int standard = rc.countRoomsByCategory("Standard");
        int deluxe = rc.countRoomsByCategory("Deluxe");
        int suite = rc.countRoomsByCategory("Suite");
        int total = rc.getTotalRooms();
        
        lblStandardRooms.setText(String.valueOf(standard));
        lblDeluxeRooms.setText(String.valueOf(deluxe));
        lblSuiteRooms.setText(String.valueOf(suite));
        dTotal.setText(String.valueOf(total));
        
        int availableRooms = rc.countRoomsByStatus("Available");
        int occupiedRooms = rc.countRoomsByStatus("Occupied");
        
        daAvail.setText(String.valueOf(availableRooms));
        daOcc.setText(String.valueOf(occupiedRooms));
        
        ReservationController rsc = new ReservationController();
        
        double revenue = rsc.getTotalRevenue();
        daRev.setText("₱" + String.valueOf(revenue));
        
        int confirmed = rsc.countReservationByStatus("Confirmed");
        int checkin = rsc.countReservationByStatus("Checked-in");
        int checkout = rsc.countReservationByStatus("Checked-out");
        int cancel = rsc.countReservationByStatus("Cancelled");

        lblConfirmed.setText(String.valueOf(confirmed));
        lblCheckin.setText(String.valueOf(checkin));
        lblCheckout.setText(String.valueOf(checkout));
        lblCancel.setText(String.valueOf(cancel));
        
        int cash = rsc.countReservationByPayMethod("Cash");
        int gcash = rsc.countReservationByPayMethod("Gcash");
        int bank = rsc.countReservationByPayMethod("Bank");

        lblCash.setText(String.valueOf(cash));
        lblGcash.setText(String.valueOf(gcash));
        lblBank.setText(String.valueOf(bank));
    }
    
    private void clearForm(){

        txtFname.setText("");
        txtLname.setText("");
        txtEmail.setText("");
        txtPhone.setText("");
        txtNumber.setText("");

        jdCheckin.setDate(null);
        jdCheckout.setDate(null);

        lblRoom.setText("");
        lblRate.setText("");
        lblNights.setText("0");
        lblTotal.setText("0");

        txtAmount.setText("");

        cbRoom.setSelectedIndex(-1);
    }
    
    public void loadRooms(){

        RoomsController rc = new RoomsController();
        roomList = rc.getAvailableRooms();

        cbRoom.removeAllItems();

        for(Rooms room : roomList){
            cbRoom.addItem(room.getRoomNumber() + " - " + room.getCategory());
        }

        selectedRoom = null;

        lblRoom.setText("");
        lblRate.setText("");
        lblNights.setText("0");
        lblTotal.setText("0");
    }
    
    private void computeTotal() {
        if(selectedRoom == null) return;
        if(jdCheckin.getDate() == null || jdCheckout.getDate() == null) return;

        LocalDate checkin = jdCheckin.getDate().toInstant()
                .atZone(java.time.ZoneId.systemDefault())
                .toLocalDate();
        LocalDate checkout = jdCheckout.getDate().toInstant()
                .atZone(java.time.ZoneId.systemDefault())
                .toLocalDate();

        long nights = ChronoUnit.DAYS.between(checkin, checkout);
        if(nights <= 0){
            lblNights.setText("0");
            lblTotal.setText("0");
            txtAmount.setText("");
            return;
        }

        double total = nights * selectedRoom.getPrice();
        lblNights.setText(String.valueOf(nights));
        lblTotal.setText(String.valueOf(total));
        txtAmount.setText(String.valueOf(total));
    }
    
    public void loadPaymentTable(String search){
        DefaultTableModel model = (DefaultTableModel) table_payment.getModel();
        model.setRowCount(0);
        ReservationController revcon = new ReservationController();
        RoomsController roomcon = new RoomsController(); 

        List<Reservations> reservation = revcon.showTableRoom();
        String keyword = search.toLowerCase().trim();

        for(Reservations rev : reservation){
            Rooms room = roomcon.getRoomById(rev.getRoomId());

            String roomNumber = "";
            if(room != null){
                roomNumber = room.getRoomNumber();
            }
            
            if (!search.isEmpty() &&
                !roomNumber.toLowerCase().contains(keyword) &&
                !rev.getFirstname().toLowerCase().contains(keyword) &&
                !rev.getLastname().toLowerCase().contains(keyword) &&
                !rev.getPaymentMethod().toLowerCase().contains(keyword)) {
                continue;
            }
            
            model.addRow(new Object[]{
                rev.getRevId(),
                rev.getFirstname() + " " + rev.getLastname(),
                roomNumber,
                rev.getPaymentMethod(),
                rev.getPaymentAmount(),
                rev.getCreated_at()
            });
        }
        
        TableUtils1.styleTable(table_payment);
        JScrollPane scroll = (JScrollPane) table_payment.getParent().getParent();
        scroll.getVerticalScrollBar().setUI(new Utils.ModernScrollBar());
        scroll.getVerticalScrollBar().setPreferredSize(new Dimension(8, 0));
        scroll.setBorder(null);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        table_payment.getColumnModel().getColumn(0).setMinWidth(0);
        table_payment.getColumnModel().getColumn(0).setMaxWidth(0);
        table_payment.getColumnModel().getColumn(0).setWidth(0);
    }
    public void loadPaymentTable() {
       loadPaymentTable("");
    }
    
    public void loadReservationTable(String search){
        DefaultTableModel model = (DefaultTableModel) table_reserv.getModel();
        model.setRowCount(0);
        ReservationController revcon = new ReservationController();
        RoomsController roomcon = new RoomsController(); 

        List<Reservations> reservation = revcon.showTableRoom();
        String keyword = search.toLowerCase().trim();

        for(Reservations rev : reservation){
            Rooms room = roomcon.getRoomById(rev.getRoomId());

            String roomNumber = "";
            if(room != null){
                roomNumber = room.getRoomNumber();
            }
            if (!search.isEmpty() &&
                !roomNumber.toLowerCase().contains(keyword) &&
                !rev.getFirstname().toLowerCase().contains(keyword) &&
                !rev.getLastname().toLowerCase().contains(keyword) &&
                !rev.getStatus().toLowerCase().contains(keyword)) {
                continue;
            }

            model.addRow(new Object[]{
                rev.getRevId(),
                rev.getFirstname() + " " + rev.getLastname(),
                roomNumber,          
                rev.getCheckin(),
                rev.getCheckout(),
                rev.getPaymentAmount(),
                rev.getStatus(),
                "Edit | Delete"
            });
        }
        loadRoomCounts();
    }
    public void loadReservationTable() {
       loadReservationTable("");
    }
    
    private void initReservationTable() {

        TableUtils3.styleTable(table_reserv);
        JScrollPane scroll = (JScrollPane) table_reserv.getParent().getParent();
        scroll.getVerticalScrollBar().setUI(new Utils.ModernScrollBar());
        scroll.getVerticalScrollBar().setPreferredSize(new Dimension(8, 0));
        scroll.setBorder(null);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        table_reserv.getColumnModel().getColumn(0).setMinWidth(0);
        table_reserv.getColumnModel().getColumn(0).setMaxWidth(0);
        table_reserv.getColumnModel().getColumn(0).setWidth(0);

        table_reserv.getColumn("Status")
                .setCellRenderer(new TableUtils3.StatusRenderer());

        table_reserv.getColumn("Actions")
                .setCellRenderer(new TableUtils3.ActionRenderer());

        table_reserv.getColumn("Actions")
        .setCellEditor(new TableUtils3.ActionEditor(table_reserv,
        new TableUtils3.ActionEditor.ActionHandler(){

            @Override
            public void onCheckin(int row){

                int revId = Integer.parseInt(table_reserv.getValueAt(row,0).toString());

                ReservationController rc = new ReservationController();
                rc.updateStatus(revId,"Checked-in");

                if(table_reserv.isEditing()){
                    table_reserv.getCellEditor().stopCellEditing();
                }
                
                loadRoomTable();
                loadReservationTable();
                loadRoomCounts();
                loadWeeklyRevenueChart();
                loadReservationStatusChart();
            }

            @Override
            public void onCancel(int row){

                int revId = Integer.parseInt(table_reserv.getValueAt(row,0).toString());

                ReservationController rc = new ReservationController();
                rc.updateStatus(revId,"Cancelled");

                if(table_reserv.isEditing()){
                    table_reserv.getCellEditor().stopCellEditing();
                }
                
                loadRoomTable();
                loadReservationTable();
                loadRoomCounts();
                loadWeeklyRevenueChart();
                loadReservationStatusChart();
            }

            @Override
            public void onCheckout(int row){

                int revId = Integer.parseInt(table_reserv.getValueAt(row,0).toString());
                String roomNumber = table_reserv.getValueAt(row,2).toString();

                ReservationController rc = new ReservationController();
                rc.updateStatus(revId,"Checked-out");

                RoomsController roomCon = new RoomsController();
                roomCon.updateRoomStatus(roomNumber,"Available");

                if(table_reserv.isEditing()){
                    table_reserv.getCellEditor().stopCellEditing();
                }
                
                loadRoomTable();
                loadReservationTable();
                loadRoomCounts();
                loadWeeklyRevenueChart();
                loadReservationStatusChart();
            }
        }));
    }
    
    public void loadRoomTable(String search){

        DefaultTableModel model = (DefaultTableModel) table_rooms.getModel();
        model.setRowCount(0);

        RoomsController roomcon = new RoomsController();
        List<Rooms> rooms = roomcon.showTableRoom();
        String keyword = search.toLowerCase().trim();
        
        for(Rooms room : rooms){
            if (!search.isEmpty() &&
                !room.getRoomNumber().toLowerCase().contains(keyword) &&
                !room.getRoomFloor().toLowerCase().contains(keyword) &&
                !room.getCategory().toLowerCase().contains(keyword) &&
                !room.getStatus().toLowerCase().contains(keyword)) {
                continue;
            }
            
            model.addRow(new Object[]{
                room.getRoomId(), 
                room.getRoomNumber(),   
                room.getRoomFloor(),    
                room.getCategory(),     
                room.getCapacity(),     
                room.getPrice(),       
                room.getStatus(),       
                "Edit | Delete"         
            });
        }
        loadRoomCounts();
    }
    public void loadRoomTable() {
       loadRoomTable("");
    }
    
    private void initRoomTable() {

        TableUtils1.styleTable(table_rooms);
        
        JScrollPane scroll = (JScrollPane) table_rooms.getParent().getParent();
        scroll.getVerticalScrollBar().setUI(new Utils.ModernScrollBar());
        scroll.getVerticalScrollBar().setPreferredSize(new Dimension(8, 0));
        scroll.setBorder(null);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        table_rooms.getColumnModel().getColumn(0).setMinWidth(0);
        table_rooms.getColumnModel().getColumn(0).setMaxWidth(0);
        table_rooms.getColumnModel().getColumn(0).setWidth(0);

        table_rooms.getColumn("Status")
                .setCellRenderer(new TableUtils1.StatusRenderer());

        table_rooms.getColumn("Actions")
                .setCellRenderer(new TableUtils1.ActionRenderer());

        table_rooms.getColumn("Actions")
                .setCellEditor(new TableUtils1.ActionEditor(table_rooms,
                new TableUtils1.ActionEditor.ActionHandler() {

            @Override
            public void onEdit(int row) {

                int roomId = Integer.parseInt(table_rooms.getValueAt(row, 0).toString());
                RoomsController roomcon = new RoomsController();
                Rooms room = roomcon.getRoomById(roomId); 

                if(room != null){
                    editRoomFrame er = new editRoomFrame(room, roomId, () -> loadRoomTable());
                    er.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Room data not found!");
                }

                loadRoomTable();
            }

            @Override
            public void onDelete(int row) {

                int confirm = JOptionPane.showConfirmDialog(null,
                        "Are you sure you want to delete this room?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {

                    String roomNumber = table_rooms.getValueAt(row, 1).toString();

                    RoomsController roomcon = new RoomsController();
                    roomcon.deleteRoom(roomNumber);

                    JOptionPane.showMessageDialog(null,
                            "Room deleted.");

                    loadRoomTable();
                }
            }
        }));
    }
    
    private void loadUserInformation(){

        UserController usercon = new UserController();
        Models.Users user = usercon.getUserById(sessionUserId);

        if(user != null){
            tfName.setText(user.getFirstname());
            tfLastname.setText(user.getLastname());
            tfGmail.setText(user.getGmail());
            tfUname.setText(user.getUsername());
            tfRole.setText(user.getRoles());
        }
    }
    
    public void enterimg(){
        ImageIcon icon = new ImageIcon(getClass().getResource("/img/leche.png"));
        Image img = icon.getImage().getScaledInstance(logo.getWidth(), logo.getHeight(), Image.SCALE_SMOOTH);
        logo.setIcon(new ImageIcon(img));
    }
    
    private void setIconImage(){
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/leche.png")));
    }
    
    public static class AutoIconResizer {
        public static void setIcon(JButton btn, String path){
            ImageIcon originalIcon = new ImageIcon(AdminDashboard.class.getResource(path));
            Image img = originalIcon.getImage().getScaledInstance(24,24, Image.SCALE_SMOOTH);
            ImageIcon defaultIcon = new ImageIcon(img);
            ImageIcon whiteIcon = new ImageIcon(makeWhite(img));
            
            btn.setIcon(defaultIcon);
            btn.putClientProperty("defaultIcon", defaultIcon);
            btn.putClientProperty("activeIcon", whiteIcon);
            btn.setHorizontalAlignment(SwingConstants.LEFT);
            btn.setHorizontalTextPosition(SwingConstants.RIGHT);
            btn.setVerticalTextPosition(SwingConstants.CENTER);
            btn.setIconTextGap(15);
            btn.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 10));
            
        }
        private static Image makeWhite(Image img){
            BufferedImage buffered = new BufferedImage(24, 24, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = buffered.createGraphics();
            g2.drawImage(img, 0, 0, null);
            g2.setComposite(AlphaComposite.SrcAtop);
            g2.setColor(new Color(16,53,113));
            g2.fillRect(0,0,24,24);
            g2.dispose();
            return buffered;
        }
    }
    
    private void setupSidebarButton(JButton btn) {
        Color defaultTextColor = Color.white;
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(false);
        Color defaultBg = new Color(0,0,0,0);
        Color activeBg = new Color(245,192,34);
        Color defaultIcon = new Color(245,192,34);
        btn.setBackground(defaultBg);

        btn.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
            @Override
            public void paint(Graphics g, javax.swing.JComponent c) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(btn.getBackground());
                g2.fillRoundRect(0, 0, btn.getWidth(), btn.getHeight(), 30, 30);

                super.paint(g2, c);
                g2.dispose();
            }
        });
        
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (selectedButton != btn) {
                    btn.setBackground(new Color(245,192,34));
                }
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (selectedButton != btn) {
                    btn.setOpaque(false);
                    btn.setBackground(new Color(0,0,0,0));
                }
            }
        });

        btn.addActionListener(e -> {
            if (selectedButton != null) {
                selectedButton.setOpaque(false);
                selectedButton.setBackground(new Color(0,0,0,0));
                selectedButton.setForeground(defaultTextColor);
                
                selectedButton.setIcon((ImageIcon) selectedButton.getClientProperty("defaultIcon"));
            }

            btn.setBackground(new Color(245,192,34));
            btn.setForeground(new Color(16,53,113));
            btn.setIcon((ImageIcon) btn.getClientProperty("activeIcon"));
            selectedButton = btn;
        });
    }
    
    public static void styleDatePicker(JDateChooser dateChooser) {

        dateChooser.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        dateChooser.setBorder(new RoundedBorder(18));
        dateChooser.setBackground(Color.WHITE);

        JTextField textField = ((JTextField) dateChooser.getDateEditor().getUiComponent());
        textField.setBorder(null);
        textField.setBackground(Color.WHITE);
        textField.setForeground(Color.BLACK);
    }
    
    private void styleComboBox(JComboBox<String> combo) {

        combo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        combo.setBackground(Color.WHITE);
        combo.setForeground(Color.BLACK);
        combo.setFocusable(false);
        combo.setOpaque(false); 

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

        jPanel2 = new javax.swing.JPanel();
        Sidebar8 = new javax.swing.JPanel();
        btnFdDashboard = new javax.swing.JButton();
        jSeparator17 = new javax.swing.JSeparator();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        btnFdRooms = new javax.swing.JButton();
        btnFdPayment = new javax.swing.JButton();
        btnFdWalkin = new javax.swing.JButton();
        logo = new javax.swing.JLabel();
        txt_sessionFirstname = new javax.swing.JLabel();
        btnFdLogout = new javax.swing.JButton();
        jSeparator18 = new javax.swing.JSeparator();
        jLabel29 = new javax.swing.JLabel();
        btnFdReservation = new javax.swing.JButton();
        btnFdSettings = new javax.swing.JButton();
        mainPanel = new javax.swing.JPanel();
        dashboard = new javax.swing.JPanel();
        panelChart = new javax.swing.JPanel();
        rmB11 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        dTotal = new javax.swing.JLabel();
        dbed = new javax.swing.JLabel();
        rmB12 = new javax.swing.JPanel();
        jLabel54 = new javax.swing.JLabel();
        daAvail = new javax.swing.JLabel();
        dAvail = new javax.swing.JLabel();
        rmB13 = new javax.swing.JPanel();
        daOcc = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        dNoAvail = new javax.swing.JLabel();
        rmB14 = new javax.swing.JPanel();
        daRev = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        dRev = new javax.swing.JLabel();
        panelPieChart = new javax.swing.JPanel();
        jLabel35 = new javax.swing.JLabel();
        jLabel62 = new javax.swing.JLabel();
        walkin = new javax.swing.JPanel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        wiPanel = new javax.swing.JPanel();
        wiUser = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        txtFname = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        txtLname = new javax.swing.JTextField();
        jLabel43 = new javax.swing.JLabel();
        txtPhone = new javax.swing.JTextField();
        jLabel44 = new javax.swing.JLabel();
        txtNumber = new javax.swing.JTextField();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        cbType = new javax.swing.JComboBox<>();
        jLabel47 = new javax.swing.JLabel();
        cbPayment = new javax.swing.JComboBox<>();
        wiBook = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jdCheckin = new com.toedter.calendar.JDateChooser();
        jdCheckout = new com.toedter.calendar.JDateChooser();
        jLabel50 = new javax.swing.JLabel();
        cbRoom = new javax.swing.JComboBox<>();
        txtAmount = new javax.swing.JTextField();
        jLabel48 = new javax.swing.JLabel();
        wiBilling = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        wiSummary = new javax.swing.JLabel();
        lblRooms = new javax.swing.JLabel();
        lblRates = new javax.swing.JLabel();
        lblNightss = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        lblTotals = new javax.swing.JLabel();
        btnCSummary = new javax.swing.JButton();
        lblRoom = new javax.swing.JLabel();
        lblRate = new javax.swing.JLabel();
        lblNights = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        rooms = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        rmB1 = new javax.swing.JPanel();
        rmBox1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lblStandardRooms = new javax.swing.JLabel();
        rmB2 = new javax.swing.JPanel();
        rmBox2 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        lblDeluxeRooms = new javax.swing.JLabel();
        rmB3 = new javax.swing.JPanel();
        rmBox3 = new javax.swing.JLabel();
        lblSuiteRooms = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_rooms = new javax.swing.JTable();
        roomSearch = new javax.swing.JTextField();
        reservation = new javax.swing.JPanel();
        jLabel51 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        rmB4 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        lblConfirmed = new javax.swing.JLabel();
        rmB5 = new javax.swing.JPanel();
        jLabel53 = new javax.swing.JLabel();
        lblCheckin = new javax.swing.JLabel();
        rmB6 = new javax.swing.JPanel();
        lblCancel = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        rmB7 = new javax.swing.JPanel();
        lblCheckout = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        table_reserv = new javax.swing.JTable();
        revSearch = new javax.swing.JTextField();
        payment = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        rmB8 = new javax.swing.JPanel();
        rmBox4 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lblCash = new javax.swing.JLabel();
        rmB9 = new javax.swing.JPanel();
        rmBox5 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        lblGcash = new javax.swing.JLabel();
        rmB10 = new javax.swing.JPanel();
        rmBox6 = new javax.swing.JLabel();
        lblBank = new javax.swing.JLabel();
        Bank = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        table_payment = new javax.swing.JTable();
        paySearch = new javax.swing.JTextField();
        setting = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        psProfile = new javax.swing.JPanel();
        stUser = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        tfName = new javax.swing.JTextField();
        tfLastname = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        tfUname = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        tfRole = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        btnSaveProfile = new javax.swing.JButton();
        jLabel31 = new javax.swing.JLabel();
        tfGmail = new javax.swing.JTextField();
        psPass = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jpCpass = new javax.swing.JPasswordField();
        jLabel37 = new javax.swing.JLabel();
        jpNpass = new javax.swing.JPasswordField();
        jpNCpass = new javax.swing.JPasswordField();
        jLabel38 = new javax.swing.JLabel();
        btnUpass = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel2.setBackground(new java.awt.Color(250, 250, 250));

        Sidebar8.setBackground(new java.awt.Color(0, 34, 76));
        Sidebar8.setForeground(new java.awt.Color(255, 255, 255));

        btnFdDashboard.setBackground(new java.awt.Color(245, 192, 34));
        btnFdDashboard.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        btnFdDashboard.setForeground(new java.awt.Color(255, 255, 255));
        btnFdDashboard.setText("Dashboard");
        btnFdDashboard.setBorder(null);
        btnFdDashboard.setBorderPainted(false);
        btnFdDashboard.setContentAreaFilled(false);
        btnFdDashboard.setIconTextGap(5);
        btnFdDashboard.addActionListener(this::btnFdDashboardActionPerformed);

        jLabel27.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(251, 189, 35));
        jLabel27.setText("NCST Hotel");

        jLabel28.setFont(new java.awt.Font("Arial", 2, 14)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(209, 213, 219));
        jLabel28.setText("Management System");

        btnFdRooms.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        btnFdRooms.setForeground(new java.awt.Color(255, 255, 255));
        btnFdRooms.setText("Rooms");
        btnFdRooms.setBorder(null);
        btnFdRooms.setBorderPainted(false);
        btnFdRooms.setContentAreaFilled(false);
        btnFdRooms.addActionListener(this::btnFdRoomsActionPerformed);

        btnFdPayment.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        btnFdPayment.setForeground(new java.awt.Color(255, 255, 255));
        btnFdPayment.setText("Payment");
        btnFdPayment.setBorder(null);
        btnFdPayment.setBorderPainted(false);
        btnFdPayment.setContentAreaFilled(false);
        btnFdPayment.setFocusPainted(false);
        btnFdPayment.addActionListener(this::btnFdPaymentActionPerformed);

        btnFdWalkin.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        btnFdWalkin.setForeground(new java.awt.Color(255, 255, 255));
        btnFdWalkin.setText("Walk-In");
        btnFdWalkin.setBorder(null);
        btnFdWalkin.setBorderPainted(false);
        btnFdWalkin.setContentAreaFilled(false);
        btnFdWalkin.setFocusPainted(false);
        btnFdWalkin.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnFdWalkin.addActionListener(this::btnFdWalkinActionPerformed);

        txt_sessionFirstname.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        txt_sessionFirstname.setForeground(new java.awt.Color(255, 255, 255));

        btnFdLogout.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        btnFdLogout.setForeground(new java.awt.Color(255, 255, 255));
        btnFdLogout.setText("Sign Out");
        btnFdLogout.setBorder(null);
        btnFdLogout.setBorderPainted(false);
        btnFdLogout.setContentAreaFilled(false);
        btnFdLogout.setFocusPainted(false);
        btnFdLogout.addActionListener(this::btnFdLogoutActionPerformed);

        jLabel29.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(185, 195, 203));
        jLabel29.setText("Front Desk");

        btnFdReservation.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        btnFdReservation.setForeground(new java.awt.Color(255, 255, 255));
        btnFdReservation.setText("Reservations");
        btnFdReservation.setBorder(null);
        btnFdReservation.setBorderPainted(false);
        btnFdReservation.setContentAreaFilled(false);
        btnFdReservation.setFocusPainted(false);
        btnFdReservation.addActionListener(this::btnFdReservationActionPerformed);

        btnFdSettings.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        btnFdSettings.setForeground(new java.awt.Color(255, 255, 255));
        btnFdSettings.setText("Settings");
        btnFdSettings.setBorder(null);
        btnFdSettings.setBorderPainted(false);
        btnFdSettings.setContentAreaFilled(false);
        btnFdSettings.setFocusPainted(false);
        btnFdSettings.addActionListener(this::btnFdSettingsActionPerformed);

        javax.swing.GroupLayout Sidebar8Layout = new javax.swing.GroupLayout(Sidebar8);
        Sidebar8.setLayout(Sidebar8Layout);
        Sidebar8Layout.setHorizontalGroup(
            Sidebar8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator17, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jSeparator18)
            .addGroup(Sidebar8Layout.createSequentialGroup()
                .addGroup(Sidebar8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(Sidebar8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(Sidebar8Layout.createSequentialGroup()
                            .addGap(8, 8, 8)
                            .addComponent(logo, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(Sidebar8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(Sidebar8Layout.createSequentialGroup()
                            .addGap(16, 16, 16)
                            .addComponent(btnFdDashboard, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(Sidebar8Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(Sidebar8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnFdWalkin, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(Sidebar8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(btnFdLogout, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnFdPayment, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
                                .addComponent(btnFdRooms, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnFdReservation, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
                                .addComponent(btnFdSettings, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE))
                            .addComponent(txt_sessionFirstname, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel29))))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        Sidebar8Layout.setVerticalGroup(
            Sidebar8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Sidebar8Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(Sidebar8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(Sidebar8Layout.createSequentialGroup()
                        .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel28)
                        .addGap(8, 8, 8))
                    .addComponent(logo, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addComponent(jSeparator17, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(btnFdDashboard, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnFdWalkin, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnFdRooms, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(btnFdReservation, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnFdPayment, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnFdSettings, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jSeparator18, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(txt_sessionFirstname, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel29)
                .addGap(18, 18, 18)
                .addComponent(btnFdLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );

        mainPanel.setBackground(new java.awt.Color(250, 250, 250));
        mainPanel.setLayout(new java.awt.CardLayout());

        dashboard.setBackground(new java.awt.Color(250, 250, 250));

        panelChart.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout panelChartLayout = new javax.swing.GroupLayout(panelChart);
        panelChart.setLayout(panelChartLayout);
        panelChartLayout.setHorizontalGroup(
            panelChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 517, Short.MAX_VALUE)
        );
        panelChartLayout.setVerticalGroup(
            panelChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 385, Short.MAX_VALUE)
        );

        rmB11.setBackground(new java.awt.Color(255, 255, 255));
        rmB11.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, java.awt.Color.lightGray));
        rmB11.setOpaque(false);

        jLabel9.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(118, 139, 148));
        jLabel9.setText("Total Rooms");

        dTotal.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        dTotal.setText("45");

        javax.swing.GroupLayout rmB11Layout = new javax.swing.GroupLayout(rmB11);
        rmB11.setLayout(rmB11Layout);
        rmB11Layout.setHorizontalGroup(
            rmB11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rmB11Layout.createSequentialGroup()
                .addGroup(rmB11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(rmB11Layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rmB11Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(dTotal)
                        .addGap(64, 64, 64)))
                .addComponent(dbed, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
        );
        rmB11Layout.setVerticalGroup(
            rmB11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rmB11Layout.createSequentialGroup()
                .addGroup(rmB11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(rmB11Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(dTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel9))
                    .addGroup(rmB11Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(dbed, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        rmB12.setBackground(new java.awt.Color(255, 255, 255));
        rmB12.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, java.awt.Color.lightGray));
        rmB12.setOpaque(false);

        jLabel54.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        jLabel54.setForeground(new java.awt.Color(118, 139, 148));
        jLabel54.setText("Available");

        daAvail.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        daAvail.setText("45");

        javax.swing.GroupLayout rmB12Layout = new javax.swing.GroupLayout(rmB12);
        rmB12.setLayout(rmB12Layout);
        rmB12Layout.setHorizontalGroup(
            rmB12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rmB12Layout.createSequentialGroup()
                .addGroup(rmB12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(rmB12Layout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addComponent(jLabel54)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rmB12Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(daAvail, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(51, 51, 51)))
                .addComponent(dAvail, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );
        rmB12Layout.setVerticalGroup(
            rmB12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rmB12Layout.createSequentialGroup()
                .addContainerGap(36, Short.MAX_VALUE)
                .addComponent(daAvail, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel54)
                .addGap(15, 15, 15))
            .addGroup(rmB12Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(dAvail, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        rmB13.setBackground(new java.awt.Color(255, 255, 255));
        rmB13.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, java.awt.Color.lightGray));
        rmB13.setOpaque(false);

        daOcc.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        daOcc.setText("45");

        jLabel60.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        jLabel60.setForeground(new java.awt.Color(118, 139, 148));
        jLabel60.setText("Occupied");

        javax.swing.GroupLayout rmB13Layout = new javax.swing.GroupLayout(rmB13);
        rmB13.setLayout(rmB13Layout);
        rmB13Layout.setHorizontalGroup(
            rmB13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rmB13Layout.createSequentialGroup()
                .addGroup(rmB13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(rmB13Layout.createSequentialGroup()
                        .addGap(66, 66, 66)
                        .addComponent(daOcc, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(rmB13Layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addComponent(jLabel60)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                .addComponent(dNoAvail, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
        );
        rmB13Layout.setVerticalGroup(
            rmB13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rmB13Layout.createSequentialGroup()
                .addGroup(rmB13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(rmB13Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(daOcc, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel60))
                    .addGroup(rmB13Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(dNoAvail, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        rmB14.setBackground(new java.awt.Color(255, 255, 255));
        rmB14.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, java.awt.Color.lightGray));
        rmB14.setOpaque(false);

        daRev.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        daRev.setText("45");

        jLabel57.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        jLabel57.setForeground(new java.awt.Color(118, 139, 148));
        jLabel57.setText("Revenue");

        javax.swing.GroupLayout rmB14Layout = new javax.swing.GroupLayout(rmB14);
        rmB14.setLayout(rmB14Layout);
        rmB14Layout.setHorizontalGroup(
            rmB14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rmB14Layout.createSequentialGroup()
                .addGroup(rmB14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(rmB14Layout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addComponent(jLabel57)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rmB14Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(daRev)
                        .addGap(33, 33, 33)))
                .addComponent(dRev, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
        );
        rmB14Layout.setVerticalGroup(
            rmB14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rmB14Layout.createSequentialGroup()
                .addGroup(rmB14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(rmB14Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(daRev, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel57))
                    .addGroup(rmB14Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(dRev, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelPieChart.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout panelPieChartLayout = new javax.swing.GroupLayout(panelPieChart);
        panelPieChart.setLayout(panelPieChartLayout);
        panelPieChartLayout.setHorizontalGroup(
            panelPieChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelPieChartLayout.setVerticalGroup(
            panelPieChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jLabel35.setBackground(new java.awt.Color(16, 53, 113));
        jLabel35.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(16, 53, 113));
        jLabel35.setText("Front Desk Dashboard");

        jLabel62.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel62.setForeground(new java.awt.Color(118, 139, 148));
        jLabel62.setText("Monitor your hotel operations and manage your business efficiently");

        javax.swing.GroupLayout dashboardLayout = new javax.swing.GroupLayout(dashboard);
        dashboard.setLayout(dashboardLayout);
        dashboardLayout.setHorizontalGroup(
            dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dashboardLayout.createSequentialGroup()
                .addGroup(dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dashboardLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(dashboardLayout.createSequentialGroup()
                                .addComponent(rmB11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(32, 32, 32)
                                .addComponent(rmB12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(28, 28, 28)
                                .addComponent(rmB13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(28, 28, 28)
                                .addComponent(rmB14, javax.swing.GroupLayout.PREFERRED_SIZE, 217, Short.MAX_VALUE))
                            .addGroup(dashboardLayout.createSequentialGroup()
                                .addComponent(panelChart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(panelPieChart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addComponent(jLabel35)
                    .addComponent(jLabel62))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        dashboardLayout.setVerticalGroup(
            dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dashboardLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel35)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel62)
                .addGap(35, 35, 35)
                .addGroup(dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(rmB14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(rmB13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(rmB11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(rmB12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelChart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelPieChart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(79, Short.MAX_VALUE))
        );

        mainPanel.add(dashboard, "dashboard");

        walkin.setBackground(new java.awt.Color(250, 250, 250));

        jLabel39.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(118, 139, 148));
        jLabel39.setText("Register a walk-in guest and create a booking");

        jLabel40.setBackground(new java.awt.Color(16, 53, 113));
        jLabel40.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(16, 53, 113));
        jLabel40.setText("Walk-In Reservation");

        wiPanel.setBackground(new java.awt.Color(255, 255, 255));

        jLabel3.setFont(new java.awt.Font("Rockwell", 1, 16)); // NOI18N
        jLabel3.setText("Guest Information");

        jLabel30.setFont(new java.awt.Font("Poppins SemiBold", 0, 15)); // NOI18N
        jLabel30.setText("First Name");

        jLabel41.setFont(new java.awt.Font("Poppins SemiBold", 0, 15)); // NOI18N
        jLabel41.setText("Email");

        jLabel42.setFont(new java.awt.Font("Poppins SemiBold", 0, 15)); // NOI18N
        jLabel42.setText("ID Type");

        jLabel43.setFont(new java.awt.Font("Poppins SemiBold", 0, 15)); // NOI18N
        jLabel43.setText("Last Name");

        jLabel44.setFont(new java.awt.Font("Poppins SemiBold", 0, 15)); // NOI18N
        jLabel44.setText("Phone #");

        jLabel45.setFont(new java.awt.Font("Poppins SemiBold", 0, 15)); // NOI18N
        jLabel45.setText("ID Number");

        jLabel46.setFont(new java.awt.Font("Poppins SemiBold", 0, 15)); // NOI18N
        jLabel46.setText("Room");

        cbType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "National ID", "Passport ID" }));

        jLabel47.setFont(new java.awt.Font("Poppins SemiBold", 0, 15)); // NOI18N
        jLabel47.setText("Payment Method");

        cbPayment.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Cash", "Gcash", "Bank" }));

        jLabel4.setFont(new java.awt.Font("Rockwell", 1, 16)); // NOI18N
        jLabel4.setText("Booking Details");

        jLabel49.setFont(new java.awt.Font("Poppins SemiBold", 0, 15)); // NOI18N
        jLabel49.setText("Check-in");

        jLabel50.setFont(new java.awt.Font("Poppins SemiBold", 0, 15)); // NOI18N
        jLabel50.setText("Check-out");

        cbRoom.addActionListener(this::cbRoomActionPerformed);

        jLabel48.setFont(new java.awt.Font("Poppins SemiBold", 0, 15)); // NOI18N
        jLabel48.setText("Payment Amount");

        javax.swing.GroupLayout wiPanelLayout = new javax.swing.GroupLayout(wiPanel);
        wiPanel.setLayout(wiPanelLayout);
        wiPanelLayout.setHorizontalGroup(
            wiPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(wiPanelLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(wiPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(wiPanelLayout.createSequentialGroup()
                        .addComponent(wiBook, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(wiPanelLayout.createSequentialGroup()
                        .addGroup(wiPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel49)
                            .addGroup(wiPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jdCheckin, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(cbType, javax.swing.GroupLayout.Alignment.LEADING, 0, 262, Short.MAX_VALUE)
                                .addComponent(txtEmail, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel30, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtFname, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel46, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel42, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel41, javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, wiPanelLayout.createSequentialGroup()
                                    .addComponent(wiUser, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel3))
                                .addComponent(cbRoom, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 73, Short.MAX_VALUE)
                        .addGroup(wiPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel43)
                            .addComponent(txtLname, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel44)
                            .addComponent(txtPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel45)
                            .addComponent(txtNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbPayment, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(wiPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jdCheckout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(wiPanelLayout.createSequentialGroup()
                                    .addComponent(jLabel50)
                                    .addGap(180, 180, 180)))
                            .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(32, 32, 32))
                    .addGroup(wiPanelLayout.createSequentialGroup()
                        .addGroup(wiPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel48)
                            .addComponent(txtAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        wiPanelLayout.setVerticalGroup(
            wiPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(wiPanelLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(wiPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(wiUser, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(wiPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(wiPanelLayout.createSequentialGroup()
                        .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtFname, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(wiPanelLayout.createSequentialGroup()
                        .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtLname, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(wiPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(wiPanelLayout.createSequentialGroup()
                        .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(wiPanelLayout.createSequentialGroup()
                        .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(wiPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(wiPanelLayout.createSequentialGroup()
                        .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cbType, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(wiPanelLayout.createSequentialGroup()
                        .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(wiPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(wiPanelLayout.createSequentialGroup()
                        .addGroup(wiPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(wiBook, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26)
                        .addComponent(jLabel49, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jdCheckin, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(wiPanelLayout.createSequentialGroup()
                        .addComponent(jLabel50, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jdCheckout, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(wiPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(wiPanelLayout.createSequentialGroup()
                        .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbPayment, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(wiPanelLayout.createSequentialGroup()
                        .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbRoom, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel48, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        wiBilling.setBackground(new java.awt.Color(255, 255, 255));

        jLabel5.setFont(new java.awt.Font("Rockwell", 1, 16)); // NOI18N
        jLabel5.setText("Billing Summary");

        lblRooms.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        lblRooms.setText("Room");

        lblRates.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        lblRates.setText("Rate/Night");

        lblNightss.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        lblNightss.setText("Nights");

        lblTotals.setFont(new java.awt.Font("Rockwell", 1, 16)); // NOI18N
        lblTotals.setText("Total");

        btnCSummary.setText("Confirm");
        btnCSummary.addActionListener(this::btnCSummaryActionPerformed);

        lblRoom.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        lblRoom.setText("Deluxe");

        lblRate.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        lblRate.setText("1500");

        lblNights.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        lblNights.setText("2 Days");

        lblTotal.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        lblTotal.setText("1500");

        javax.swing.GroupLayout wiBillingLayout = new javax.swing.GroupLayout(wiBilling);
        wiBilling.setLayout(wiBillingLayout);
        wiBillingLayout.setHorizontalGroup(
            wiBillingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(wiBillingLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(wiBillingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(wiBillingLayout.createSequentialGroup()
                        .addComponent(lblTotals, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(wiBillingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(wiBillingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jSeparator1)
                            .addComponent(btnCSummary, javax.swing.GroupLayout.DEFAULT_SIZE, 277, Short.MAX_VALUE)
                            .addComponent(lblNightss, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(wiBillingLayout.createSequentialGroup()
                                .addComponent(wiSummary, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblRates, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(wiBillingLayout.createSequentialGroup()
                            .addComponent(lblRooms, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(97, 97, 97)
                            .addGroup(wiBillingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblRoom, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(wiBillingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(lblNights, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
                                    .addComponent(lblRate, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        wiBillingLayout.setVerticalGroup(
            wiBillingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(wiBillingLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(wiBillingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(wiBillingLayout.createSequentialGroup()
                        .addGroup(wiBillingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(wiSummary, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(wiBillingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblRoom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblRooms, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblRates))
                    .addComponent(lblRate, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(wiBillingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblNightss)
                    .addComponent(lblNights, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(wiBillingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTotals)
                    .addComponent(lblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addComponent(btnCSummary, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout walkinLayout = new javax.swing.GroupLayout(walkin);
        walkin.setLayout(walkinLayout);
        walkinLayout.setHorizontalGroup(
            walkinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(walkinLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(walkinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(walkinLayout.createSequentialGroup()
                        .addGroup(walkinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel40)
                            .addComponent(jLabel39))
                        .addContainerGap(742, Short.MAX_VALUE))
                    .addGroup(walkinLayout.createSequentialGroup()
                        .addComponent(wiPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(wiBilling, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39))))
        );
        walkinLayout.setVerticalGroup(
            walkinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(walkinLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel40)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel39)
                .addGap(30, 30, 30)
                .addGroup(walkinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(wiBilling, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(wiPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(31, Short.MAX_VALUE))
        );

        mainPanel.add(walkin, "walkin");

        rooms.setBackground(new java.awt.Color(250, 250, 250));

        jLabel10.setBackground(new java.awt.Color(16, 53, 113));
        jLabel10.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(16, 53, 113));
        jLabel10.setText("Room Management");

        jLabel11.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(118, 139, 148));
        jLabel11.setText("Manage hotel rooms, categories, and availability");

        rmB1.setBackground(new java.awt.Color(255, 255, 255));
        rmB1.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, java.awt.Color.lightGray));
        rmB1.setOpaque(false);

        jLabel6.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(118, 139, 148));
        jLabel6.setText("Standard Rooms");

        lblStandardRooms.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        lblStandardRooms.setText("45");

        javax.swing.GroupLayout rmB1Layout = new javax.swing.GroupLayout(rmB1);
        rmB1.setLayout(rmB1Layout);
        rmB1Layout.setHorizontalGroup(
            rmB1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rmB1Layout.createSequentialGroup()
                .addContainerGap(53, Short.MAX_VALUE)
                .addGroup(rmB1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rmB1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(33, 33, 33))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rmB1Layout.createSequentialGroup()
                        .addComponent(lblStandardRooms, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(50, 50, 50)))
                .addComponent(rmBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
        );
        rmB1Layout.setVerticalGroup(
            rmB1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rmB1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(rmB1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rmB1Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(lblStandardRooms, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6)
                        .addGap(14, 14, 14))
                    .addGroup(rmB1Layout.createSequentialGroup()
                        .addComponent(rmBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        rmB2.setBackground(new java.awt.Color(255, 255, 255));
        rmB2.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, java.awt.Color.lightGray));
        rmB2.setOpaque(false);

        jLabel13.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(118, 139, 148));
        jLabel13.setText("Deluxe Rooms");

        lblDeluxeRooms.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        lblDeluxeRooms.setText("45");

        javax.swing.GroupLayout rmB2Layout = new javax.swing.GroupLayout(rmB2);
        rmB2.setLayout(rmB2Layout);
        rmB2Layout.setHorizontalGroup(
            rmB2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rmB2Layout.createSequentialGroup()
                .addContainerGap(48, Short.MAX_VALUE)
                .addGroup(rmB2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rmB2Layout.createSequentialGroup()
                        .addComponent(lblDeluxeRooms, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(66, 66, 66))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rmB2Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addGap(58, 58, 58)))
                .addComponent(rmBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );
        rmB2Layout.setVerticalGroup(
            rmB2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rmB2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(rmB2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rmB2Layout.createSequentialGroup()
                        .addComponent(rmBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(23, 23, 23))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rmB2Layout.createSequentialGroup()
                        .addComponent(lblDeluxeRooms, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel13)
                        .addGap(15, 15, 15))))
        );

        rmB3.setBackground(new java.awt.Color(255, 255, 255));
        rmB3.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, java.awt.Color.lightGray));
        rmB3.setOpaque(false);

        lblSuiteRooms.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        lblSuiteRooms.setText("45");

        jLabel16.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(118, 139, 148));
        jLabel16.setText("Suite Rooms");

        javax.swing.GroupLayout rmB3Layout = new javax.swing.GroupLayout(rmB3);
        rmB3.setLayout(rmB3Layout);
        rmB3Layout.setHorizontalGroup(
            rmB3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rmB3Layout.createSequentialGroup()
                .addContainerGap(66, Short.MAX_VALUE)
                .addGroup(rmB3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblSuiteRooms, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(55, 55, 55)
                .addComponent(rmBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        rmB3Layout.setVerticalGroup(
            rmB3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rmB3Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(rmB3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(rmB3Layout.createSequentialGroup()
                        .addComponent(lblSuiteRooms, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel16))
                    .addComponent(rmBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        table_rooms.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "roomId", "Room #", "Floor", "Category", "Capacity", "Price/Night", "Status", "Actions"
            }
        ));
        jScrollPane1.setViewportView(table_rooms);

        roomSearch.addActionListener(this::roomSearchActionPerformed);
        roomSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                roomSearchKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout roomsLayout = new javax.swing.GroupLayout(rooms);
        rooms.setLayout(roomsLayout);
        roomsLayout.setHorizontalGroup(
            roomsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roomsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roomsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roomsLayout.createSequentialGroup()
                        .addGroup(roomsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11))
                        .addContainerGap(722, Short.MAX_VALUE))
                    .addGroup(roomsLayout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addGroup(roomsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1)
                            .addGroup(roomsLayout.createSequentialGroup()
                                .addComponent(rmB1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(rmB2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(60, 60, 60)
                                .addComponent(rmB3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(roomSearch, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(29, 29, 29))))
        );
        roomsLayout.setVerticalGroup(
            roomsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roomsLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11)
                .addGap(18, 18, 18)
                .addGroup(roomsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(rmB1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(rmB2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(rmB3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(roomSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 397, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(55, Short.MAX_VALUE))
        );

        mainPanel.add(rooms, "rooms");

        reservation.setBackground(new java.awt.Color(250, 250, 250));

        jLabel51.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel51.setForeground(new java.awt.Color(118, 139, 148));
        jLabel51.setText("Track and manage all guest reservations");

        jLabel52.setBackground(new java.awt.Color(16, 53, 113));
        jLabel52.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        jLabel52.setForeground(new java.awt.Color(16, 53, 113));
        jLabel52.setText("Reservations");

        rmB4.setBackground(new java.awt.Color(255, 255, 255));
        rmB4.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, java.awt.Color.lightGray));
        rmB4.setOpaque(false);

        jLabel7.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(118, 139, 148));
        jLabel7.setText("Confirmed");

        lblConfirmed.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        lblConfirmed.setText("45");

        javax.swing.GroupLayout rmB4Layout = new javax.swing.GroupLayout(rmB4);
        rmB4.setLayout(rmB4Layout);
        rmB4Layout.setHorizontalGroup(
            rmB4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rmB4Layout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addComponent(jLabel7)
                .addContainerGap(66, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rmB4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblConfirmed, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(75, 75, 75))
        );
        rmB4Layout.setVerticalGroup(
            rmB4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rmB4Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(lblConfirmed, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        rmB5.setBackground(new java.awt.Color(255, 255, 255));
        rmB5.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, java.awt.Color.lightGray));
        rmB5.setOpaque(false);

        jLabel53.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        jLabel53.setForeground(new java.awt.Color(118, 139, 148));
        jLabel53.setText("Checked-In");

        lblCheckin.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        lblCheckin.setText("45");

        javax.swing.GroupLayout rmB5Layout = new javax.swing.GroupLayout(rmB5);
        rmB5.setLayout(rmB5Layout);
        rmB5Layout.setHorizontalGroup(
            rmB5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rmB5Layout.createSequentialGroup()
                .addContainerGap(62, Short.MAX_VALUE)
                .addGroup(rmB5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rmB5Layout.createSequentialGroup()
                        .addComponent(jLabel53)
                        .addGap(60, 60, 60))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rmB5Layout.createSequentialGroup()
                        .addComponent(lblCheckin, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(70, 70, 70))))
        );
        rmB5Layout.setVerticalGroup(
            rmB5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rmB5Layout.createSequentialGroup()
                .addContainerGap(36, Short.MAX_VALUE)
                .addComponent(lblCheckin, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel53)
                .addGap(15, 15, 15))
        );

        rmB6.setBackground(new java.awt.Color(255, 255, 255));
        rmB6.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, java.awt.Color.lightGray));
        rmB6.setOpaque(false);

        lblCancel.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        lblCancel.setText("45");

        jLabel56.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        jLabel56.setForeground(new java.awt.Color(118, 139, 148));
        jLabel56.setText("Cancelled");

        javax.swing.GroupLayout rmB6Layout = new javax.swing.GroupLayout(rmB6);
        rmB6.setLayout(rmB6Layout);
        rmB6Layout.setHorizontalGroup(
            rmB6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rmB6Layout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addComponent(jLabel56)
                .addContainerGap(63, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rmB6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55))
        );
        rmB6Layout.setVerticalGroup(
            rmB6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rmB6Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(lblCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel56)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        rmB7.setBackground(new java.awt.Color(255, 255, 255));
        rmB7.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, java.awt.Color.lightGray));
        rmB7.setOpaque(false);

        lblCheckout.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        lblCheckout.setText("45");

        jLabel58.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        jLabel58.setForeground(new java.awt.Color(118, 139, 148));
        jLabel58.setText("Checked-Out");

        javax.swing.GroupLayout rmB7Layout = new javax.swing.GroupLayout(rmB7);
        rmB7.setLayout(rmB7Layout);
        rmB7Layout.setHorizontalGroup(
            rmB7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rmB7Layout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addComponent(jLabel58)
                .addContainerGap(50, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rmB7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblCheckout, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(58, 58, 58))
        );
        rmB7Layout.setVerticalGroup(
            rmB7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rmB7Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(lblCheckout, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel58)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        table_reserv.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Reservation ID", "Full Name", "Room", "Check-in", "Check-out", "Amount", "Status", "Actions"
            }
        ));
        jScrollPane2.setViewportView(table_reserv);

        revSearch.addActionListener(this::revSearchActionPerformed);
        revSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                revSearchKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout reservationLayout = new javax.swing.GroupLayout(reservation);
        reservation.setLayout(reservationLayout);
        reservationLayout.setHorizontalGroup(
            reservationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(reservationLayout.createSequentialGroup()
                .addGroup(reservationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(reservationLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(reservationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel52)
                            .addComponent(jLabel51)))
                    .addGroup(reservationLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(reservationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2)
                            .addGroup(reservationLayout.createSequentialGroup()
                                .addComponent(rmB4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 102, Short.MAX_VALUE)
                                .addComponent(rmB5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(39, 39, 39)
                                .addComponent(rmB7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(40, 40, 40)
                                .addComponent(rmB6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(revSearch))))
                .addGap(41, 41, 41))
        );
        reservationLayout.setVerticalGroup(
            reservationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(reservationLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel52)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel51)
                .addGap(18, 18, 18)
                .addGroup(reservationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(reservationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(rmB6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(rmB7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(rmB4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(rmB5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(revSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 383, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(66, Short.MAX_VALUE))
        );

        mainPanel.add(reservation, "reservation");

        payment.setBackground(new java.awt.Color(255, 255, 255));

        jLabel32.setBackground(new java.awt.Color(16, 53, 113));
        jLabel32.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(16, 53, 113));
        jLabel32.setText("Payments");

        jLabel59.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel59.setForeground(new java.awt.Color(118, 139, 148));
        jLabel59.setText("Payment records and billing management");

        rmB8.setBackground(new java.awt.Color(255, 255, 255));
        rmB8.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, java.awt.Color.lightGray));
        rmB8.setOpaque(false);

        jLabel8.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(118, 139, 148));
        jLabel8.setText("Cash");

        lblCash.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        lblCash.setText("45");

        javax.swing.GroupLayout rmB8Layout = new javax.swing.GroupLayout(rmB8);
        rmB8.setLayout(rmB8Layout);
        rmB8Layout.setHorizontalGroup(
            rmB8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rmB8Layout.createSequentialGroup()
                .addContainerGap(90, Short.MAX_VALUE)
                .addGroup(rmB8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rmB8Layout.createSequentialGroup()
                        .addComponent(lblCash, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rmB8Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(60, 60, 60)))
                .addComponent(rmBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
        );
        rmB8Layout.setVerticalGroup(
            rmB8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rmB8Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(rmB8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rmB8Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(lblCash, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8)
                        .addGap(14, 14, 14))
                    .addGroup(rmB8Layout.createSequentialGroup()
                        .addComponent(rmBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        rmB9.setBackground(new java.awt.Color(255, 255, 255));
        rmB9.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, java.awt.Color.lightGray));
        rmB9.setOpaque(false);

        jLabel61.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        jLabel61.setForeground(new java.awt.Color(118, 139, 148));
        jLabel61.setText("Gcash");

        lblGcash.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        lblGcash.setText("45");

        javax.swing.GroupLayout rmB9Layout = new javax.swing.GroupLayout(rmB9);
        rmB9.setLayout(rmB9Layout);
        rmB9Layout.setHorizontalGroup(
            rmB9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rmB9Layout.createSequentialGroup()
                .addContainerGap(95, Short.MAX_VALUE)
                .addGroup(rmB9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rmB9Layout.createSequentialGroup()
                        .addComponent(lblGcash, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rmB9Layout.createSequentialGroup()
                        .addComponent(jLabel61)
                        .addGap(65, 65, 65)))
                .addComponent(rmBox5, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );
        rmB9Layout.setVerticalGroup(
            rmB9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rmB9Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(rmB9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rmB9Layout.createSequentialGroup()
                        .addComponent(rmBox5, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(23, 23, 23))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rmB9Layout.createSequentialGroup()
                        .addComponent(lblGcash, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel61)
                        .addGap(15, 15, 15))))
        );

        rmB10.setBackground(new java.awt.Color(255, 255, 255));
        rmB10.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, java.awt.Color.lightGray));
        rmB10.setOpaque(false);

        lblBank.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        lblBank.setText("45");

        Bank.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        Bank.setForeground(new java.awt.Color(118, 139, 148));
        Bank.setText("Bank");

        javax.swing.GroupLayout rmB10Layout = new javax.swing.GroupLayout(rmB10);
        rmB10.setLayout(rmB10Layout);
        rmB10Layout.setHorizontalGroup(
            rmB10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rmB10Layout.createSequentialGroup()
                .addContainerGap(95, Short.MAX_VALUE)
                .addGroup(rmB10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rmB10Layout.createSequentialGroup()
                        .addComponent(lblBank, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rmB10Layout.createSequentialGroup()
                        .addComponent(Bank, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(52, 52, 52)))
                .addComponent(rmBox6, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        rmB10Layout.setVerticalGroup(
            rmB10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rmB10Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(rmB10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(rmB10Layout.createSequentialGroup()
                        .addComponent(lblBank, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Bank))
                    .addComponent(rmBox6, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        table_payment.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "id", "Guess Name", "Room", "Payment Method", "Amount", "Date"
            }
        ));
        jScrollPane3.setViewportView(table_payment);

        paySearch.addActionListener(this::paySearchActionPerformed);
        paySearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                paySearchKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout paymentLayout = new javax.swing.GroupLayout(payment);
        payment.setLayout(paymentLayout);
        paymentLayout.setHorizontalGroup(
            paymentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paymentLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(paymentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel32)
                    .addComponent(jLabel59))
                .addGap(56, 779, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, paymentLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(paymentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(paySearch)
                    .addComponent(jScrollPane3)
                    .addGroup(paymentLayout.createSequentialGroup()
                        .addComponent(rmB8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(rmB9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(77, 77, 77)
                        .addComponent(rmB10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(51, 51, 51))
        );
        paymentLayout.setVerticalGroup(
            paymentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paymentLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel32)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel59)
                .addGap(18, 18, 18)
                .addGroup(paymentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(rmB8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(rmB9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(rmB10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(paySearch, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 409, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(46, Short.MAX_VALUE))
        );

        mainPanel.add(payment, "payment");

        setting.setBackground(new java.awt.Color(250, 250, 250));

        jLabel17.setBackground(new java.awt.Color(16, 53, 113));
        jLabel17.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(16, 53, 113));
        jLabel17.setText("Settings");

        jLabel18.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(118, 139, 148));
        jLabel18.setText("Manage your profile and system settings");

        jLabel19.setFont(new java.awt.Font("Californian FB", 1, 24)); // NOI18N
        jLabel19.setText("My Profile ");

        psProfile.setBackground(new java.awt.Color(255, 255, 255));

        jLabel20.setFont(new java.awt.Font("Rockwell", 1, 16)); // NOI18N
        jLabel20.setText("Personal Information");

        jLabel21.setFont(new java.awt.Font("Poppins", 0, 13)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(118, 139, 148));
        jLabel21.setText("Update your name and username");

        jLabel22.setFont(new java.awt.Font("Poppins SemiBold", 0, 15)); // NOI18N
        jLabel22.setText("First Name");

        jLabel23.setFont(new java.awt.Font("Poppins SemiBold", 0, 15)); // NOI18N
        jLabel23.setText("Last Name");

        jLabel24.setFont(new java.awt.Font("Poppins SemiBold", 0, 15)); // NOI18N
        jLabel24.setText("Username");

        tfRole.setEditable(false);

        jLabel25.setFont(new java.awt.Font("Poppins SemiBold", 0, 15)); // NOI18N
        jLabel25.setText("Role");

        btnSaveProfile.setText("Save Profile");
        btnSaveProfile.addActionListener(this::btnSaveProfileActionPerformed);

        jLabel31.setFont(new java.awt.Font("Poppins SemiBold", 0, 15)); // NOI18N
        jLabel31.setText("Gmail");

        javax.swing.GroupLayout psProfileLayout = new javax.swing.GroupLayout(psProfile);
        psProfile.setLayout(psProfileLayout);
        psProfileLayout.setHorizontalGroup(
            psProfileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(psProfileLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(psProfileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel31)
                    .addComponent(tfRole)
                    .addComponent(jLabel25)
                    .addComponent(tfUname)
                    .addComponent(jLabel24)
                    .addComponent(tfLastname)
                    .addComponent(jLabel21)
                    .addGroup(psProfileLayout.createSequentialGroup()
                        .addComponent(stUser, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel20))
                    .addComponent(tfName)
                    .addComponent(jLabel23)
                    .addComponent(jLabel22)
                    .addComponent(btnSaveProfile, javax.swing.GroupLayout.DEFAULT_SIZE, 459, Short.MAX_VALUE)
                    .addComponent(tfGmail))
                .addContainerGap(36, Short.MAX_VALUE))
        );
        psProfileLayout.setVerticalGroup(
            psProfileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(psProfileLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(psProfileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(stUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel21)
                .addGap(18, 18, 18)
                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tfName, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tfLastname, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tfGmail, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tfUname, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tfRole, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSaveProfile, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        psPass.setBackground(new java.awt.Color(255, 255, 255));
        psPass.setPreferredSize(new java.awt.Dimension(489, 445));

        jLabel26.setFont(new java.awt.Font("Poppins", 0, 13)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(118, 139, 148));
        jLabel26.setText("Update your account password");

        jLabel33.setFont(new java.awt.Font("Rockwell", 1, 16)); // NOI18N
        jLabel33.setText("Change Password");

        jLabel34.setFont(new java.awt.Font("Poppins SemiBold", 0, 15)); // NOI18N
        jLabel34.setText("Current Password");

        jLabel37.setFont(new java.awt.Font("Poppins SemiBold", 0, 15)); // NOI18N
        jLabel37.setText("New Password");

        jLabel38.setFont(new java.awt.Font("Poppins SemiBold", 0, 15)); // NOI18N
        jLabel38.setText("Confirm Password");

        btnUpass.setText("Update Password");
        btnUpass.addActionListener(this::btnUpassActionPerformed);

        javax.swing.GroupLayout psPassLayout = new javax.swing.GroupLayout(psPass);
        psPass.setLayout(psPassLayout);
        psPassLayout.setHorizontalGroup(
            psPassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(psPassLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(psPassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jpNpass, javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(psPassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(btnUpass, javax.swing.GroupLayout.DEFAULT_SIZE, 401, Short.MAX_VALUE)
                        .addComponent(jLabel38)
                        .addComponent(jpNCpass)
                        .addComponent(jLabel37)
                        .addComponent(jLabel34)
                        .addComponent(jLabel33)
                        .addComponent(jLabel26)
                        .addComponent(jpCpass)))
                .addContainerGap(39, Short.MAX_VALUE))
        );
        psPassLayout.setVerticalGroup(
            psPassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(psPassLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel33, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel26)
                .addGap(26, 26, 26)
                .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpCpass, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpNpass, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpNCpass, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(btnUpass, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );

        javax.swing.GroupLayout settingLayout = new javax.swing.GroupLayout(setting);
        setting.setLayout(settingLayout);
        settingLayout.setHorizontalGroup(
            settingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(settingLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(settingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(settingLayout.createSequentialGroup()
                        .addGroup(settingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17)
                            .addComponent(jLabel19))
                        .addContainerGap(766, Short.MAX_VALUE))
                    .addGroup(settingLayout.createSequentialGroup()
                        .addComponent(psProfile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(psPass, javax.swing.GroupLayout.PREFERRED_SIZE, 459, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40))))
        );
        settingLayout.setVerticalGroup(
            settingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(settingLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel18)
                .addGap(27, 27, 27)
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(settingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(psPass, javax.swing.GroupLayout.PREFERRED_SIZE, 408, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(psProfile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(46, Short.MAX_VALUE))
        );

        mainPanel.add(setting, "settings");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(Sidebar8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Sidebar8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnFdDashboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFdDashboardActionPerformed
        CardLayout cl = (CardLayout)(mainPanel.getLayout());
        cl.show(mainPanel, "dashboard");
    }//GEN-LAST:event_btnFdDashboardActionPerformed

    private void btnFdRoomsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFdRoomsActionPerformed
        CardLayout cl = (CardLayout)(mainPanel.getLayout());
        cl.show(mainPanel, "rooms");
    }//GEN-LAST:event_btnFdRoomsActionPerformed

    private void btnFdPaymentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFdPaymentActionPerformed
        CardLayout cl = (CardLayout)(mainPanel.getLayout());
        cl.show(mainPanel, "payment");
    }//GEN-LAST:event_btnFdPaymentActionPerformed

    private void btnFdWalkinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFdWalkinActionPerformed
        CardLayout cl = (CardLayout)(mainPanel.getLayout());
        cl.show(mainPanel, "walkin");
    }//GEN-LAST:event_btnFdWalkinActionPerformed

    private void btnFdLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFdLogoutActionPerformed
        new loginFrame().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnFdLogoutActionPerformed

    private void btnFdReservationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFdReservationActionPerformed
        CardLayout cl = (CardLayout)(mainPanel.getLayout());
        cl.show(mainPanel, "reservation");
    }//GEN-LAST:event_btnFdReservationActionPerformed

    private void btnFdSettingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFdSettingsActionPerformed
        CardLayout cl = (CardLayout)(mainPanel.getLayout());
        cl.show(mainPanel, "settings");
    }//GEN-LAST:event_btnFdSettingsActionPerformed

    private void btnSaveProfileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveProfileActionPerformed
        String firstname = tfName.getText();
        String lastname = tfLastname.getText();
        String gmail = tfGmail.getText();
        String username = tfUname.getText();

        Users user = new Users();
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setGmail(gmail);
        user.setUsername(username);

        UserController usercon = new UserController();
        usercon.updateUserInformation(user, sessionUserId);

        JOptionPane.showMessageDialog(this, "Profile Updated Successfully!");
        loadUserInformation();
    }//GEN-LAST:event_btnSaveProfileActionPerformed

    private void btnUpassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpassActionPerformed
        String currentPass = new String(jpCpass.getPassword());
        String newPass = new String(jpNpass.getPassword());
        String confirmPass = new String(jpNCpass.getPassword());

        if(!newPass.equals(confirmPass)){
            JOptionPane.showMessageDialog(this,"New password and confirm password do not match!");
            return;
        }

        UserController usercon = new UserController();

        boolean updated = usercon.changePassword(sessionUserId, currentPass, newPass);

        if(updated){
            JOptionPane.showMessageDialog(this,"Password Updated Successfully!");

            jpCpass.setText("");
            jpNpass.setText("");
            jpNCpass.setText("");
        }
        else{
            JOptionPane.showMessageDialog(this,"Current password is incorrect!");
        }
    }//GEN-LAST:event_btnUpassActionPerformed

    private void btnCSummaryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCSummaryActionPerformed
        if(selectedRoom == null){
            JOptionPane.showMessageDialog(this,"Please select a room.");
            return;
        }

        if(txtFname.getText().trim().isEmpty() || txtLname.getText().trim().isEmpty() ||
           txtEmail.getText().trim().isEmpty() || txtPhone.getText().trim().isEmpty() ||
           txtNumber.getText().trim().isEmpty() || cbType.getSelectedIndex() == -1 ||
           cbPayment.getSelectedIndex() == -1){
            JOptionPane.showMessageDialog(this,"Please fill in all required fields.");
            return;
        }
        
        String fname = txtFname.getText().trim();
        String lname = txtLname.getText().trim();

        if (!fname.matches("^[A-Za-z\\s-]{2,50}$")) {
            JOptionPane.showMessageDialog(this, "First name must contain only letters (2-50 characters).");
            return;
        }

        if (!lname.matches("^[A-Za-z\\s-]{2,50}$")) {
            JOptionPane.showMessageDialog(this, "Last name must contain only letters (2-50 characters).");
            return;
        }

        if(jdCheckin.getDate() == null || jdCheckout.getDate() == null){
            JOptionPane.showMessageDialog(this,"Please select check-in and check-out dates.");
            return;
        }

        LocalDate today = LocalDate.now();
        LocalDate checkinDate = jdCheckin.getDate().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        LocalDate checkoutDate = jdCheckout.getDate().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        if(checkinDate.isBefore(today)){
            JOptionPane.showMessageDialog(this,"Check-in date cannot be in the past.");
            return;
        }

        if(!checkoutDate.isAfter(checkinDate)){
            JOptionPane.showMessageDialog(this,"Check-out date must be after check-in date.");
            return;
        }

        String email = txtEmail.getText().trim();
        if(!email.matches("^[\\w.-]+@[\\w.-]+\\.[A-Za-z]{2,6}$")){
            JOptionPane.showMessageDialog(this,"Please enter a valid email address.");
            return;
        }

        String phone = txtPhone.getText().trim();
        if(!phone.matches("\\d{7,15}")){
            JOptionPane.showMessageDialog(this,"Please enter a valid phone number (7-15 digits).");
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(txtAmount.getText().trim());
            if(amount <= 0){
                JOptionPane.showMessageDialog(this,"Amount must be greater than 0.");
                return;
            }
        } catch(NumberFormatException ex){
            JOptionPane.showMessageDialog(this,"Please enter a valid numeric amount.");
            return;
        }


        Reservations res = new Reservations();
        res.setRoomId(selectedRoom.getRoomId());
        res.setFirstname(txtFname.getText());
        res.setLastname(txtLname.getText());
        res.setEmail(txtEmail.getText());
        res.setPhone(txtPhone.getText());
        res.setTypeId(cbType.getSelectedItem().toString());
        res.setNumId(txtNumber.getText());
        res.setCheckin(new java.sql.Date(jdCheckin.getDate().getTime()));
        res.setCheckout(new java.sql.Date(jdCheckout.getDate().getTime()));
        res.setPaymentMethod(cbPayment.getSelectedItem().toString());
        res.setPaymentAmount(Double.parseDouble(txtAmount.getText()));

        ReservationController rc = new ReservationController();
        rc.addReservation(res);

        JOptionPane.showMessageDialog(this, "Reservation added successfully for room " 
                + selectedRoom.getRoomNumber() + "!", "Success", JOptionPane.INFORMATION_MESSAGE);

        loadRooms();
        clearForm();
        loadRoomTable();
        loadReservationTable();
        loadPaymentTable();
        loadWeeklyRevenueChart();
        loadReservationStatusChart();
        loadRoomCounts();
    }//GEN-LAST:event_btnCSummaryActionPerformed

    private void cbRoomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbRoomActionPerformed
        int index = cbRoom.getSelectedIndex();

        if(index >= 0 && index < roomList.size()){

            selectedRoom = roomList.get(index);

            lblRoom.setText(selectedRoom.getRoomNumber());
            lblRate.setText(String.valueOf(selectedRoom.getPrice()));

            computeTotal();
        }
    }//GEN-LAST:event_cbRoomActionPerformed

    private void roomSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_roomSearchActionPerformed
        loadRoomTable(roomSearch.getText().trim());
    }//GEN-LAST:event_roomSearchActionPerformed

    private void roomSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_roomSearchKeyReleased
        loadRoomTable(roomSearch.getText().trim());
    }//GEN-LAST:event_roomSearchKeyReleased

    private void revSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_revSearchActionPerformed
        loadReservationTable(revSearch.getText().trim());
    }//GEN-LAST:event_revSearchActionPerformed

    private void revSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_revSearchKeyReleased
        loadReservationTable(revSearch.getText().trim());
    }//GEN-LAST:event_revSearchKeyReleased

    private void paySearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_paySearchActionPerformed
        loadPaymentTable(paySearch.getText().trim());
    }//GEN-LAST:event_paySearchActionPerformed

    private void paySearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_paySearchKeyReleased
        loadPaymentTable(paySearch.getText().trim());
    }//GEN-LAST:event_paySearchKeyReleased

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
        java.awt.EventQueue.invokeLater(() -> {
        new loginFrame().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Bank;
    private javax.swing.JPanel Sidebar8;
    private javax.swing.JButton btnCSummary;
    private javax.swing.JButton btnFdDashboard;
    private javax.swing.JButton btnFdLogout;
    private javax.swing.JButton btnFdPayment;
    private javax.swing.JButton btnFdReservation;
    private javax.swing.JButton btnFdRooms;
    private javax.swing.JButton btnFdSettings;
    private javax.swing.JButton btnFdWalkin;
    private javax.swing.JButton btnSaveProfile;
    private javax.swing.JButton btnUpass;
    private javax.swing.JComboBox<String> cbPayment;
    private javax.swing.JComboBox<String> cbRoom;
    private javax.swing.JComboBox<String> cbType;
    private javax.swing.JLabel dAvail;
    private javax.swing.JLabel dNoAvail;
    private javax.swing.JLabel dRev;
    private javax.swing.JLabel dTotal;
    private javax.swing.JLabel daAvail;
    private javax.swing.JLabel daOcc;
    private javax.swing.JLabel daRev;
    private javax.swing.JPanel dashboard;
    private javax.swing.JLabel dbed;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator17;
    private javax.swing.JSeparator jSeparator18;
    private com.toedter.calendar.JDateChooser jdCheckin;
    private com.toedter.calendar.JDateChooser jdCheckout;
    private javax.swing.JPasswordField jpCpass;
    private javax.swing.JPasswordField jpNCpass;
    private javax.swing.JPasswordField jpNpass;
    private javax.swing.JLabel lblBank;
    private javax.swing.JLabel lblCancel;
    private javax.swing.JLabel lblCash;
    private javax.swing.JLabel lblCheckin;
    private javax.swing.JLabel lblCheckout;
    private javax.swing.JLabel lblConfirmed;
    private javax.swing.JLabel lblDeluxeRooms;
    private javax.swing.JLabel lblGcash;
    private javax.swing.JLabel lblNights;
    private javax.swing.JLabel lblNightss;
    private javax.swing.JLabel lblRate;
    private javax.swing.JLabel lblRates;
    private javax.swing.JLabel lblRoom;
    private javax.swing.JLabel lblRooms;
    private javax.swing.JLabel lblStandardRooms;
    private javax.swing.JLabel lblSuiteRooms;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JLabel lblTotals;
    private javax.swing.JLabel logo;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPanel panelChart;
    private javax.swing.JPanel panelPieChart;
    private javax.swing.JTextField paySearch;
    private javax.swing.JPanel payment;
    private javax.swing.JPanel psPass;
    private javax.swing.JPanel psProfile;
    private javax.swing.JPanel reservation;
    private javax.swing.JTextField revSearch;
    private javax.swing.JPanel rmB1;
    private javax.swing.JPanel rmB10;
    private javax.swing.JPanel rmB11;
    private javax.swing.JPanel rmB12;
    private javax.swing.JPanel rmB13;
    private javax.swing.JPanel rmB14;
    private javax.swing.JPanel rmB2;
    private javax.swing.JPanel rmB3;
    private javax.swing.JPanel rmB4;
    private javax.swing.JPanel rmB5;
    private javax.swing.JPanel rmB6;
    private javax.swing.JPanel rmB7;
    private javax.swing.JPanel rmB8;
    private javax.swing.JPanel rmB9;
    private javax.swing.JLabel rmBox1;
    private javax.swing.JLabel rmBox2;
    private javax.swing.JLabel rmBox3;
    private javax.swing.JLabel rmBox4;
    private javax.swing.JLabel rmBox5;
    private javax.swing.JLabel rmBox6;
    private javax.swing.JTextField roomSearch;
    private javax.swing.JPanel rooms;
    private javax.swing.JPanel setting;
    private javax.swing.JLabel stUser;
    private javax.swing.JTable table_payment;
    private javax.swing.JTable table_reserv;
    private javax.swing.JTable table_rooms;
    private javax.swing.JTextField tfGmail;
    private javax.swing.JTextField tfLastname;
    private javax.swing.JTextField tfName;
    private javax.swing.JTextField tfRole;
    private javax.swing.JTextField tfUname;
    private javax.swing.JTextField txtAmount;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtFname;
    private javax.swing.JTextField txtLname;
    private javax.swing.JTextField txtNumber;
    private javax.swing.JTextField txtPhone;
    private javax.swing.JLabel txt_sessionFirstname;
    private javax.swing.JPanel walkin;
    private javax.swing.JPanel wiBilling;
    private javax.swing.JLabel wiBook;
    private javax.swing.JPanel wiPanel;
    private javax.swing.JLabel wiSummary;
    private javax.swing.JLabel wiUser;
    // End of variables declaration//GEN-END:variables
}
