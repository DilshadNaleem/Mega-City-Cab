
package Admin;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import DatabaseConnection.*;

public class ApprovalVehicleDatabaseClass {
    public static String getDriverEmail (String vehicleId) throws SQLException 
    {
        String query = "SELECT driver_email FROM vehicles WHERE unique_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query))
        {
            stmt.setString(1, vehicleId);
            try(ResultSet rs = stmt.executeQuery())
            {
                if(rs.next())
                {
                    return rs.getString("driver_email");
                }
            }
        }
       return  ""; 
    }
    
    
    public static boolean updateVehicleStatus(String vehicleId) throws SQLException
    {
        String query = "UPDATE vehicles SET status = 'Confirmed' WHERE unique_id = ? ";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query))
                {
                    stmt.setString(1, vehicleId);
                    return stmt.executeUpdate() > 0;
                }
    }
}
