package Customer;

import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ResetPasswordServe extends HttpServlet {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/mega_city";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    private Connection conn;

    @Override
    public void init() throws ServletException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException("Database connection error", e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new ServletException("JDBC Driver not found", e);
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

            // Print session email to console for debugging
            System.out.println("Session email: " + sessionEmail);

            String newPassword = request.getParameter("newPassword");
            String confirmPassword = request.getParameter("confirmPassword");

            if (newPassword == null || confirmPassword == null || newPassword.isEmpty() || confirmPassword.isEmpty()) {
                out.println("<h2>Error: Password fields cannot be empty.</h2>");
                return;
            }

            if (newPassword.equals(confirmPassword)) {
                String hashedPassword = hashPassword(newPassword);
                String updateQuery = "UPDATE customers SET password = ? WHERE email = ?";

                try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
                    updateStmt.setString(1, hashedPassword);
                    updateStmt.setString(2, sessionEmail);
                    int rowsAffected = updateStmt.executeUpdate();

                    if (rowsAffected > 0) {
                        out.println("<h2>Password updated successfully.</h2>");
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

    private String hashPassword(String password) throws IOException {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new IOException("Password hashing algorithm not available", e);
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
