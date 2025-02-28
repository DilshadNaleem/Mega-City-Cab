
package Admin.Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.*;
import java.sql.PreparedStatement;
import DatabaseConnection.*;


public class DeleteCustomerServlet extends HttpServlet {

  
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if(session == null || session.getAttribute("email") == null)
        {
            response.sendRedirect("/Mega_City/Admin/Admin_Login.html");
            return;
        }
        
        
        String customer  = request.getParameter("id");
        String sql = "DELETE FROM customers WHERE unique_id = ?";
        try(Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setString(1, customer);
            
            int rowsUpdated = stmt.executeUpdate();
            
            if(rowsUpdated > 0)
            {
                System.out.println("Delete success");
                response.sendRedirect("Success.html");
            } 
            else
            {
                System.out.println("Delete failed");
                response.sendRedirect("Error.jsp");
            }
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
            System.err.println("SQL Exception : " + ex.getMessage());
        }
    }
}
