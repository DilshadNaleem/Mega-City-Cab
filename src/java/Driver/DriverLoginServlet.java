package Driver;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import DatabaseConnection.*;

public class DriverLoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Retrieve email and password from the request
        String email = request.getParameter("username");
        String password = request.getParameter("password");

        // Debugging: Print email and password
        System.out.println("Received Email: " + email);
        System.out.println("Received Password: " + password);

        try {
            // Hash the password
            String hashedPassword = hashPassword(password);
            System.out.println("Hashed Password: " + hashedPassword);

            // Get database connection
            Connection conn = DatabaseConnection.getConnection();
            System.out.println("Database connection established.");

            // Prepare SQL query
            String sql = "SELECT * FROM driver WHERE email = ? AND password = ? AND status = 1";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, hashedPassword);

            System.out.println("Executing Query: " + sql);

            // Execute query
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Valid login
                HttpSession session = request.getSession();
                session.setAttribute("driveremail", email);
                session.setAttribute("driverName", rs.getString("first_name") + " " + rs.getString("last_name"));

                // Redirect to driver dashboard
                out.println("<script type='text/javascript'>");
                out.println("alert('Login successful!');");
                out.println("window.location.href = '/Mega_City/Driver/Driver_Dashboard.jsp';"); // Update this URL if needed
                out.println("</script>");
            } else {
                // Invalid credentials
                out.println("<script type='text/javascript'>");
                out.println("alert('Invalid email or password. Received email: " + email + ", hashed password: " + hashedPassword + "');");
                out.println("window.location.href = 'login.html';");
                out.println("</script>");
            }
            conn.close();
        } catch (Exception ex) {
            // Handle any errors
            ex.printStackTrace();
            out.println("<h1>Error: Unable to process the request.</h1>");
            out.println("<p>" + ex.getMessage() + "</p>");
        }
    }

    // Hash password using SHA-256
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
