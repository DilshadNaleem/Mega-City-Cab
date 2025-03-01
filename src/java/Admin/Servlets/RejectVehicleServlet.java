
package Admin.Servlets;

import Admin.ApprovalVehicleDatabaseClass;
import Admin.RejectEmailService;
import AServices.RejectedVehicleDatabaseClass;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;


public class RejectVehicleServlet extends HttpServlet {

  
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            String vehicleId = request.getParameter("vehicleId");
        
        try 
        {
            String driverEmail = ApprovalVehicleDatabaseClass.getDriverEmail(vehicleId);
            boolean update = RejectedVehicleDatabaseClass.updateVehicleStatusReject(vehicleId);
            
            if(update)
            {
                System.out.println("Vehicle statud Updated Successfully");
                if(!driverEmail.isEmpty())
                {
                    RejectEmailService.sendRejectMail(driverEmail);
                    System.out.println("Rejected Email Sent Successfully");
                } else
                {
                    System.out.println("Driver Email not found, skipping the email notification");
                } 
            } else
            {
                System.out.println("No vehicles found with given Id");
            }
        } catch (SQLException ex)
        {
            System.out.println("Database Error! " + ex.getMessage());
            ex.printStackTrace();
        } catch (Exception ex)
        {
            System.out.println("Unexpected Error " + ex.getMessage());
            ex.printStackTrace();
        }
        
        response.sendRedirect("/Mega_City/Admin/Admin_Dashboard.jsp");
    }

    }
    
