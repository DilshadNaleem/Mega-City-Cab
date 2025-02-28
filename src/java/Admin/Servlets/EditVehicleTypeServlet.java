package Admin.Servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import DatabaseConnection.DatabaseConnection;

public class EditVehicleTypeServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String vehicleId = request.getParameter("vehicleId");
        String vehicleCategory = request.getParameter("vehicleCategory");
        String vehicleDescription = request.getParameter("vehicleDescription");
        String newVehicleImage = request.getParameter("vehicleImage"); // Get new image name if provided

        String vehicleImage = null; // To store the final image path

        try (Connection conn = DatabaseConnection.getConnection()) {
            // Step 1: Fetch existing image if no new image is provided
            if (newVehicleImage == null || newVehicleImage.trim().isEmpty()) {
                String fetchImageQuery = "SELECT vehicle_image FROM vehicle_types WHERE unique_id = ?";
                try (PreparedStatement fetchStmt = conn.prepareStatement(fetchImageQuery)) {
                    fetchStmt.setString(1, vehicleId);
                    ResultSet rs = fetchStmt.executeQuery();
                    if (rs.next()) {
                        vehicleImage = rs.getString("vehicle_image"); // Keep old image path
                    }
                }
            } else {
                vehicleImage = "vehicle_types/" + newVehicleImage; // Use new image
            }

            // Step 2: Update the database with the final image path
            String sql = "UPDATE vehicle_types SET vehicle_cat = ?, vehicle_desc = ?, vehicle_image = ? WHERE unique_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, vehicleCategory);
                stmt.setString(2, vehicleDescription);
                stmt.setString(3, vehicleImage); // Save new or existing image
                stmt.setString(4, vehicleId);

                int rowsUpdated = stmt.executeUpdate();
                if (rowsUpdated > 0) {
                    response.sendRedirect("manageVehicles.jsp?success=Vehicle updated successfully");
                } else {
                    response.sendRedirect("editVehicle.jsp?error=Update failed");
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            response.sendRedirect("editVehicle.jsp?error=SQL Exception: " + ex.getMessage());
        }
    }
}
