
package AServices;

import jakarta.servlet.http.Part;
import java.io.IOException;
import java.sql.*;
import DatabaseConnection.*;

public class VehicleRepository {
    private FileUploader fileService;

    public VehicleRepository(FileUploader fileService) {
        this.fileService = fileService;
    }
    
  
    public void saveVehicle(String uniqueId, String category, String description, String imagePath) throws SQLException {
        String sql = "INSERT INTO vehicle_types (unique_id, vehicle_cat, vehicle_desc, vehicle_image, created_at) VALUES (?, ?, ?, ?, NOW())";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, uniqueId);
            stmt.setString(2, category);
            stmt.setString(3, description);
            stmt.setString(4, imagePath);
            stmt.executeUpdate();
        }
    }

    public String getLastUniqueID() {
        String query = "SELECT unique_id FROM vehicle_types ORDER BY unique_id DESC LIMIT 1";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getString("unique_id");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    public String saveVehicleImage(Part filePart) throws IOException 
    {
        return fileService.saveFile(filePart);
    }
}

