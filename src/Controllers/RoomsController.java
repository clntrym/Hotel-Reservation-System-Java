/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import DataBase.DBConnection;
import Models.Rooms;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class RoomsController {

    public void addRoom(Rooms room){
        String query = "INSERT INTO rooms(room_number, room_floor, category, status, price, capacity) VALUES (?,?,?,?,?,?)";
        
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)){
            
            ps.setString(1, room.getRoomNumber());
            ps.setString(2, room.getRoomFloor());
            ps.setString(3, room.getCategory());
            ps.setString(4, room.getStatus());
            ps.setInt(5, room.getPrice());
            ps.setString(6, room.getCapacity());
            
            ps.executeUpdate();
            System.out.println("Adding Room Success");
            
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    
    public List<Rooms> showTableRoom(){
        List <Rooms> rooms = new ArrayList<>();
        String query = "SELECT * FROM rooms";

        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
                
            ResultSet rs = ps.executeQuery()){
            
            while(rs.next()) {
                Rooms room = new Rooms();
                room.setRoomId(rs.getInt("room_id"));
                room.setRoomNumber(rs.getString("room_number"));
                room.setRoomFloor(rs.getString("room_floor"));
                room.setCategory(rs.getString("category"));
                room.setStatus(rs.getString("status"));
                room.setPrice(rs.getInt("price"));
                room.setCapacity(rs.getString("capacity"));
                
                rooms.add(room);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return rooms;
    }
    
    public void updateRoomStatus(String roomNumber, String status) {
        String query = "UPDATE rooms SET status = ? WHERE room_number = ?";

        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, status);
            ps.setString(2, roomNumber);
            ps.executeUpdate();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public Rooms getRoomByNumber(String roomNumber) {

        Rooms room = null;

        String query = "SELECT * FROM rooms WHERE room_number = ?";

        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, roomNumber);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                room = new Rooms();
                room.setRoomNumber(rs.getString("room_number"));
                room.setRoomFloor(rs.getString("room_floor"));
                room.setCategory(rs.getString("category"));
                room.setCapacity(rs.getString("capacity"));
                room.setPrice(rs.getInt("price"));
                room.setStatus(rs.getString("status"));
            }

        } catch(Exception e){
            e.printStackTrace();
        }

        return room;
    }
    public Rooms getRoomById(int roomId) {
        Rooms room = null;
        String query = "SELECT * FROM rooms WHERE room_id = ?";
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, roomId);
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                room = new Rooms();
                room.setRoomId(rs.getInt("room_id"));
                room.setRoomNumber(rs.getString("room_number"));
                room.setRoomFloor(rs.getString("room_floor"));
                room.setCategory(rs.getString("category"));
                room.setStatus(rs.getString("status"));
                room.setPrice(rs.getInt("price"));
                room.setCapacity(rs.getString("capacity"));
            }

        } catch(Exception e){
            e.printStackTrace();
        }

        return room;
    }
    
    public void updateRoom(Rooms room, int roomId){
        String query = "UPDATE rooms SET room_number = ?, room_floor = ?, category = ?, status = ?, price = ?, capacity = ? WHERE room_id = ?";
        
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)){
            
            ps.setString(1, room.getRoomNumber());
            ps.setString(2, room.getRoomFloor());
            ps.setString(3, room.getCategory());
            ps.setString(4, room.getStatus());
            ps.setInt(5, room.getPrice());
            ps.setString(6, room.getCapacity());
            ps.setInt(7, roomId);
            
            ps.executeUpdate();
            System.out.println("Update Success");
            
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    
    public void deleteRoom(String roomNumber){

        String query = "DELETE FROM rooms WHERE room_number = ?";

        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)){

            ps.setString(1, roomNumber);
            ps.executeUpdate();

        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public List<Rooms> getAvailableRooms(){

    List<Rooms> rooms = new ArrayList<>();

    String query = "SELECT * FROM rooms WHERE status = 'Available'";

    try(Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(query);
        ResultSet rs = ps.executeQuery()){

        while(rs.next()){

            Rooms room = new Rooms();

            room.setRoomId(rs.getInt("room_id"));
            room.setRoomNumber(rs.getString("room_number")); // ⭐ FIX
            room.setRoomFloor(rs.getString("room_floor"));
            room.setCategory(rs.getString("category"));
            room.setPrice(rs.getInt("price"));
            room.setCapacity(rs.getString("capacity"));
            room.setStatus(rs.getString("status"));

            rooms.add(room);
        }

    }catch(Exception e){
        e.printStackTrace();
    }

    return rooms;
}
    
    public boolean isRoomNumberExists(String RoomNumber) {
        String query = "SELECT room_id FROM rooms WHERE room_number = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, RoomNumber);
            ResultSet rs = ps.executeQuery();

            return rs.next(); 

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean isRoomNumberExistsExceptId(String roomNumber, int roomId) {

        String query = "SELECT room_id FROM rooms WHERE room_number = ? AND room_id != ?";

        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, roomNumber);
            ps.setInt(2, roomId);

            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch(Exception e){
            e.printStackTrace();
        }

        return false;
    }
    
    public int countRoomsByCategory(String category) {

        int count = 0;

        String query = "SELECT COUNT(*) FROM rooms WHERE category = ?";

        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, category);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                count = rs.getInt(1);
            }

        } catch(Exception e){
            e.printStackTrace();
        }

        return count;
    }
    
    public int getTotalRooms() {

        int total = 0;

        String query = "SELECT COUNT(*) FROM rooms";

        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery()) {

            if(rs.next()){
                total = rs.getInt(1);
            }

        } catch(Exception e){
            e.printStackTrace();
        }

        return total;
    }
    
    public int countRoomsByStatus(String status) {

        int count = 0;

        String query = "SELECT COUNT(*) FROM rooms WHERE status = ?";

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
}
