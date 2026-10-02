package Controllers;

import Models.Users;
import DataBase.DBConnection;
import Models.Rooms;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserController {
    
    public void addStaff(Users user){
        String query = "INSERT INTO user(firstname, lastname, gmail, roles, username, password) VALUES (?,?,?,?,?,?)";
        
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)){
            
            ps.setString(1, user.getFirstname());
            ps.setString(2, user.getLastname());
            ps.setString(3, user.getGmail());
            ps.setString(4, user.getRoles());
            ps.setString(5, user.getUsername());
            ps.setString(6, user.getPassword());
            
            ps.executeUpdate();
            System.out.println("Register Success");
            
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    
    public void updateUserInformation(Users user, int id){
        String query = "UPDATE user SET firstname = ?, lastname = ?, gmail = ?, username = ? WHERE id = ?";
        
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)){
            
            ps.setString(1, user.getFirstname());
            ps.setString(2, user.getLastname());
            ps.setString(3, user.getGmail());
            ps.setString(4, user.getUsername());
            ps.setInt(5, id);
            
            ps.executeUpdate();
            System.out.println("Update Success");
            
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    
    public boolean changePassword(int id, String currentPassword, String newPassword){
        String checkQuery = "SELECT password FROM user WHERE id = ?";
        String updateQuery = "UPDATE user SET password = ? WHERE id = ?";

        try(Connection conn = DBConnection.getConnection();
            PreparedStatement checkPs = conn.prepareStatement(checkQuery)){

            checkPs.setInt(1, id);
            ResultSet rs = checkPs.executeQuery();

            if(rs.next()){
                String dbPassword = rs.getString("password");
                String hashedCurrent = hashPassword(currentPassword);
                
                if(!dbPassword.equals(hashedCurrent)){
                    return false;
                }
                
                String hashedNew = hashPassword(newPassword);

                try(PreparedStatement updatePs = conn.prepareStatement(updateQuery)){

                    updatePs.setString(1, hashedNew);
                    updatePs.setInt(2, id);

                    updatePs.executeUpdate();
                    return true;
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    
    public Users getUserById(int id){
        Users user = null;
        String query = "SELECT * FROM user WHERE id = ?";

        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)){

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                user = new Users();
                user.setId(rs.getInt("id"));
                user.setFirstname(rs.getString("firstname"));
                user.setLastname(rs.getString("lastname"));
                user.setGmail(rs.getString("gmail"));
                user.setUsername(rs.getString("username"));
                user.setRoles(rs.getString("roles"));
            }

        }catch(SQLException e){
            e.printStackTrace();
        }

        return user;
    }
    
    public List<Users> showUser(){
        List <Users> users = new ArrayList<>();
        String query = "SELECT * FROM user";

        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
                
            ResultSet rs = ps.executeQuery()){
            
            while(rs.next()) {
                Users user = new Users();
                user.setId(rs.getInt("id"));
                user.setFirstname(rs.getString("firstname"));
                user.setLastname(rs.getString("lastname"));
                user.setGmail(rs.getString("gmail"));
                user.setRoles(rs.getString("roles"));
                user.setStatus(rs.getString("status"));
                user.setUsername(rs.getString("username"));
                
                users.add(user);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return users;
    }
    
    public void updateStaff(Users user, int id){
        String query = "UPDATE user SET firstname = ?, lastname = ?, gmail = ?, roles = ?, status = ?, username = ? WHERE id = ?";
        
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)){
            
            ps.setString(1, user.getFirstname());
            ps.setString(2, user.getLastname());
            ps.setString(3, user.getGmail());
            ps.setString(4, user.getRoles());
            ps.setString(5, user.getStatus());
            ps.setString(6, user.getUsername());
            ps.setInt(7, id);
            
            ps.executeUpdate();
            System.out.println("Update Success");
            
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    
    public static String hashPassword(String password){
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes){
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch(NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    
    public boolean isUsernameExists(String username) {
        String query = "SELECT id FROM user WHERE username = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            return rs.next(); 

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean isEmailExists(String gmail, int userId) {
        String query = "SELECT COUNT(*) FROM user WHERE gmail = ? AND id != ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, gmail);
            ps.setInt(2, userId);
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()){
                int count = rs.getInt(1); 
                return count > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    
    
    public Users authenticate(String username, String password){
        String sql = "SELECT * FROM user WHERE username = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){

                String storedPassword = rs.getString("password");
                String hashedInput = hashPassword(password);
                
                if(storedPassword.equals(hashedInput)){

                    Users user = new Users();
                    user.setId(rs.getInt("id"));
                    user.setFirstname(rs.getString("firstname"));
                    user.setLastname(rs.getString("lastname"));
                    user.setUsername(rs.getString("username"));
                    user.setRoles(rs.getString("roles"));
                    user.setStatus(rs.getString("status"));

                    return user;
                }
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    
    public int[] getUserCounts() {
        String query = "SELECT " +
                       "COUNT(*) AS totalUsers, " +
                       "SUM(CASE WHEN status = 'Active' THEN 1 ELSE 0 END) AS activeUsers, " +
                       "SUM(CASE WHEN status = 'Inactive' THEN 1 ELSE 0 END) AS inactiveUsers " +
                       "FROM user";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                int total = rs.getInt("totalUsers");
                int active = rs.getInt("activeUsers");
                int inactive = rs.getInt("inactiveUsers");
                return new int[]{total, active, inactive};
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new int[]{0, 0, 0};
    }
    
    public static void main(String[] args) {
        
    }
    
}
