
package AServices;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import DatabaseConnection.*;

public class AdminService {
    
    public boolean validateAdmin(String email, String hashpassword) throws Exception 
    {
        String sql ="SELECT * FROM admin WHERE email = ? AND password =? AND status ==1";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setString(1, email);
            stmt.setString(2, hashpassword);
           
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (Exception ex)
        {
           ex.printStackTrace();
           return false;
        }          
    }
    
    
    public String getAdminName(String email)
    {
        String sql = "SELECT first_name, last_name FROM admin WHERE email= ? ";
        
        try (Connection conn = DatabaseConnection.getConnection(); 
                PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            
            if(rs.next())
            {
                return rs.getString("first_name") + " " + rs.getString("last_name");
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return null;
    }
}
