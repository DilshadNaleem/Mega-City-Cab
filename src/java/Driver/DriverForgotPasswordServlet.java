package Driver;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Properties;
import DatabaseConnection.*;

public class DriverForgotPasswordServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String FROM_EMAIL = "hypermarket403@gmail.com";
    private static final String EMAIL_PASSWORD = "prny fbme inmd nkzb";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws 
            ServletException, IOException {
        response.setContentType("text/html");
        String email = request.getParameter("email");
        HttpSession session = request.getSession();
        session.setAttribute("driveremail", email);

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try (PrintWriter out = response.getWriter()) {
            // Establish the database connection
            conn = DatabaseConnection.getConnection();

            String query = "SELECT * FROM driver WHERE email = ?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, email);
            rs = stmt.executeQuery();

            if (rs.next()) {
                try {
                    sendResetPasswordEmail(email);
                    response.sendRedirect("/Mega_City/Driver/Reset_Password.html");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    out.println("<h2>Error Sending email: " + ex.getMessage() + "</h2>");
                }
            } else {
                out.println("<h2>Email not found</h2>");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            response.getWriter().println("<h2>Database Error: " + ex.getMessage() + "</h2>");
        } finally {
            // Ensure all resources are closed
            DatabaseConnection.closeConnection(conn);
        }
    }

    private void sendResetPasswordEmail(String email) throws MessagingException, UnsupportedEncodingException {
        String host = "smtp.gmail.com";
        String port = "587";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM_EMAIL, EMAIL_PASSWORD);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(FROM_EMAIL, "Mega City"));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
        message.setSubject("Password Reset Request Notification");
        message.setText("We received a request to reset your password. If you did not request this, please ignore this message. If you need further assistance, contact support.");

        Transport.send(message);
    }
}
