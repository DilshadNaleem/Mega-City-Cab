package Customer;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.util.*;

public class ProductServlet extends HttpServlet {

    private static final String UPLOAD_DIR = "http://localhost:8080/Mega_City/";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String vehicleType = request.getParameter("vehicleType");

        System.out.println("Received vehicle category: " + vehicleType);

        if (vehicleType == null || vehicleType.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Error: Vehicle category not provided.");
            return;
        }

        String query = "SELECT v.vehicle_name, v.vehicle_year, v.brand_name, vt.vehicle_cat, v.vehicle_image_path, " +
                       "v.vehicle_condition, v.mileage, v.rent_per_day, v.color " +
                       "FROM vehicles v " +
                       "LEFT JOIN vehicle_types vt ON v.vehicle_cat = vt.vehicle_cat " +
                       "WHERE v.vehicle_cat = ?";

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

                    System.out.println("Database Image Path: " + databaseImagePath);
                    System.out.println("Web Accessible Image Path: " + imagePath);

                    vehicleInfo.put("vehicle_image_path", imagePath);
                    vehicleDetails.add(vehicleInfo);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Database error occurred.");
            return;
        }

        request.setAttribute("vehicleDetails", vehicleDetails);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/Customer/Vehicle_Category.jsp");
        dispatcher.forward(request, response);
    }
}
