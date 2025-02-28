package Customer;

import Customer.CService.PasswordHasher;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;
import DatabaseConnection.*;

public class ResetPasswordServe extends HttpServlet {

    private Connection conn;

    @Override
    public void init() throws ServletException {
        try {
            // Get connection from DatabaseConnection
            conn = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException("Database connection error", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession(false);
        String sessionEmail = (session != null) ? (String) session.getAttribute("email") : null;

        try (PrintWriter out = response.getWriter()) {
            if (sessionEmail == null) {
                out.println("<h2>Error: No session email found. Please log in again.</h2>");
                return;
            }

            // Debugging log
            System.out.println("Session email: " + sessionEmail);

            String newPassword = request.getParameter("newPassword");
            String confirmPassword = request.getParameter("confirmPassword");

            if (newPassword == null || confirmPassword == null || newPassword.isEmpty() || confirmPassword.isEmpty()) {
                out.println("<h2>Error: Password fields cannot be empty.</h2>");
                return;
            }

            if (newPassword.equals(confirmPassword)) {
                // Use PasswordHasher to hash the password
                PasswordHasher hasher = new PasswordHasher();
                String hashedPassword = hasher.hashPassword(newPassword);

                String updateQuery = "UPDATE customers SET password = ? WHERE email = ?";
                try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
                    updateStmt.setString(1, hashedPassword);
                    updateStmt.setString(2, sessionEmail);

                    int rowsAffected = updateStmt.executeUpdate();

                    if (rowsAffected > 0) {
                         out.println("<script type='text/javascript'>");
                         out.println("alert('Update successful!');");
                         out.println("window.location.href = '/Mega_City/Customer/Signin.html';"); // Adjust to actual dashboard URL
                         out.println("</script>");
                    } else {
                        out.println("<h2>Error: Failed to update the password. Please try again later.</h2>");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    out.println("<h2>Error: Database update error. Please try again later.</h2>");
                }
            } else {
                out.println("<h2>Error: Passwords do not match. Please try again.</h2>");
            }
        } catch (IOException e) {
            e.printStackTrace();
            response.getWriter().println("<h2>Error: Unexpected error occurred. Please try again later.</h2>");
        }
    }

    @Override
    public void destroy() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
