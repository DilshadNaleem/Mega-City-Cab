
package Admin;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.*;
import java.sql.PreparedStatement;

public class EditVehicleTypeServlet extends HttpServlet {

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
   {
       String vehicleId = request.getParameter("vehicleId");
       String vehicleCategory = request.getParameter("vehicleCategory");
       String vehicleDescription = request.getParameter("vehicleDescription");
       String vehicleImage = "vehicle_types/" + request.getParameter("vehicleImage");
       
       try(Connection conn = DatabaseConnection.getConnection())
       {
           String sql = "UPDATE vehicle_types SET vehicle_cat=? , vehicle_desc = ? , vehicle_image =? WHERE unique_id= ?";
           PreparedStatement stmt = conn.prepareStatement(sql);
           stmt.setString(1, vehicleCategory);
           stmt.setString(2, vehicleDescription);
           stmt.setString(3, vehicleImage);
           stmt.setString(4, vehicleId);
           
           int rowsUpdated = stmt.executeUpdate();
           if(rowsUpdated > 0)
           {
               response.sendRedirect("manageVehicles.jsp?success=Vehicle updated successfully");
               return;
           } 
           else
           {
              response.sendRedirect("editVehicle.jsp?error=Update failed"); 
           }
       }
       catch(SQLException ex)
       {
           ex.printStackTrace();
           System.out.println("SQL Exception: " + ex.getMessage());
       }
       catch(Exception ex)
       {
           ex.printStackTrace();
           System.out.println("EXception: " + ex.getMessage());
       }
   }
}
