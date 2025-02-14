
package Admin;

import java.sql.*;


public class VehicleRepository {
    private final Connection conn;

    public VehicleRepository(Connection conn) {
        this.conn = conn;
    }

    public void saveVehicle(String uniqueId, String category, String description, String imagePath) throws SQLException {
        String sql = "INSERT INTO vehicle_types (unique_id, vehicle_cat, vehicle_desc, vehicle_image, created_at) VALUES (?, ?, ?, ?, NOW())";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, uniqueId);
            stmt.setString(2, category);
            stmt.setString(3, description);
            stmt.setString(4, imagePath);
            stmt.executeUpdate();
        }
    }

    public String getLastUniqueID() {
        String query = "SELECT unique_id FROM vehicle_types ORDER BY unique_id DESC LIMIT 1";
        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getString("unique_id");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}

