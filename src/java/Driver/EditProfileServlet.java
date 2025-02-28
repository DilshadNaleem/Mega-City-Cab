package Driver;

import Driver.Class.EditProfile;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import DatabaseConnection.*;

public class EditProfileServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        ArrayList<EditProfile> editProfile = new ArrayList<>();

        // Get driver email from session
        String sessionEmail = (String) session.getAttribute("driveremail");

        if (sessionEmail == null) {
            response.sendRedirect("Driver/Login.html");
            return;
        }

        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn == null) {
                System.out.println("Database connection is null");
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database Connection Error");
                return;
            }

            // Fetch driver details using email
            String query = "SELECT unique_id, first_name, last_name, email, contact_number, nic, status FROM driver WHERE email = ?";

            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, sessionEmail);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        EditProfile profile = new EditProfile();
                        profile.setUniqueId(rs.getString("unique_id"));
                        profile.setFirstName(rs.getString("first_name"));
                        profile.setLastName(rs.getString("last_name"));
                        profile.setEmail(rs.getString("email"));
                        profile.setContactNumber(rs.getString("contact_number"));
                        profile.setNic(rs.getString("nic"));

                        // Convert status: 1 -> "Active", otherwise -> "Inactive"
                        int status = rs.getInt("status");
                        profile.setStatus(status == 1 ? "Active" : "Inactive");

                        editProfile.add(profile);
                        System.out.println("Profile retrieved: " + profile.getEmail());
                    } else {
                        request.setAttribute("errorMessage", "No profile details found.");
                        System.out.println("No profile details available.");
                    }
                }
            }

            // Set profile details and forward to JSP
            request.setAttribute("profileDetails", editProfile);
            request.getRequestDispatcher("/Driver/Edit_Profile.jsp").forward(request, response);

        } catch (SQLException ex) {
            System.err.println("SQL Exception: " + ex.getMessage());
            ex.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database Error");
        } catch (Exception ex) {
            System.err.println("Exception: " + ex.getMessage());
            ex.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred.");
        }
    }
}
