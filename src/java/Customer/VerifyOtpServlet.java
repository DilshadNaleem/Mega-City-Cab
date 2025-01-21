package Customer;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class VerifyOtpServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Database configuration
    private static final String DB_URL = "jdbc:mysql://localhost:3306/mega_city";  
    private static final String DB_USER = "root";  
    private static final String DB_PASSWORD = "";  

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Retrieve the OTP entered by the user and the email from the session
        String otpEntered = request.getParameter("otp_code");
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
        String storedOtp = (String) session.getAttribute("otp");

        // Check if OTP entered by the user matches the one stored in the session
        if (storedOtp != null && otpEntered != null && otpEntered.equals(storedOtp)) {
            try {
                // Establish database connection
                Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

                // Query to check the current verification status of the customer
                String query = "SELECT status FROM customers WHERE email = ?";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, email);
                var rs = stmt.executeQuery();

                // Check if the customer exists and if the status is 0 (unverified)
                if (rs.next()) {
                    int status = rs.getInt("status");

                    if (status == 1) {
                        out.println("<script type='text/javascript'>");
                        out.println("alert('Account already verified.');");
                        out.println("window.location.href = 'login.html';");
                        out.println("</script>");
                    } else {
                        // If OTP is valid, update the status to 1 (verified)
                        String updateQuery = "UPDATE customers SET status = 1 WHERE email = ?";
                        PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
                        updateStmt.setString(1, email);
                        int rowsUpdated = updateStmt.executeUpdate();

                        if (rowsUpdated > 0) {
                            out.println("<script type='text/javascript'>");
                            out.println("alert('Account verified successfully!');");
                            out.println("window.location.href = '/Mega_City/Customer/Signin.html';");
                            out.println("</script>");
                        } else {
                            out.println("<script type='text/javascript'>");
                            out.println("alert('Verification failed. Please try again.');");
                            out.println("window.location.href = '/Mega_City/Customer/verification.html';");
                            out.println("</script>");
                        }
                    }
                } else {
                    out.println("<script type='text/javascript'>");
                    out.println("alert('Account not found. Please register first.');");
                    out.println("window.location.href = 'registration.html';");
                    out.println("</script>");
                }

                // Close the connection
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
                out.println("<h1>Error: Unable to process the request.</h1>");
                out.println("<p>" + e.getMessage() + "</p>");
            }
        } else {
            out.println("<script type='text/javascript'>");
            out.println("alert('Invalid OTP. Please try again.');");
            out.println("window.location.href = '/Mega_City/Customer/verification.html';");
            out.println("</script>");
        }
    }
}
