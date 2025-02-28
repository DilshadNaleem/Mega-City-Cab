package Admin.Servlets;

import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import DatabaseConnection.*;


public class AdminResetPasswordServet extends HttpServlet {

    private Connection conn;

    @Override
    public void init() throws ServletException {
        try {
            // Use the connection from Admin.DatabaseConnection
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
        HttpSession session = request.getSession(false); // Retrieve the existing session
        if (session == null || session.getAttribute("adminemail") == null) {
            try (PrintWriter out = response.getWriter()) {
                out.println("<h2>Error: No session email found. Please log in again.</h2>");
            }
            return;
        }

        String sessionEmail = (String) session.getAttribute("adminemail");
        System.out.println("Admin session email: " + sessionEmail);

        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        if (newPassword == null || confirmPassword == null || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            try (PrintWriter out = response.getWriter()) {
                out.println("<h2>Error: Password fields cannot be empty.</h2>");
            }
            return;
        }

        if (newPassword.equals(confirmPassword)) {
            String hashedPassword = hashPassword(newPassword);
            String updateQuery = "UPDATE admin SET password = ? WHERE email = ?";

            try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
                updateStmt.setString(1, hashedPassword);
                updateStmt.setString(2, sessionEmail);
                int rowsAffected = updateStmt.executeUpdate();

                try (PrintWriter out = response.getWriter()) {
                    if (rowsAffected > 0) {
                        out.println("<h2>Password updated successfully.</h2>");
                    } else {
                        out.println("<h2>Error: Failed to update the password. Please try again later.</h2>");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                try (PrintWriter out = response.getWriter()) {
                    out.println("<h2>Error: Database update error. Please try again later.</h2>");
                }
            }
        } else {
            try (PrintWriter out = response.getWriter()) {
                out.println("<h2>Error: Passwords do not match. Please try again.</h2>");
            }
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
