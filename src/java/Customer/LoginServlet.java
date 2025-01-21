package Customer;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Database configuration
    private static final String DB_URL = "jdbc:mysql://localhost:3306/mega_city";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Retrieve login form data
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            // Hash the entered password
            String hashedPassword = hashPassword(password);

            // Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish database connection
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // SQL query to validate user with status == 1
            String sql = "SELECT * FROM customers WHERE email = ? AND password = ? AND status = 1";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, hashedPassword);

            // Execute query and check result
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                // Login successful, create session
                HttpSession session = request.getSession();
                session.setAttribute("email", email);
                session.setAttribute("customerName", rs.getString("first_name") + " " + rs.getString("last_name"));

                // Redirect to the customer dashboard
                out.println("<script type='text/javascript'>");
                out.println("alert('Login successful!');");
                out.println("window.location.href = './Customer/Dashboard.html';");  // Adjust to actual dashboard URL
                out.println("</script>");
            } else {
                // Login failed
                out.println("<script type='text/javascript'>");
                out.println("alert('Invalid email or password, or account not verified.');");
                out.println("window.location.href = 'login.html';");
                out.println("</script>");
            }

            // Close connection
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<h1>Error: Unable to process the request.</h1>");
            out.println("<p>" + e.getMessage() + "</p>");
        }
    }

    // Hashing method using SHA-256
    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = digest.digest(password.getBytes("UTF-8"));

            StringBuilder hexString = new StringBuilder();
            for (byte b : hashedBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
