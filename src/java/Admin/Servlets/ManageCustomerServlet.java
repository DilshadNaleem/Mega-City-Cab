
package Admin.Servlets;

import AServices.CustomerService;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.*;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Map;
import DatabaseConnection.*;


public class ManageCustomerServlet extends HttpServlet {

   
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String sql = "SELECT * FROM customers";
        HttpSession session = request.getSession(false);
        
        if(session == null || session.getAttribute("email") == null)
        {
            response.sendRedirect("/Mega_City/Admin/Admin_Login.html");
            return;
        }
        
        try(Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql))
        {
            if(conn == null)
            {
                System.out.println("Connection is null");
                return;
            }
          
            ResultSet rs = stmt.executeQuery();
            Map <String, CustomerService> customerservice = new HashMap<>();
            while(rs.next())
            {
             CustomerService customer = new CustomerService();
             customer.setUniqueId(rs.getString("unique_id"));
             customer.setFirstname(rs.getString("first_name"));
             customer.setLastname(rs.getString("last_name"));
             customer.setEmail(rs.getString("email"));
             customer.setContactnumber(rs.getString("contact_number"));
             customer.setNic(rs.getString("nic"));
             customer.setStatus(rs.getString("status"));
             customer.setCreatedAt(rs.getString("created_at"));
             
             customerservice.put(customer.getUniqueId(), customer);
            }
            
            request.setAttribute("customerservice", customerservice);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/Admin/Manage_Customers.jsp");
            dispatcher.forward(request, response);
        }
        
        catch(SQLException ex)
        {
            ex.printStackTrace();
            System.err.println("SQL Exception: " + ex.getMessage());
        }
        
        catch (Exception ex)
        {
            ex.printStackTrace();
            System.err.println("Exception: " + ex.getMessage());
        }
    }
}
