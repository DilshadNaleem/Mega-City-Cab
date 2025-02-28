package Driver.Class;

import java.sql.*;

public class VehicleSearchService {

    public ResultSet searchVehicles(Connection conn, String searchQuery) throws SQLException {
        String sql = "SELECT * FROM vehicles";

        PreparedStatement psmt;

        if (searchQuery != null && !searchQuery.isEmpty()) {
            sql += " WHERE vehicle_name LIKE ? OR brand_name LIKE ?";
            psmt = conn.prepareStatement(sql);
            psmt.setString(1, "%" + searchQuery + "%");
            psmt.setString(2, "%" + searchQuery + "%");
        } else {
            psmt = conn.prepareStatement(sql);
        }

        return psmt.executeQuery();
    }
}
