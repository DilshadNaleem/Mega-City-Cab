
package AServices;
import AServices.Admin;
import java.sql.*;
import java.sql.PreparedStatement;
import DatabaseConnection.*;

public class DatabaseUtility {
    
    

    public boolean isEmailRegistered(String email) throws SQLException {
        String query = "SELECT email FROM admin WHERE email = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             java.sql.PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }
    }

    
    
    public boolean insertAdmin(Admin admin ) throws SQLException {
        String query = "INSERT INTO admin (unique_id, first_name, last_name, email, NIC, contact_number, password, created_at,status) "
                + "VALUES (?,?,?,?,?,?,?,NOW(),0)";
        
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query))
        {
            stmt.setString(1, admin.getUniqueId());
            stmt.setString(2, admin.getFirstName());
            stmt.setString(3, admin.getLastName());
            stmt.setString(4, admin.getEmail());
            stmt.setString(5, admin.getNic());
            stmt.setString(6, admin.getContactNumber());
            stmt.setString(7, admin.getPassword());
            
            return stmt.executeUpdate() > 0;
        }
    }
    
    public String getLastUniqueId() throws SQLException {
        String query = "SELECT unique_id FROM admin ORDER BY unique_id DESC LIMIT 1";
        
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery())
        {
            if(rs.next())
            {
                return rs.getString("unique_id");
            }
        }
        return null;
    }
}
