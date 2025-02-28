package Customer.CService;

import java.sql.*;
import java.util.*;
import DatabaseConnection.*;

public class VehicleServiceImpl implements VehicleService {

    private static final String UPLOAD_DIR = "http://localhost:8080/Mega_City/";

    @Override
    public List<Map<String, String>> getVehiclesByType(String vehicleType) throws SQLException {
        String query = "SELECT v.vehicle_name, v.vehicle_year, v.brand_name, vt.vehicle_cat, v.vehicle_image_path, " +
                       "v.vehicle_condition, v.mileage, v.rent_per_day, v.color " +
                       "FROM vehicles v " +
                       "LEFT JOIN vehicle_types vt ON v.vehicle_cat = vt.vehicle_cat " +
                       "WHERE v.vehicle_cat = ? AND status = 'Confirmed'";

        List<Map<String, String>> vehicleDetails = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, vehicleType);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Map<String, String> vehicleInfo = new HashMap<>();
                    vehicleInfo.put("vehicle_name", rs.getString("vehicle_name"));
                    vehicleInfo.put("vehicle_year", rs.getString("vehicle_year"));
                    vehicleInfo.put("brand_name", rs.getString("brand_name"));
                    vehicleInfo.put("vehicle_cat", rs.getString("vehicle_cat"));
                    vehicleInfo.put("vehicle_condition", rs.getString("vehicle_condition"));
                    vehicleInfo.put("mileage", rs.getString("mileage"));
                    vehicleInfo.put("rent_per_day", rs.getString("rent_per_day"));
                    vehicleInfo.put("color", rs.getString("color"));

                    String databaseImagePath = rs.getString("vehicle_image_path");
                    String imagePath = UPLOAD_DIR + databaseImagePath;

                    vehicleInfo.put("vehicle_image_path", imagePath);
                    vehicleDetails.add(vehicleInfo);
                }
            }
        }

        return vehicleDetails;
    }

    @Override
    public List<Map<String, String>> searchVehiclesInType(String vehicleType, String searchQuery) throws SQLException {
        String query = "SELECT v.vehicle_name, v.vehicle_year, v.brand_name, vt.vehicle_cat, v.vehicle_image_path, " +
                       "v.vehicle_condition, v.mileage, v.rent_per_day, v.color " +
                       "FROM vehicles v " +
                       "LEFT JOIN vehicle_types vt ON v.vehicle_cat = vt.vehicle_cat " +
                       "WHERE v.vehicle_cat = ? AND (v.vehicle_name LIKE ? OR v.brand_name LIKE ? OR v.vehicle_year LIKE ?) AND status = 'Confirmed'";

        List<Map<String, String>> vehicleDetails = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            String searchPattern = "%" + searchQuery + "%";  // Wildcard search for partial matches
            stmt.setString(1, vehicleType);
            stmt.setString(2, searchPattern);
            stmt.setString(3, searchPattern);
            stmt.setString(4, searchPattern);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Map<String, String> vehicleInfo = new HashMap<>();
                    vehicleInfo.put("vehicle_name", rs.getString("vehicle_name"));
                    vehicleInfo.put("vehicle_year", rs.getString("vehicle_year"));
                    vehicleInfo.put("brand_name", rs.getString("brand_name"));
                    vehicleInfo.put("vehicle_cat", rs.getString("vehicle_cat"));
                    vehicleInfo.put("vehicle_condition", rs.getString("vehicle_condition"));
                    vehicleInfo.put("mileage", rs.getString("mileage"));
                    vehicleInfo.put("rent_per_day", rs.getString("rent_per_day"));
                    vehicleInfo.put("color", rs.getString("color"));

                    String databaseImagePath = rs.getString("vehicle_image_path");
                    String imagePath = UPLOAD_DIR + databaseImagePath;

                    vehicleInfo.put("vehicle_image_path", imagePath);
                    vehicleDetails.add(vehicleInfo);
                }
            }
        }

        return vehicleDetails;
    }

    @Override
    public List<Map<String, String>> searchVehicles(String searchQuery) throws SQLException {
        String query = "SELECT v.vehicle_name, v.vehicle_year, v.brand_name, vt.vehicle_cat, v.vehicle_image_path, " +
                       "v.vehicle_condition, v.mileage, v.rent_per_day, v.color " +
                       "FROM vehicles v " +
                       "LEFT JOIN vehicle_types vt ON v.vehicle_cat = vt.vehicle_cat " +
                       "WHERE (v.vehicle_name LIKE ? OR v.brand_name LIKE ? OR v.vehicle_year LIKE ?) AND status = 'Confirmed'";

        List<Map<String, String>> vehicleDetails = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            String searchPattern = "%" + searchQuery + "%";  // Wildcard search for partial matches
            stmt.setString(1, searchPattern);
            stmt.setString(2, searchPattern);
            stmt.setString(3, searchPattern);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Map<String, String> vehicleInfo = new HashMap<>();
                    vehicleInfo.put("vehicle_name", rs.getString("vehicle_name"));
                    vehicleInfo.put("vehicle_year", rs.getString("vehicle_year"));
                    vehicleInfo.put("brand_name", rs.getString("brand_name"));
                    vehicleInfo.put("vehicle_cat", rs.getString("vehicle_cat"));
                    vehicleInfo.put("vehicle_condition", rs.getString("vehicle_condition"));
                    vehicleInfo.put("mileage", rs.getString("mileage"));
                    vehicleInfo.put("rent_per_day", rs.getString("rent_per_day"));
                    vehicleInfo.put("color", rs.getString("color"));

                    String databaseImagePath = rs.getString("vehicle_image_path");
                    String imagePath = UPLOAD_DIR + databaseImagePath;

                    vehicleInfo.put("vehicle_image_path", imagePath);
                    vehicleDetails.add(vehicleInfo);
                }
            }
        }

        return vehicleDetails;
    }
}
