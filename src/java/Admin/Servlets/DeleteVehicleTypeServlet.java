package Admin.Servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import DatabaseConnection.*;

public class DeleteVehicleTypeServlet extends HttpServlet {
  
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String vehicleId = request.getParameter("vehicleId"); // Get vehicleId from form

        if (vehicleId == null || vehicleId.isEmpty()) {
            response.sendRedirect("error.jsp?message=Invalid ID");
            return;
        }

        String sql = "DELETE FROM vehicle_types WHERE unique_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, vehicleId);  // Set parameter
            
            int rowsDeleted = stmt.executeUpdate();  // Execute delete query
            
            if (rowsDeleted > 0) {
                response.sendRedirect("manageVehicles.jsp?success=Vehicle deleted successfully");
            } else {
                response.sendRedirect("manageVehicles.jsp?error=Vehicle not found");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            response.sendRedirect("error.jsp?message=SQL Error: " + ex.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
            response.sendRedirect("error.jsp?message=Error: " + ex.getMessage());
        }
    }
}
