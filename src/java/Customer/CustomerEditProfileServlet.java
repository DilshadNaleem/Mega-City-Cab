
package Customer;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.sql.*;
import java.sql.PreparedStatement;

/**
 *
 * @author HP
 */
public class CustomerEditProfileServlet extends HttpServlet {

  
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        Map<String,EditProfile> editProfile = new HashMap<>();
        
        String sessionEmail = (String) session.getAttribute("email");
        
        if(sessionEmail == null)
        {
            response.sendRedirect("Mega_City/Customer/Signin.html");
            return;
        }
        
        try (Connection conn = DatabaseConnection.getConnection())
        {
            if(conn == null)
            {
                System.out.println("Database Connection is null");
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database null");
                return;
            }
            
            String query = "SELECT * FROM customers WHERE email = ?";
            
            try (PreparedStatement stmt = conn.prepareStatement(query))
            {
                stmt.setString(1,sessionEmail);
                try (ResultSet rs = stmt.executeQuery())
                {
                    if(rs.next())
                    {
                        EditProfile profile = new EditProfile();
                        profile.setId(rs.getString("unique_id"));
                        profile.setFirstName(rs.getString("first_name"));
                        profile.setLastName(rs.getString("last_name"));
                        profile.setEmail(rs.getString("email"));
                        profile.setContactnumber(rs.getString("contact_number"));
                        profile.setNic(rs.getString("nic"));
                        
                        int status = rs.getInt("status");
                        profile.setStatus(status ==1 ? "Active" : "InActive");
                        
                        editProfile.put(profile.getId(),profile);
                        System.out.println("Profile Retrieved: " + profile.getEmail());
                    }
                    else
                    {
                        request.setAttribute("errorMessage", "No Profile details found");
                        System.out.println("No Profile details Available");
                    }
                }
            }
            request.setAttribute("profileDetails", editProfile);
            request.getRequestDispatcher("/Customer/Edit_Profile.jsp"). forward(request, response);
        }
        catch (SQLException ex)
        {
            System.err.println("SQL Exception: " + ex.getMessage());
            ex.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database Exception");
        }
        catch (Exception ex)
        {
            System.err.println("Exception: " + ex.getMessage());
            ex.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An Error Occured");
        }
    }
    }
