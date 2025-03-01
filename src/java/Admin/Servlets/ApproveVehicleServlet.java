package Admin.Servlets;


import jakarta.mail.MessagingException;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import Admin.ApprovalVehicleDatabaseClass;
import Admin.ApprovalEmailService;
import Admin.ApprovalEmailService;
import Admin.ApprovalVehicleDatabaseClass;

public class ApproveVehicleServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String vehicleId = request.getParameter("vehicleId");

        try {
            String driverEmail = ApprovalVehicleDatabaseClass.getDriverEmail(vehicleId);
            boolean isUpdated = ApprovalVehicleDatabaseClass.updateVehicleStatus(vehicleId);

            if (isUpdated) {
                System.out.println("Vehicle status updated successfully.");
                if (!driverEmail.isEmpty()) {
                    ApprovalEmailService.sendApproveEmail(driverEmail);
                    System.out.println("Approval email sent successfully.");
                } else {
                    System.out.println("Driver email not found, skipping email notification.");
                }
            } else {
                System.out.println("No vehicles found with the given ID.");
            }
        } catch (SQLException ex) {
            System.out.println("Database Error: " + ex.getMessage());
            ex.printStackTrace();
        } catch (Exception ex) {
            System.out.println("Unexpected Error: " + ex.getMessage());
            ex.printStackTrace();
        }

        response.sendRedirect("/Mega_City/Admin/Admin_Dashboard.jsp");
    }
}
