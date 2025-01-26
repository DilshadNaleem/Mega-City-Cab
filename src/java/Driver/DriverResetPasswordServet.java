
package Driver;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.*;
import java.util.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class DriverResetPasswordServet extends HttpServlet {

 private Connection conn;
 
 @Override
 public void init() throws ServletException {
     try{
         conn = DatabaseConnection.getConnection();
     }
     catch (SQLException ex)
     {
         ex.printStackTrace();
         throw new ServletException ("Database connection error ", ex);
     }
 }
 
protected void doPost(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {
    response.setContentType("text/html");
    HttpSession session = request.getSession(false);

    if (session == null || session.getAttribute("driveremail") == null) {
        try (PrintWriter out = response.getWriter()) {
            out.println("<h2>Error: No session email found. Please try again later!</h2>");
        }
        return;
    }

    String sessionEmail = (String) session.getAttribute("driveremail");
    System.out.println("Driver Session Email: " + sessionEmail);

    String newPassword = request.getParameter("newPassword");
    String confirmPassword = request.getParameter("confirmPassword");

    if (newPassword == null || confirmPassword == null || newPassword.isEmpty() || confirmPassword.isEmpty()) {
        try (PrintWriter out = response.getWriter()) {
            out.println("<h2>Error: Password fields cannot be empty</h2>");
        }
        return;
    }

    if (newPassword.equals(confirmPassword)) {
        String hashedPassword = hashPassword(newPassword);
        String updateQuery = "UPDATE driver SET password = ? WHERE email = ?";

        try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
            updateStmt.setString(1, hashedPassword);
            updateStmt.setString(2, sessionEmail);
            int rowsAffected = updateStmt.executeUpdate();

            if (rowsAffected > 0) {
                try (PrintWriter out = response.getWriter()) {
                    out.println("<script type=\"text/javascript\">");
                    out.println("alert('Password updated successfully!');");
                    out.println("window.location.href = 'login.html';");
                    out.println("</script>");
                }
            } else {
                try (PrintWriter out = response.getWriter()) {
                    out.println("<script type=\"text/javascript\">");
                    out.println("alert('Error: Failed to update the password. Please try again later.');");
                    out.println("window.history.back();");
                    out.println("</script>");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            try (PrintWriter out = response.getWriter()) {
                out.println("<h2>Error: Database update Error!</h2>");
            }
        }
    } else {
        try (PrintWriter out = response.getWriter()) {
            out.println("<script type=\"text/javascript\">");
            out.println("alert('Error: Passwords do not match. Please try again.');");
            out.println("window.history.back();");
            out.println("</script>");
        }
    }
}

 
 private String hashPassword(String password) throws IOException 
 {
     try
     {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashedBytes = md.digest(password.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b : hashedBytes)
        {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
     }
     catch (NoSuchAlgorithmException ex)
     {
         throw new IOException("Password hashing algorithm not available",ex);
     }
 }
 
}

