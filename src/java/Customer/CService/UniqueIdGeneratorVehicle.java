
package Customer.CService;
import java.sql.*;
import DatabaseConnection.*;

public class UniqueIdGeneratorVehicle {
    
public String generateUniqueId() {
        String uniqueId = "order_01";
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT unique_id FROM vehicle_bookings ORDER BY booking_id DESC LIMIT 1";
            try (PreparedStatement ps = conn.prepareStatement(query);
                 ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String lastId = rs.getString("unique_id");
                    if (lastId != null && lastId.startsWith("order_")) {
                        int num = Integer.parseInt(lastId.substring(6)) + 1;
                        uniqueId = String.format("order_%02d", num);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return uniqueId;
    }
}
