package Admin.Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import DatabaseConnection.DatabaseConnection;

public class DeleteVehicleTypeServlet extends HttpServlet {
  
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String vehicleId = request.getParameter("vehicleId"); // Get vehicleId from form
        String message;

        if (vehicleId == null || vehicleId.isEmpty()) {
            message = "Invalid Vehicle ID!";
            sendAlert(response, message);
            return;
        }

        String sql = "DELETE FROM vehicle_types WHERE unique_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, vehicleId);  // Set parameter
            
            int rowsDeleted = stmt.executeUpdate();  // Execute delete query
            
            if (rowsDeleted > 0) {
                message = "Vehicle deleted Successfully!";
            } else {
                message = "Vehicle not found!";
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            message = "SQL Error: " + ex.getMessage();
        } catch (Exception ex) {
            ex.printStackTrace();
            message = "Unexpected Error: " + ex.getMessage();
        }

        // Send alert message and reload page
        sendAlert(response, message);
    }

    private void sendAlert(HttpServletResponse response, String message) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<script type='text/javascript'>");
        out.println("alert('" + message + "');");
        out.println("window.location.href=document.referrer;");
        out.println("</script>");
    }
}
