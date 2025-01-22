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
            Connection conn = DatabaseConnection.getConnection();

            String sql = "SELECT * FROM admin WHERE email = ? AND password = ? AND status = 1";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, password);  // Direct comparison without hashing

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                HttpSession session = request.getSession();
                session.setAttribute("email", email);
                session.setAttribute("adminName", rs.getString("first_name") + " " + rs.getString("last_name"));

                // Redirect to dashboard on success
                out.println("<script type='text/javascript'>");
                out.println("alert('Login successful!');");
                out.println("window.location.href = 'AdminDashboard.html';");  // Update with actual URL
                out.println("</script>");
            } else {
                // Print the email and password in the alert for debugging purposes
                out.println("<script type='text/javascript'>");
                out.println("alert('Invalid email or password. ');");
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
}
