package Customer;

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

public class VerifyOtpServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String otpEntered = request.getParameter("otp_code");
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
        String storedOtp = (String) session.getAttribute("otp");

        if (storedOtp != null && otpEntered != null && otpEntered.equals(storedOtp)) {
            Connection conn = null;
            try {
                conn = DatabaseConnection.getConnection();

                String query = "SELECT status FROM customers WHERE email = ?";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, email);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    int status = rs.getInt("status");

                    if (status == 1) {
                        out.println("<script type='text/javascript'>");
                        out.println("alert('Account already verified.');");
                        out.println("window.location.href = 'login.html';");
                        out.println("</script>");
                    } else {
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
            } catch (Exception e) {
                e.printStackTrace();
                out.println("<h1>Error: Unable to process the request.</h1>");
                out.println("<p>" + e.getMessage() + "</p>");
            } finally {
                DatabaseConnection.closeConnection(conn);
            }
        } else {
            out.println("<script type='text/javascript'>");
            out.println("alert('Invalid OTP. Please try again.');");
            out.println("window.location.href = '/Mega_City/Customer/verification.html';");
            out.println("</script>");
        }
    }
}
