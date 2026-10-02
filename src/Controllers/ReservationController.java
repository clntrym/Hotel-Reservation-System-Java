/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import DataBase.DBConnection;
import Models.Reservations;
import Models.Rooms;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;


public class ReservationController {
    
    public void addReservation(Reservations res){

        String query = "INSERT INTO reservation "
                + "(room_id, firstname, lastname, email, phone, type_id, num_id, checkin, checkout, payment_method, payment_amount) "
                + "VALUES (?,?,?,?,?,?,?,?,?,?,?)";

        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)){

            ps.setInt(1, res.getRoomId());
            ps.setString(2, res.getFirstname());
            ps.setString(3, res.getLastname());
            ps.setString(4, res.getEmail());
            ps.setString(5, res.getPhone());
            ps.setString(6, res.getTypeId());
            ps.setString(7, res.getNumId());
            ps.setDate(8, new java.sql.Date(res.getCheckin().getTime()));
            ps.setDate(9, new java.sql.Date(res.getCheckout().getTime()));
            ps.setString(10, res.getPaymentMethod());
            ps.setDouble(11, res.getPaymentAmount());

            ps.executeUpdate();
            
            updateRoomStatus(res.getRoomId(),"Occupied");

            System.out.println("Reservation Added Successfully");

        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    private void updateRoomStatus(int roomId, String status){

        String query = "UPDATE rooms SET status = ? WHERE room_id = ?";

        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)){

            ps.setString(1,status);
            ps.setInt(2,roomId);

            ps.executeUpdate();

        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public List<Reservations> showTableRoom(){
        List <Reservations> reservation = new ArrayList<>();
        String query = "SELECT * FROM reservation";

        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
                
            ResultSet rs = ps.executeQuery()){
            
            while(rs.next()) {
                Reservations reserv = new Reservations();
                reserv.setRevId(rs.getInt("rev_id"));
                reserv.setRoomId(rs.getInt("room_id"));
                reserv.setFirstname(rs.getString("firstname"));
                reserv.setLastname(rs.getString("lastname"));
                reserv.setCheckin(rs.getDate("checkin"));
                reserv.setCheckout(rs.getDate("checkout"));
                reserv.setPaymentAmount(rs.getInt("payment_amount"));
                reserv.setStatus(rs.getString("status"));
                reserv.setCreated_at(rs.getDate("created_at"));
                reserv.setPaymentMethod(rs.getString("payment_method"));
                
                reservation.add(reserv);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return reservation;
    }
    
    public void updateStatus(int revId, String status){

        String query = "UPDATE reservation SET status=? WHERE rev_id=?";

        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)){

            ps.setString(1, status);
            ps.setInt(2, revId);

            ps.executeUpdate();

        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public int countReservationByStatus(String status) {

        int count = 0;

        String query = "SELECT COUNT(*) FROM reservation WHERE status = ?";

        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, status);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                count = rs.getInt(1);
            }

        } catch(Exception e){
            e.printStackTrace();
        }

        return count;
    }
    
    public int countReservationByPayMethod(String payment_method) {

        int count = 0;

        String query = "SELECT COUNT(*) FROM reservation WHERE payment_method = ?";

        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, payment_method);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                count = rs.getInt(1);
            }

        } catch(Exception e){
            e.printStackTrace();
        }

        return count;
    }
    
    public double getOccupancyRate() {
        int totalRooms = 0;
        int occupiedRooms = 0;

        String totalQuery = "SELECT COUNT(*) FROM rooms";
        String occupiedQuery = "SELECT COUNT(*) FROM rooms WHERE status = 'Occupied'";

        try(Connection conn = DBConnection.getConnection();
            PreparedStatement psTotal = conn.prepareStatement(totalQuery);
            PreparedStatement psOccupied = conn.prepareStatement(occupiedQuery)) {

            ResultSet rsTotal = psTotal.executeQuery();
            if(rsTotal.next()) {
                totalRooms = rsTotal.getInt(1);
            }

            ResultSet rsOccupied = psOccupied.executeQuery();
            if(rsOccupied.next()) {
                occupiedRooms = rsOccupied.getInt(1);
            }

        } catch(Exception e){
            e.printStackTrace();
        }

        if(totalRooms == 0) return 0;

        return ((double) occupiedRooms / totalRooms) * 100;
    }
    
    public int getTotalBookings() {
        int total = 0;
        String query = "SELECT COUNT(*) FROM reservation";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                total = rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return total;
    }
    
    public double getTotalRevenue() {

        double total = 0;

        String query = """
            SELECT SUM(
                CASE 
                    WHEN status = 'Checked-out' THEN payment_amount
                    WHEN status = 'Cancelled' THEN payment_amount * 0.30
                    ELSE 0
                END
            ) AS totalRevenue
            FROM reservation
            """;

        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery()) {

            if(rs.next()){
                total = rs.getDouble("totalRevenue");
            }

        } catch(Exception e){
            e.printStackTrace();
        }

        return total;
    }
    
    public DefaultCategoryDataset getWeeklyRevenueDataset(){

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        String query = """
            SELECT DAYNAME(checkout) AS day,
            SUM(payment_amount) AS revenue
            FROM reservation
            WHERE status = 'Checked-out'
            AND checkout >= DATE_SUB(CURDATE(), INTERVAL 7 DAY)
            GROUP BY DAYNAME(checkout)
            """;

        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery()){

            while(rs.next()){

                String day = rs.getString("day");
                double revenue = rs.getDouble("revenue");

                dataset.addValue(revenue, "Revenue", day);
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return dataset;
    }
    
    public DefaultPieDataset getReservationStatusDataset(){

        DefaultPieDataset dataset = new DefaultPieDataset();

        String query = """
            SELECT status, COUNT(*) AS total
            FROM reservation
            GROUP BY status
            """;

        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery()){

            while(rs.next()){

                String status = rs.getString("status");
                int total = rs.getInt("total");

                dataset.setValue(status, total);
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return dataset;
    }
    
    public DefaultCategoryDataset getRevenueByCategoryDataset(){

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        String query = """
            SELECT r.category,
            SUM(
                CASE 
                    WHEN res.status = 'Checked-out' THEN res.payment_amount
                    WHEN res.status = 'Cancelled' THEN res.payment_amount * 0.30
                    ELSE 0
                END
            ) AS revenue
            FROM reservation res
            JOIN rooms r ON res.room_id = r.room_id
            GROUP BY r.category
            """;

        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery()){

            while(rs.next()){

                String category = rs.getString("category");
                double revenue = rs.getDouble("revenue");

                dataset.addValue(revenue, "Revenue", category);
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return dataset;
    }
    
}
