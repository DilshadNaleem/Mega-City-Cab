package Admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AdminLoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Get input values from the form
        String email = request.getParameter("username");
        String password = request.getParameter("password");

        // Debugging: Print email and password values to ensure they are being passed correctly
        System.out.println("Email: " + email);  // This will print the email value to the console
        System.out.println("Password: " + password);  // This will print the password value to the console

        try {
            // Hash the provided password
            String hashedPassword = hashPassword(password);

            Connection conn = DatabaseConnection.getConnection();

            // SQL query to check if the email and hashed password match
            String sql = "SELECT * FROM admin WHERE email = ? AND password = ? AND status = 1";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, hashedPassword);  // Use the hashed password for comparison

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                HttpSession session = request.getSession();
                session.setAttribute("email", email);
                session.setAttribute("adminName", rs.getString("first_name") + " " + rs.getString("last_name"));

                // Redirect to dashboard on success
                out.println("<script type='text/javascript'>");
                out.println("alert('Login successful!');");
                out.println("window.location.href = '/Mega_City/Admin/Admin_Dashboard.jsp';");  // Update with actual URL
                out.println("</script>");
            } else {
                // Invalid credentials
                out.println("<script type='text/javascript'>");
                out.println("alert('Invalid email or password.');");
                out.println("window.location.href = 'login.html';");
                out.println("</script>");
            }

            conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            out.println("<h1>Error: Unable to process the request.</h1>");
            out.println("<p>" + ex.getMessage() + "</p>");
        }
    }

    // Method to hash the password using SHA-256
    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
