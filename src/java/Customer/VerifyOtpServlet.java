package Customer;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
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

        if (storedOtp == null || otpEntered == null || !otpEntered.equals(storedOtp)) {
            displayAlert(out, "Invalid OTP. Please try again.", "/Mega_City/Customer/verification.html");
            return;
        }

        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();

            if (!CustomerHelper.isEmailRegistered(conn, email)) {
                displayAlert(out, "Account not found. Please register first.", "registration.html");
                return;
            }

            if (CustomerHelper.isAccountVerified(conn, email)) {
                displayAlert(out, "Account already verified.", "login.html");
                return;
            }

            if (CustomerHelper.updateAccountStatus(conn, email)) {
                displayAlert(out, "Account verified successfully!", "/Mega_City/Customer/Signin.html");
            } else {
                displayAlert(out, "Verification failed. Please try again.", "/Mega_City/Customer/verification.html");
            }

        } catch (Exception e) {
            e.printStackTrace();
            out.println("<h1>Error: Unable to process the request.</h1>");
            out.println("<p>" + e.getMessage() + "</p>");
        } finally {
            DatabaseConnection.closeConnection(conn);
        }
    }

    private void displayAlert(PrintWriter out, String message, String redirectUrl) {
        out.println("<script type='text/javascript'>");
        out.println("alert('" + message + "');");
        out.println("window.location.href = '" + redirectUrl + "';");
        out.println("</script>");
    }
}
