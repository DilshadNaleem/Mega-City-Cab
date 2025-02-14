
package Admin;
import java.sql.*;
import java.sql.PreparedStatement;

public class RejectedVehicleDatabaseClass {
    
   public static boolean updateVehicleStatusReject (String vehicleId) throws SQLException 
   {
       String query = "UPDATE vehicles SET status = 'Rejected' WHERE unique_id = ? ";
       try (Connection conn = DatabaseConnection.getConnection();
               PreparedStatement stmt = conn.prepareStatement(query))
       {
           stmt.setString(1,vehicleId);
           return stmt.executeUpdate() > 0;
       }
   }
}
