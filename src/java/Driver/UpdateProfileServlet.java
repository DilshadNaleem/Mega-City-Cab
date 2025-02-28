package Driver;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import DatabaseConnection.*;

public class UpdateProfileServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    
        HttpSession session = request.getSession();
        if(session == null || session.getAttribute("driveremail") == null) {
            response.sendRedirect("/Mega_City/Driver/Login.html");
            return;
        }
        
        String driverEmail = (String) session.getAttribute("driveremail");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        
        if(firstName == null || lastName == null || firstName.trim().isEmpty() || lastName.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Invalid Input");
            request.getRequestDispatcher("Edit_Profile.jsp").forward(request, response);
            return;
        }
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            if(conn == null) {
                request.setAttribute("errorMessage", "Database connection error");
                request.getRequestDispatcher("/Mega_City/Driver/Edit_Profile.jsp").forward(request, response);
                return;
            }
            
            String sql = "UPDATE driver SET first_name = ?, last_name = ? WHERE email = ?";
            
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, firstName);
                stmt.setString(2, lastName);
                stmt.setString(3, driverEmail);
                
                int rowsUpdated = stmt.executeUpdate();
                if (rowsUpdated > 0) {
                    // Redirect to Dashboard with a success message as an alert
                    response.sendRedirect("/Mega_City/Driver/Driver_Dashboard.jsp?success=Profile Updated Successfully");
                } else {
                    request.setAttribute("errorMessage", "Error updating profile.");
                    request.getRequestDispatcher("/Mega_City/Driver/Edit_Profile.jsp").forward(request, response);
                }
            }
        } catch (SQLException ex) {     
            ex.printStackTrace();
            request.setAttribute("errorMessage", "Database error: " + ex.getMessage());
            request.getRequestDispatcher("/Mega_City/Driver/Edit_Profile.jsp").forward(request, response);
        }
    }
}
