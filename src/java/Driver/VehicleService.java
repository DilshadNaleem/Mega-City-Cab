
package Driver;

import jakarta.servlet.http.Part; // Correct import for file uploads
import java.io.IOException;
import java.sql.*;

public class VehicleService {

    private FileService fileService;

    // Constructor to inject FileService dependency
    public VehicleService(FileService fileService) {
        this.fileService = fileService;
    }

    public void saveVehicle(String vehicleName, String vehicleYear, String brandName, String condition,
                            String mileage, String rentPerDay, String imagePath, String driverEmail, String color, String type) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO vehicles (vehicle_name, vehicle_year, brand_name, vehicle_condition, mileage, rent_per_day, vehicle_image_path, driver_email, status, created_at,color,type) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, 'Pending', NOW(),?,?)")) {

            stmt.setString(1, vehicleName);
            stmt.setString(2, vehicleYear);
            stmt.setString(3, brandName);
            stmt.setString(4, condition);
            stmt.setString(5, mileage);
            stmt.setString(6, rentPerDay);
            stmt.setString(7, imagePath != null ? imagePath : "");
            stmt.setString(8, driverEmail);
            stmt.setString(9, color);
            stmt.setString(10, type);

            stmt.executeUpdate();
        }
    }

    public String saveVehicleImage(Part filePart) throws IOException {
        return fileService.saveFile(filePart); // Use the correct file service to save the image
    }
    
    
}
