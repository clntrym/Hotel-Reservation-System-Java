/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Views;

import Controllers.ReservationController;
import Utils.TableUtils1;
import Utils.RoundedBorder;
import Utils.UIStyles;
import Controllers.UserController;
import Controllers.RoomsController;
import DataBase.DBConnection;
import Models.Reservations;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import Models.Rooms;
import Models.Users;
import Utils.TableUtils2;
import Utils.TableUtils3;
import Utils.TableUtils4;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.DefaultCategoryDataset;


public class AdminDashboard extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(AdminDashboard.class.getName());
    private javax.swing.JButton selectedButton = null;
    
    private int sessionUserId;
    private String sessionFirstname;
    private String sessionLastname;
    private String firstname;
    private String lastname;
    private int id;
    
    public AdminDashboard(String firstname, String lastname, int id) {
        initComponents();
        setTitle("NCST Hotel Management System (Admin)");
        this.sessionFirstname = firstname;
        this.sessionLastname = lastname;
        this.sessionUserId = id;
        txt_sessionFirstname.setText(firstname + " " + lastname);
        loadUserInformation();
        loadUserTable();
        initUserTable();
        initRoomTable();
        loadRoomTable();
        loadWeeklyRevenueChart();
        loadRevenueByCategoryChart();
        loadRoomCounts();
        loadReservationTable();
        
        psProfile.setBorder(new RoundedBorder(20));
        psProfile.setBackground(Color.WHITE);
        psPass.setBorder(new RoundedBorder(20));
        psPass.setBackground(Color.WHITE);
        rmB1.setBorder(new RoundedBorder(20));
        rmB2.setBorder(new RoundedBorder(20));
        rmB3.setBorder(new RoundedBorder(20));
        rmB8.setBorder(new RoundedBorder(20));
        rmB9.setBorder(new RoundedBorder(20));
        rmB10.setBorder(new RoundedBorder(20));
        rmB11.setBorder(new RoundedBorder(20));
        rmB12.setBorder(new RoundedBorder(20));
        rmB13.setBorder(new RoundedBorder(20));
        rmB14.setBorder(new RoundedBorder(20));
        jpCpass.setBorder(new RoundedBorder(20));
        jpNpass.setBorder(new RoundedBorder(20));
        jpNCpass.setBorder(new RoundedBorder(20));
        tfName.setBorder(new RoundedBorder(20));
        tfLastname.setBorder(new RoundedBorder(20));
        txtGmail.setBorder(new RoundedBorder(20));
        tfUname.setBorder(new RoundedBorder(20));
        tfRole.setBorder(new RoundedBorder(20));
        roomSearch.setBorder(new RoundedBorder(20));
        userSearch.setBorder(new RoundedBorder(20));
        setupSidebarButton(btnDashboard);
        setupSidebarButton(btnStaff);
        setupSidebarButton(btnRooms);
        setupSidebarButton(btnReports);
        setupSidebarButton(btnLogout);
        setupSidebarButton(btnSettings);
        UIStyles.applyRoundedButton(btnAddStaff);
        UIStyles.applyRoundedButton(btnAddRoom);
        UIStyles.applyRoundedButton(btnSaveProfile);
        UIStyles.applyRoundedButton(btnUpass);
        setLabelImage(logo, "/img/leche.png");
        setLabelImage(rmBox1, "/img/standardbed.png");
        setLabelImage(rmBox2, "/img/deluxebed.png");
        setLabelImage(rmBox3, "/img/suitebed.png");
        setLabelImage(rmBox4, "/img/group.png");
        setLabelImage(rmBox5, "/img/approve.png");
        setLabelImage(rmBox6, "/img/information.png");
        setLabelImage(stUser, "/img/stUser.png");
        setLabelImage(dbed, "/img/bed.png");
        setLabelImage(dAvail, "/img/check-mark.png");
        setLabelImage(dNoAvail, "/img/wrong.png");
        setLabelImage(dRev, "/img/money.png");
        
        setIconImage();
        AutoIconResizer.setIcon(btnAddStaff, "/img/plus.png");
        AutoIconResizer.setIcon(btnAddRoom, "/img/plus.png");
        AutoIconResizer.setIcon(btnDashboard, "/img/dashboard2.png");
        AutoIconResizer.setIcon(btnStaff, "/img/group2.png");
        AutoIconResizer.setIcon(btnRooms, "/img/bed2.png");
        AutoIconResizer.setIcon(btnReports, "/img/growth2.png");
        AutoIconResizer.setIcon(btnSettings, "/img/setting.png");
        AutoIconResizer.setIcon(btnLogout, "/img/logout.png");
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
    private void loadRevenueByCategoryChart() {

        ReservationController rc = new ReservationController();
        DefaultCategoryDataset dataset = rc.getRevenueByCategoryDataset();

        JFreeChart chart = ChartFactory.createBarChart(
                "Revenue by Room Category",  
                "Room Category",             
                "Revenue (₱)",              
                dataset
        );

        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setOutlineVisible(false);
        plot.setRangeGridlinePaint(new Color(220, 220, 220)); 

        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setBarPainter(new StandardBarPainter());  
        renderer.setShadowVisible(false);                  
        renderer.setMaximumBarWidth(0.15);                
        renderer.setItemMargin(0.1);                      

        renderer.setSeriesPaint(0, new GradientPaint(0f, 0f, new Color(52, 152, 219),
                                                     0f, 0f, new Color(46, 204, 113)));


        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
        domainAxis.setTickLabelFont(new Font("Segoe UI", Font.BOLD, 12));

        ValueAxis rangeAxis = plot.getRangeAxis();
        rangeAxis.setTickLabelFont(new Font("Segoe UI", Font.PLAIN, 12));

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(450, 322));
        chartPanel.setOpaque(false);
        
        panelRevenueCategory.setBorder(new Utils.RoundedBorder(25));
        panelRevenueCategory.removeAll();
        panelRevenueCategory.setLayout(new BorderLayout());
        panelRevenueCategory.add(chartPanel, BorderLayout.CENTER);
        panelRevenueCategory.revalidate();
        panelRevenueCategory.repaint();
    }
    
    public void loadRoomCounts(){
        ReservationController rsc = new ReservationController();
        RoomsController rc = new RoomsController();
        UserController usercon = new UserController();
        int[] counts = usercon.getUserCounts();

        stotal.setText(String.valueOf(counts[0]));
        sActive.setText(String.valueOf(counts[1]));
        sInactive.setText(String.valueOf(counts[2]));
        
        int availableRooms = rc.countRoomsByStatus("Available");
        int occupiedRooms = rc.countRoomsByStatus("Occupied");
        daAvail.setText(String.valueOf(availableRooms));
        daOcc.setText(String.valueOf(occupiedRooms));
        int total = rc.getTotalRooms();
        dTotal.setText(String.valueOf(total));
        double occupancyRate = rsc.getOccupancyRate();
        rOc.setText(String.format("%.2f%%", occupancyRate));
        double revenue = rsc.getTotalRevenue();
        rRev.setText("₱" + String.valueOf(revenue));
        daRev.setText("₱" + String.valueOf(revenue));
        int totalBookings = rsc.getTotalBookings();
        rBook.setText(String.valueOf(totalBookings));
        int standard = rc.countRoomsByCategory("Standard");
        int deluxe = rc.countRoomsByCategory("Deluxe");
        int suite = rc.countRoomsByCategory("Suite");
        
        lblStandardRooms.setText(String.valueOf(standard));
        lblDeluxeRooms.setText(String.valueOf(deluxe));
        lblSuiteRooms.setText(String.valueOf(suite));
        
    }
    private void loadUserInformation(){

        UserController usercon = new UserController();
        Models.Users user = usercon.getUserById(sessionUserId);

        if(user != null){
            tfName.setText(user.getFirstname());
            tfLastname.setText(user.getLastname());
            txtGmail.setText(user.getGmail());
            tfUname.setText(user.getUsername());
            tfRole.setText(user.getRoles());
        }
    }
    public void loadReservationTable(){
        DefaultTableModel model = (DefaultTableModel) table_reserv.getModel();
        model.setRowCount(0);
        ReservationController revcon = new ReservationController();
        RoomsController roomcon = new RoomsController();

        List<Reservations> reservation = revcon.showTableRoom();
        
        for(Reservations rev : reservation){
            Rooms room = roomcon.getRoomById(rev.getRoomId());

            String roomNumber = "";
            if(room != null){
                roomNumber = room.getRoomNumber();
            }

            model.addRow(new Object[]{
                rev.getRevId(),
                rev.getFirstname() + " " + rev.getLastname(),
                roomNumber,          
                rev.getCheckin(),
                rev.getCheckout(),
                rev.getPaymentAmount(),
                rev.getStatus()
            });
        }
        
        TableUtils4.styleTable(table_reserv);
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
    }
    
    public static void setLabelImage(JLabel label, String path) {

        ImageIcon icon = new ImageIcon(UIStyles.class.getResource(path));

        Image img = icon.getImage().getScaledInstance(
                label.getWidth(),
                label.getHeight(),
                Image.SCALE_SMOOTH
        );

        label.setIcon(new ImageIcon(img));
        label.setHorizontalAlignment(JLabel.CENTER);
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
    
    public void loadUserTable(String search) {
        DefaultTableModel model = (DefaultTableModel) table_user.getModel();
        model.setRowCount(0); 

        UserController usercon = new UserController();
        List<Users> users = usercon.showUser();
        String keyword = search.toLowerCase().trim();
        for (Users user : users) {
            if (!search.isEmpty() &&
                !user.getFirstname().toLowerCase().contains(keyword) &&
                !user.getLastname().toLowerCase().contains(keyword) &&
                !user.getUsername().toLowerCase().contains(keyword) &&
                !user.getGmail().toLowerCase().contains(keyword) &&
                !user.getRoles().toLowerCase().contains(keyword)) {
                continue;
            }
            
            model.addRow(new Object[]{
                user.getId(),
                user.getFirstname(),
                user.getLastname(),
                user.getGmail(),
                user.getRoles(),
                user.getUsername(),
                user.getStatus() == null ? "Active" : user.getStatus(), // default Active
                "Edit"
            });
        }
        loadRoomCounts();
    }
    public void loadUserTable() {
       loadUserTable("");
    }
    private void initUserTable() {
        TableUtils2.styleTable(table_user);
        
        JScrollPane scroll = (JScrollPane) table_user.getParent().getParent();
        scroll.getVerticalScrollBar().setUI(new Utils.ModernScrollBar());
        scroll.getVerticalScrollBar().setPreferredSize(new Dimension(8, 0));
        scroll.setBorder(null);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        table_user.getColumnModel().getColumn(0).setMinWidth(0);
        table_user.getColumnModel().getColumn(0).setMaxWidth(0);
        table_user.getColumnModel().getColumn(0).setWidth(0);

        table_user.getColumn("Status")
                .setCellRenderer(new TableUtils2.StatusRenderer());

        table_user.getColumn("Action")
                .setCellRenderer(new TableUtils2.ActionRenderer());

        table_user.getColumn("Action")
                .setCellEditor(new TableUtils2.ActionEditor(table_user, row -> {

            int userId = Integer.parseInt(table_user.getValueAt(row, 0).toString());

            UserController usercon = new UserController();
            Users user = usercon.getUserById(userId);

            if(user != null){
                editUserFrame ef = new editUserFrame(user, userId, () -> loadUserTable());
                ef.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "User not found!");
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
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        Sidebar = new javax.swing.JPanel();
        btnDashboard = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btnRooms = new javax.swing.JButton();
        btnSettings = new javax.swing.JButton();
        btnStaff = new javax.swing.JButton();
        logo = new javax.swing.JLabel();
        txt_sessionFirstname = new javax.swing.JLabel();
        btnLogout = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel9 = new javax.swing.JLabel();
        btnReports = new javax.swing.JButton();
        mainPanel = new javax.swing.JPanel();
        dashboard = new javax.swing.JPanel();
        jLabel35 = new javax.swing.JLabel();
        jLabel62 = new javax.swing.JLabel();
        rmB11 = new javax.swing.JPanel();
        jLabel33 = new javax.swing.JLabel();
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
        jScrollPane3 = new javax.swing.JScrollPane();
        table_reserv = new javax.swing.JTable();
        staff = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        btnAddStaff = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        table_user = new javax.swing.JTable();
        rmB4 = new javax.swing.JPanel();
        rmBox4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        stotal = new javax.swing.JLabel();
        rmB5 = new javax.swing.JPanel();
        rmBox5 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        sActive = new javax.swing.JLabel();
        rmB6 = new javax.swing.JPanel();
        rmBox6 = new javax.swing.JLabel();
        sInactive = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        userSearch = new javax.swing.JTextField();
        rooms = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        btnAddRoom = new javax.swing.JButton();
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
        reports = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        rmB8 = new javax.swing.JPanel();
        jLabel31 = new javax.swing.JLabel();
        rRev = new javax.swing.JLabel();
        rmB9 = new javax.swing.JPanel();
        jLabel61 = new javax.swing.JLabel();
        rOc = new javax.swing.JLabel();
        rmB10 = new javax.swing.JPanel();
        rBook = new javax.swing.JLabel();
        Bank = new javax.swing.JLabel();
        panelChart = new javax.swing.JPanel();
        panelRevenueCategory = new javax.swing.JPanel();
        settings = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
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
        txtGmail = new javax.swing.JTextField();
        jLabel40 = new javax.swing.JLabel();
        psPass = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jpCpass = new javax.swing.JPasswordField();
        jLabel29 = new javax.swing.JLabel();
        jpNpass = new javax.swing.JPasswordField();
        jpNCpass = new javax.swing.JPasswordField();
        jLabel30 = new javax.swing.JLabel();
        btnUpass = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(250, 250, 250));

        Sidebar.setBackground(new java.awt.Color(0, 34, 76));
        Sidebar.setForeground(new java.awt.Color(255, 255, 255));

        btnDashboard.setBackground(new java.awt.Color(245, 192, 34));
        btnDashboard.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        btnDashboard.setForeground(new java.awt.Color(255, 255, 255));
        btnDashboard.setText("Dashboard");
        btnDashboard.setBorder(null);
        btnDashboard.setBorderPainted(false);
        btnDashboard.setContentAreaFilled(false);
        btnDashboard.setIconTextGap(5);
        btnDashboard.addActionListener(this::btnDashboardActionPerformed);

        jLabel3.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(251, 189, 35));
        jLabel3.setText("NCST Hotel");

        jLabel4.setFont(new java.awt.Font("Arial", 2, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(209, 213, 219));
        jLabel4.setText("Management System");

        btnRooms.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        btnRooms.setForeground(new java.awt.Color(255, 255, 255));
        btnRooms.setText("Rooms");
        btnRooms.setBorder(null);
        btnRooms.setBorderPainted(false);
        btnRooms.setContentAreaFilled(false);
        btnRooms.addActionListener(this::btnRoomsActionPerformed);

        btnSettings.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        btnSettings.setForeground(new java.awt.Color(255, 255, 255));
        btnSettings.setText("Settings");
        btnSettings.setBorder(null);
        btnSettings.setBorderPainted(false);
        btnSettings.setContentAreaFilled(false);
        btnSettings.setFocusPainted(false);
        btnSettings.addActionListener(this::btnSettingsActionPerformed);

        btnStaff.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        btnStaff.setForeground(new java.awt.Color(255, 255, 255));
        btnStaff.setText("Staff");
        btnStaff.setBorder(null);
        btnStaff.setBorderPainted(false);
        btnStaff.setContentAreaFilled(false);
        btnStaff.setFocusPainted(false);
        btnStaff.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnStaff.addActionListener(this::btnStaffActionPerformed);

        txt_sessionFirstname.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        txt_sessionFirstname.setForeground(new java.awt.Color(255, 255, 255));

        btnLogout.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        btnLogout.setForeground(new java.awt.Color(255, 255, 255));
        btnLogout.setText("Sign Out");
        btnLogout.setBorder(null);
        btnLogout.setBorderPainted(false);
        btnLogout.setContentAreaFilled(false);
        btnLogout.setFocusPainted(false);
        btnLogout.addActionListener(this::btnLogoutActionPerformed);

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(185, 195, 203));
        jLabel9.setText("admin");

        btnReports.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        btnReports.setForeground(new java.awt.Color(255, 255, 255));
        btnReports.setText("Reports");
        btnReports.setBorder(null);
        btnReports.setBorderPainted(false);
        btnReports.setContentAreaFilled(false);
        btnReports.setFocusPainted(false);
        btnReports.addActionListener(this::btnReportsActionPerformed);

        javax.swing.GroupLayout SidebarLayout = new javax.swing.GroupLayout(Sidebar);
        Sidebar.setLayout(SidebarLayout);
        SidebarLayout.setHorizontalGroup(
            SidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jSeparator2)
            .addGroup(SidebarLayout.createSequentialGroup()
                .addGroup(SidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(SidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(SidebarLayout.createSequentialGroup()
                            .addGap(8, 8, 8)
                            .addComponent(logo, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(SidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(SidebarLayout.createSequentialGroup()
                            .addGap(16, 16, 16)
                            .addComponent(btnDashboard, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(SidebarLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(SidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnStaff, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(SidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(btnLogout, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnSettings, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
                                .addComponent(btnRooms, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnReports, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE))
                            .addComponent(txt_sessionFirstname, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        SidebarLayout.setVerticalGroup(
            SidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SidebarLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(SidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(SidebarLayout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addGap(8, 8, 8))
                    .addComponent(logo, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(btnDashboard, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnStaff, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnRooms, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(btnReports, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSettings, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_sessionFirstname, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39))
        );

        mainPanel.setLayout(new java.awt.CardLayout());

        dashboard.setBackground(new java.awt.Color(255, 255, 255));
        dashboard.setPreferredSize(new java.awt.Dimension(1440, 700));

        jLabel35.setBackground(new java.awt.Color(16, 53, 113));
        jLabel35.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(16, 53, 113));
        jLabel35.setText("Admin Dashboard");

        jLabel62.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel62.setForeground(new java.awt.Color(118, 139, 148));
        jLabel62.setText("Monitor your hotel operations and manage your business efficiently");

        rmB11.setBackground(new java.awt.Color(255, 255, 255));
        rmB11.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, java.awt.Color.lightGray));
        rmB11.setOpaque(false);

        jLabel33.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(118, 139, 148));
        jLabel33.setText("Total Rooms");

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
                        .addComponent(jLabel33)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                        .addComponent(jLabel33))
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(daAvail, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel54)
                .addGap(15, 15, 15))
            .addGroup(rmB12Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(dAvail, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
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
                .addContainerGap(32, Short.MAX_VALUE)
                .addGroup(rmB14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rmB14Layout.createSequentialGroup()
                        .addComponent(daRev)
                        .addGap(30, 30, 30))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rmB14Layout.createSequentialGroup()
                        .addComponent(jLabel57)
                        .addGap(18, 18, 18)))
                .addComponent(dRev, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
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

        table_reserv.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Reservation ID", "Full Name", "Room", "Check-in", "Check-out", "Amount", "Status"
            }
        ));
        jScrollPane3.setViewportView(table_reserv);

        javax.swing.GroupLayout dashboardLayout = new javax.swing.GroupLayout(dashboard);
        dashboard.setLayout(dashboardLayout);
        dashboardLayout.setHorizontalGroup(
            dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dashboardLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel35)
                    .addComponent(jLabel62)
                    .addGroup(dashboardLayout.createSequentialGroup()
                        .addComponent(rmB11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(rmB12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(rmB13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(rmB14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 1004, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(17, Short.MAX_VALUE))
        );
        dashboardLayout.setVerticalGroup(
            dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dashboardLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel35)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel62)
                .addGap(18, 18, 18)
                .addGroup(dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(rmB14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(rmB13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(rmB11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rmB12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 462, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        mainPanel.add(dashboard, "dashboard");

        staff.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setBackground(new java.awt.Color(16, 53, 113));
        jLabel2.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(16, 53, 113));
        jLabel2.setText("Staff Management");

        jLabel1.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(118, 139, 148));
        jLabel1.setText("Manage staff accounts and assign roles");

        btnAddStaff.setBackground(new java.awt.Color(245, 192, 34));
        btnAddStaff.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        btnAddStaff.setText("Add Staff");
        btnAddStaff.addActionListener(this::btnAddStaffActionPerformed);

        table_user.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "id", "First Name", "Last Name", "Gmail", "Role", "Username", "Status", "Action"
            }
        ));
        jScrollPane2.setViewportView(table_user);

        rmB4.setBackground(new java.awt.Color(255, 255, 255));
        rmB4.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, java.awt.Color.lightGray));
        rmB4.setOpaque(false);

        jLabel7.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(118, 139, 148));
        jLabel7.setText("Total Staff");

        stotal.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        stotal.setText("45");

        javax.swing.GroupLayout rmB4Layout = new javax.swing.GroupLayout(rmB4);
        rmB4.setLayout(rmB4Layout);
        rmB4Layout.setHorizontalGroup(
            rmB4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rmB4Layout.createSequentialGroup()
                .addContainerGap(64, Short.MAX_VALUE)
                .addGroup(rmB4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(stotal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50)
                .addComponent(rmBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
        );
        rmB4Layout.setVerticalGroup(
            rmB4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rmB4Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(rmB4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rmB4Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(stotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7)
                        .addGap(14, 14, 14))
                    .addGroup(rmB4Layout.createSequentialGroup()
                        .addComponent(rmBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        rmB5.setBackground(new java.awt.Color(255, 255, 255));
        rmB5.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, java.awt.Color.lightGray));
        rmB5.setOpaque(false);

        jLabel36.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(118, 139, 148));
        jLabel36.setText("Active Staff");

        sActive.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        sActive.setText("45");

        javax.swing.GroupLayout rmB5Layout = new javax.swing.GroupLayout(rmB5);
        rmB5.setLayout(rmB5Layout);
        rmB5Layout.setHorizontalGroup(
            rmB5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rmB5Layout.createSequentialGroup()
                .addContainerGap(58, Short.MAX_VALUE)
                .addGroup(rmB5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sActive, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel36, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(66, 66, 66)
                .addComponent(rmBox5, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );
        rmB5Layout.setVerticalGroup(
            rmB5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rmB5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(rmB5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rmB5Layout.createSequentialGroup()
                        .addComponent(rmBox5, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(23, 23, 23))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rmB5Layout.createSequentialGroup()
                        .addComponent(sActive, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel36)
                        .addGap(15, 15, 15))))
        );

        rmB6.setBackground(new java.awt.Color(255, 255, 255));
        rmB6.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, java.awt.Color.lightGray));
        rmB6.setOpaque(false);

        sInactive.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        sInactive.setText("45");

        jLabel39.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(118, 139, 148));
        jLabel39.setText("Inactive Staff");

        javax.swing.GroupLayout rmB6Layout = new javax.swing.GroupLayout(rmB6);
        rmB6.setLayout(rmB6Layout);
        rmB6Layout.setHorizontalGroup(
            rmB6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rmB6Layout.createSequentialGroup()
                .addContainerGap(33, Short.MAX_VALUE)
                .addGroup(rmB6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rmB6Layout.createSequentialGroup()
                        .addComponent(jLabel39)
                        .addGap(43, 43, 43))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rmB6Layout.createSequentialGroup()
                        .addComponent(sInactive, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(52, 52, 52)))
                .addComponent(rmBox6, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        rmB6Layout.setVerticalGroup(
            rmB6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rmB6Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(rmB6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(rmB6Layout.createSequentialGroup()
                        .addComponent(sInactive, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel39))
                    .addComponent(rmBox6, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        userSearch.addActionListener(this::userSearchActionPerformed);
        userSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                userSearchKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout staffLayout = new javax.swing.GroupLayout(staff);
        staff.setLayout(staffLayout);
        staffLayout.setHorizontalGroup(
            staffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(staffLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(staffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, staffLayout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(staffLayout.createSequentialGroup()
                        .addGroup(staffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(userSearch, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(staffLayout.createSequentialGroup()
                                .addComponent(rmB4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 78, Short.MAX_VALUE)
                                .addComponent(rmB5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(71, 71, 71)
                                .addComponent(rmB6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(staffLayout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnAddStaff, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(52, 52, 52))))
        );
        staffLayout.setVerticalGroup(
            staffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(staffLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(staffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(btnAddStaff))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(staffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(rmB4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(rmB5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(rmB6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(userSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(36, Short.MAX_VALUE))
        );

        mainPanel.add(staff, "staff");

        rooms.setBackground(new java.awt.Color(250, 250, 250));

        jLabel10.setBackground(new java.awt.Color(16, 53, 113));
        jLabel10.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(16, 53, 113));
        jLabel10.setText("Room Management");

        jLabel11.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(118, 139, 148));
        jLabel11.setText("Manage hotel rooms, categories, and availability");

        btnAddRoom.setBackground(new java.awt.Color(245, 192, 34));
        btnAddRoom.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        btnAddRoom.setText("Add Room");
        btnAddRoom.addActionListener(this::btnAddRoomActionPerformed);

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
                .addContainerGap(62, Short.MAX_VALUE)
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
                        .addComponent(lblStandardRooms, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
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
                .addContainerGap(49, Short.MAX_VALUE)
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
                .addContainerGap(31, Short.MAX_VALUE)
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addGroup(roomsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, roomsLayout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(roomSearch, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roomsLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(rmB1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(54, 54, 54)
                        .addComponent(rmB2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(54, 54, 54)
                        .addComponent(rmB3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(roomsLayout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAddRoom, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING))
                .addGap(53, 53, 53))
        );
        roomsLayout.setVerticalGroup(
            roomsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roomsLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(roomsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(btnAddRoom))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11)
                .addGap(18, 18, 18)
                .addGroup(roomsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(rmB1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(rmB2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(rmB3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(roomSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 417, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
        );

        mainPanel.add(rooms, "rooms");

        reports.setBackground(new java.awt.Color(250, 250, 250));

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

        jLabel31.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(118, 139, 148));
        jLabel31.setText("Total Revenue");

        rRev.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        rRev.setText("45");

        javax.swing.GroupLayout rmB8Layout = new javax.swing.GroupLayout(rmB8);
        rmB8.setLayout(rmB8Layout);
        rmB8Layout.setHorizontalGroup(
            rmB8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rmB8Layout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addGroup(rmB8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rRev, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel31))
                .addContainerGap(56, Short.MAX_VALUE))
        );
        rmB8Layout.setVerticalGroup(
            rmB8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rmB8Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(rRev, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel31)
                .addGap(14, 14, 14))
        );

        rmB9.setBackground(new java.awt.Color(255, 255, 255));
        rmB9.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, java.awt.Color.lightGray));
        rmB9.setOpaque(false);

        jLabel61.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        jLabel61.setForeground(new java.awt.Color(118, 139, 148));
        jLabel61.setText("Occupancy Rate");

        rOc.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        rOc.setText("45");

        javax.swing.GroupLayout rmB9Layout = new javax.swing.GroupLayout(rmB9);
        rmB9.setLayout(rmB9Layout);
        rmB9Layout.setHorizontalGroup(
            rmB9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rmB9Layout.createSequentialGroup()
                .addContainerGap(82, Short.MAX_VALUE)
                .addGroup(rmB9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rmB9Layout.createSequentialGroup()
                        .addComponent(jLabel61)
                        .addGap(71, 71, 71))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rmB9Layout.createSequentialGroup()
                        .addComponent(rOc, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(56, 56, 56))))
        );
        rmB9Layout.setVerticalGroup(
            rmB9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rmB9Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(rOc, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel61)
                .addGap(15, 15, 15))
        );

        rmB10.setBackground(new java.awt.Color(255, 255, 255));
        rmB10.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, java.awt.Color.lightGray));
        rmB10.setOpaque(false);

        rBook.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        rBook.setText("45");

        Bank.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        Bank.setForeground(new java.awt.Color(118, 139, 148));
        Bank.setText("Total Bookings");

        javax.swing.GroupLayout rmB10Layout = new javax.swing.GroupLayout(rmB10);
        rmB10.setLayout(rmB10Layout);
        rmB10Layout.setHorizontalGroup(
            rmB10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rmB10Layout.createSequentialGroup()
                .addContainerGap(77, Short.MAX_VALUE)
                .addGroup(rmB10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rmB10Layout.createSequentialGroup()
                        .addComponent(Bank)
                        .addGap(91, 91, 91))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rmB10Layout.createSequentialGroup()
                        .addComponent(rBook, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(100, 100, 100))))
        );
        rmB10Layout.setVerticalGroup(
            rmB10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rmB10Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(rBook, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Bank)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        panelChart.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout panelChartLayout = new javax.swing.GroupLayout(panelChart);
        panelChart.setLayout(panelChartLayout);
        panelChartLayout.setHorizontalGroup(
            panelChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 435, Short.MAX_VALUE)
        );
        panelChartLayout.setVerticalGroup(
            panelChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 425, Short.MAX_VALUE)
        );

        panelRevenueCategory.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout panelRevenueCategoryLayout = new javax.swing.GroupLayout(panelRevenueCategory);
        panelRevenueCategory.setLayout(panelRevenueCategoryLayout);
        panelRevenueCategoryLayout.setHorizontalGroup(
            panelRevenueCategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelRevenueCategoryLayout.setVerticalGroup(
            panelRevenueCategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 393, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout reportsLayout = new javax.swing.GroupLayout(reports);
        reports.setLayout(reportsLayout);
        reportsLayout.setHorizontalGroup(
            reportsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(reportsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(reportsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(reportsLayout.createSequentialGroup()
                        .addComponent(panelChart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(panelRevenueCategory, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel32)
                    .addComponent(jLabel59)
                    .addGroup(reportsLayout.createSequentialGroup()
                        .addComponent(rmB8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(44, 44, 44)
                        .addComponent(rmB9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(47, 47, 47)
                        .addComponent(rmB10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(76, Short.MAX_VALUE))
        );
        reportsLayout.setVerticalGroup(
            reportsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(reportsLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel32)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel59)
                .addGap(18, 18, 18)
                .addGroup(reportsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(rmB8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(rmB9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(rmB10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(reportsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(reportsLayout.createSequentialGroup()
                        .addComponent(panelChart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(73, 73, 73))
                    .addGroup(reportsLayout.createSequentialGroup()
                        .addComponent(panelRevenueCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        mainPanel.add(reports, "reports");

        settings.setBackground(new java.awt.Color(250, 250, 250));

        jLabel17.setBackground(new java.awt.Color(16, 53, 113));
        jLabel17.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(16, 53, 113));
        jLabel17.setText("Settings");

        jLabel18.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(118, 139, 148));
        jLabel18.setText("Manage your profile and system settings");

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

        jLabel40.setFont(new java.awt.Font("Poppins SemiBold", 0, 15)); // NOI18N
        jLabel40.setText("Gmail");

        javax.swing.GroupLayout psProfileLayout = new javax.swing.GroupLayout(psProfile);
        psProfile.setLayout(psProfileLayout);
        psProfileLayout.setHorizontalGroup(
            psProfileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(psProfileLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(psProfileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, psProfileLayout.createSequentialGroup()
                        .addComponent(jLabel40)
                        .addGap(420, 420, 420))
                    .addComponent(tfRole)
                    .addComponent(jLabel25)
                    .addComponent(tfUname)
                    .addComponent(jLabel24)
                    .addComponent(jLabel21)
                    .addGroup(psProfileLayout.createSequentialGroup()
                        .addComponent(stUser, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel20))
                    .addComponent(jLabel23)
                    .addComponent(jLabel22)
                    .addComponent(btnSaveProfile, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtGmail, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(tfName)
                    .addComponent(tfLastname))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        psProfileLayout.setVerticalGroup(
            psProfileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(psProfileLayout.createSequentialGroup()
                .addGap(16, 16, 16)
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
                .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtGmail, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tfUname, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tfRole, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSaveProfile, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        psPass.setBackground(new java.awt.Color(255, 255, 255));
        psPass.setPreferredSize(new java.awt.Dimension(489, 445));

        jLabel26.setFont(new java.awt.Font("Poppins", 0, 13)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(118, 139, 148));
        jLabel26.setText("Update your account password");

        jLabel27.setFont(new java.awt.Font("Rockwell", 1, 16)); // NOI18N
        jLabel27.setText("Change Password");

        jLabel28.setFont(new java.awt.Font("Poppins SemiBold", 0, 15)); // NOI18N
        jLabel28.setText("Current Password");

        jLabel29.setFont(new java.awt.Font("Poppins SemiBold", 0, 15)); // NOI18N
        jLabel29.setText("New Password");

        jLabel30.setFont(new java.awt.Font("Poppins SemiBold", 0, 15)); // NOI18N
        jLabel30.setText("Confirm Password");

        btnUpass.setText("Update Password");
        btnUpass.addActionListener(this::btnUpassActionPerformed);

        javax.swing.GroupLayout psPassLayout = new javax.swing.GroupLayout(psPass);
        psPass.setLayout(psPassLayout);
        psPassLayout.setHorizontalGroup(
            psPassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(psPassLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(psPassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel30)
                    .addComponent(jLabel29)
                    .addComponent(jLabel28)
                    .addComponent(jLabel27)
                    .addComponent(jLabel26)
                    .addComponent(jpCpass)
                    .addComponent(jpNpass)
                    .addComponent(jpNCpass)
                    .addComponent(btnUpass, javax.swing.GroupLayout.DEFAULT_SIZE, 418, Short.MAX_VALUE))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        psPassLayout.setVerticalGroup(
            psPassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(psPassLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel26)
                .addGap(26, 26, 26)
                .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpCpass, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpNpass, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpNCpass, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(btnUpass, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );

        jLabel19.setFont(new java.awt.Font("Californian FB", 1, 24)); // NOI18N
        jLabel19.setText("My Profile ");

        javax.swing.GroupLayout settingsLayout = new javax.swing.GroupLayout(settings);
        settings.setLayout(settingsLayout);
        settingsLayout.setHorizontalGroup(
            settingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(settingsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(settingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17)
                    .addComponent(jLabel19)
                    .addGroup(settingsLayout.createSequentialGroup()
                        .addComponent(psProfile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(psPass, javax.swing.GroupLayout.PREFERRED_SIZE, 453, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(49, Short.MAX_VALUE))
        );
        settingsLayout.setVerticalGroup(
            settingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, settingsLayout.createSequentialGroup()
                .addContainerGap(32, Short.MAX_VALUE)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel18)
                .addGap(26, 26, 26)
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(settingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(psPass, javax.swing.GroupLayout.PREFERRED_SIZE, 408, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(psProfile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40))
        );

        mainPanel.add(settings, "settings");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(Sidebar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 1027, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Sidebar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
 
    
    
    private void btnDashboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDashboardActionPerformed
        CardLayout cl = (CardLayout)(mainPanel.getLayout());
        cl.show(mainPanel, "dashboard");
    }//GEN-LAST:event_btnDashboardActionPerformed

    private void btnStaffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStaffActionPerformed
        CardLayout cl = (CardLayout)(mainPanel.getLayout());
        cl.show(mainPanel, "staff");
    }//GEN-LAST:event_btnStaffActionPerformed

    private void btnRoomsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRoomsActionPerformed
        CardLayout cl = (CardLayout)(mainPanel.getLayout());
        cl.show(mainPanel, "rooms");
    }//GEN-LAST:event_btnRoomsActionPerformed

    private void btnSettingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSettingsActionPerformed
        CardLayout cl = (CardLayout)(mainPanel.getLayout());
        cl.show(mainPanel, "settings");
    }//GEN-LAST:event_btnSettingsActionPerformed

    private void btnAddStaffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddStaffActionPerformed
        addStaffFrame as = new addStaffFrame();
        as.setOnUpdate(() -> loadUserTable());
        as.setVisible(true);
        
        Users user = new Users();
        UserController usercon = new UserController();
        loadRoomCounts();
    }//GEN-LAST:event_btnAddStaffActionPerformed

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        new loginFrame().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnLogoutActionPerformed

    private void btnReportsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReportsActionPerformed
        CardLayout cl = (CardLayout)(mainPanel.getLayout());
        cl.show(mainPanel, "reports");
    }//GEN-LAST:event_btnReportsActionPerformed

    private void btnAddRoomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddRoomActionPerformed
        addRoomFrame ar = new addRoomFrame(this);
        ar.setVisible(true);
        
        Rooms room = new Rooms();
        RoomsController roomcon = new RoomsController();
        loadRoomCounts();
    }//GEN-LAST:event_btnAddRoomActionPerformed

    private void btnSaveProfileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveProfileActionPerformed
        String firstname = tfName.getText();
        String lastname = tfLastname.getText();
        String gmail = txtGmail.getText();
        String username = tfUname.getText();

        Users user = new Users();
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setGmail(gmail);
        user.setUsername(username);

        UserController usercon = new UserController();
        usercon.updateUserInformation(user, sessionUserId);

        JOptionPane.showMessageDialog(this, "Profile Updated Successfully!");
        loadRoomCounts();
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

    private void userSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_userSearchActionPerformed
        loadUserTable(userSearch.getText().trim());
    }//GEN-LAST:event_userSearchActionPerformed

    private void userSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_userSearchKeyReleased
        loadUserTable(userSearch.getText().trim());
    }//GEN-LAST:event_userSearchKeyReleased

    private void roomSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_roomSearchActionPerformed
        loadRoomTable(roomSearch.getText().trim());
    }//GEN-LAST:event_roomSearchActionPerformed

    private void roomSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_roomSearchKeyReleased
        loadRoomTable(roomSearch.getText().trim());
    }//GEN-LAST:event_roomSearchKeyReleased

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
    private javax.swing.JPanel Sidebar;
    private javax.swing.JButton btnAddRoom;
    private javax.swing.JButton btnAddStaff;
    private javax.swing.JButton btnDashboard;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnReports;
    private javax.swing.JButton btnRooms;
    private javax.swing.JButton btnSaveProfile;
    private javax.swing.JButton btnSettings;
    private javax.swing.JButton btnStaff;
    private javax.swing.JButton btnUpass;
    private javax.swing.JLabel dAvail;
    private javax.swing.JLabel dNoAvail;
    private javax.swing.JLabel dRev;
    private javax.swing.JLabel dTotal;
    private javax.swing.JLabel daAvail;
    private javax.swing.JLabel daOcc;
    private javax.swing.JLabel daRev;
    private javax.swing.JPanel dashboard;
    private javax.swing.JLabel dbed;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
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
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JPasswordField jpCpass;
    private javax.swing.JPasswordField jpNCpass;
    private javax.swing.JPasswordField jpNpass;
    private javax.swing.JLabel lblDeluxeRooms;
    private javax.swing.JLabel lblStandardRooms;
    private javax.swing.JLabel lblSuiteRooms;
    private javax.swing.JLabel logo;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPanel panelChart;
    private javax.swing.JPanel panelRevenueCategory;
    private javax.swing.JPanel psPass;
    private javax.swing.JPanel psProfile;
    private javax.swing.JLabel rBook;
    private javax.swing.JLabel rOc;
    private javax.swing.JLabel rRev;
    private javax.swing.JPanel reports;
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
    private javax.swing.JLabel sActive;
    private javax.swing.JLabel sInactive;
    private javax.swing.JPanel settings;
    private javax.swing.JLabel stUser;
    private javax.swing.JPanel staff;
    private javax.swing.JLabel stotal;
    private javax.swing.JTable table_reserv;
    private javax.swing.JTable table_rooms;
    private javax.swing.JTable table_user;
    private javax.swing.JTextField tfLastname;
    private javax.swing.JTextField tfName;
    private javax.swing.JTextField tfRole;
    private javax.swing.JTextField tfUname;
    private javax.swing.JTextField txtGmail;
    private javax.swing.JLabel txt_sessionFirstname;
    private javax.swing.JTextField userSearch;
    // End of variables declaration//GEN-END:variables
}
