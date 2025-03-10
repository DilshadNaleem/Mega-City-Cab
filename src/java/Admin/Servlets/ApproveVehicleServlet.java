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
import java.io.PrintWriter;

public class ApproveVehicleServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String vehicleId = request.getParameter("vehicleId");

        try {
            String driverEmail = ApprovalVehicleDatabaseClass.getDriverEmail(vehicleId);
            boolean isUpdated = ApprovalVehicleDatabaseClass.updateVehicleStatus(vehicleId);

            if (isUpdated) {
                if (!driverEmail.isEmpty()) {
                    ApprovalEmailService.sendApproveEmail(driverEmail);
                    System.out.println("Approval email sent successfully.");
                } else {
                    System.out.println("Driver email not found, skipping email notification.");
                }

                // Send JavaScript alert on success
                response.setContentType("text/html");
                PrintWriter out = response.getWriter();
                out.println("<script type=\"text/javascript\">");
                out.println("alert('Vehicle Approved successfully!');");
                out.println("window.location.href='/Mega_City/Admin/Admin_Dashboard.jsp';");
                out.println("</script>");
                out.close();
                return;  // Ensure no further execution

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
