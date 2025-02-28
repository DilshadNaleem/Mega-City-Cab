
package AServices;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.sql.PreparedStatement;
import DatabaseConnection.*;


public class StaffSearchService {
    
    public List<StaffManageService> searchStaff(String searchQuery)
    {
        List<StaffManageService> staffList = new ArrayList<>();
        
        try 
        {
            String sql = "SELECT * FROM staff WHERE first_name LIKE ? OR last_name LIKE ? OR email LIKE ?";
            Connection conn = DatabaseConnection.getConnection();
            
            if(conn == null)
            {
                System.out.println("Connection is null");
                
            }
            
            PreparedStatement stmt = conn.prepareStatement(sql);
            String query = "%" + searchQuery + "%";
            stmt.setString(1,query);
            stmt.setString(2, query);
            stmt.setString(3, query);
            
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next())
            {
                StaffManageService staff = new StaffManageService();
                staff.setUniqueId(rs.getString("unique_id"));
                staff.setFirstName(rs.getString("first_name"));
                staff.setLastName(rs.getString("last_name"));
                staff.setEmail(rs.getString("email"));
                staff.setNic(rs.getString("nic"));
                staff.setCreatedat(rs.getString("created_at"));
                staff.setStatus(rs.getString("status"));
                staff.setVehicleName(rs.getString("vehicle_name"));
                staff.setTotalEarnings(rs.getDouble("total_earnings"));
                
                staffList.add(staff);
            }
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
            System.err.println("SQL Exception: " + ex.getMessage());
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            System.out.println("Exception: " + ex.getMessage());
        }
        
        return staffList;
    }
}
