package Driver.Class;

import DatabaseConnection.*;
import jakarta.servlet.http.Part;
import java.io.IOException;
import java.sql.*;


public class VehicleService {

    private FileService fileService;

    public VehicleService(FileService fileService) {
        this.fileService = fileService;
    }

    public void saveVehicle(String vehicleName, String vehicleYear, String brandName, String condition,
                            String mileage, String rentPerDay, String imagePath, String driverEmail, String color, String type) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO vehicles (unique_id, vehicle_name, vehicle_year, brand_name, vehicle_condition, mileage, rent_per_day, vehicle_image_path, driver_email, status, created_at, color, vehicle_cat) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, 'Pending', NOW(), ?, ?)")
        ) {
            String vehicleId = generateUniqueVehicleId(conn);
            
            stmt.setString(1, vehicleId);
            stmt.setString(2, vehicleName);
            stmt.setString(3, vehicleYear);
            stmt.setString(4, brandName);
            stmt.setString(5, condition);
            stmt.setString(6, mileage);
            stmt.setString(7, rentPerDay);
            stmt.setString(8, imagePath != null ? imagePath : "");
            stmt.setString(9, driverEmail);
            stmt.setString(10, color);
            stmt.setString(11, type);

            stmt.executeUpdate();
        }
    }

    private String generateUniqueVehicleId(Connection conn) throws SQLException {
        String query = "SELECT unique_id FROM vehicles ORDER BY created_at DESC LIMIT 1";
        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                String lastId = rs.getString("unique_id");
                int num = Integer.parseInt(lastId.split("_")[1]) + 1;
                return String.format("VEH_%02d", num);
            }
        }
        return "VEH_01"; // Default for first vehicle
    }

    public String saveVehicleImage(Part filePart) throws IOException {
        return fileService.saveFile(filePart);
    }
}
