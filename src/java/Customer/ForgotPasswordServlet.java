package Customer;

import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;

public class ForgotPasswordServlet extends HttpServlet {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/mega_city";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = ""; // Adjust as needed
    
    private static final String FROM_EMAIL = "hypermarket403@gmail.com"; 
    private static final String EMAIL_PASSWORD = "prny fbme inmd nkzb"; // Gmail App Password
    
    private Connection conn;

    public void init() throws ServletException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new ServletException("MySQL Driver not found", e);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException("Database connection error", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        String email = request.getParameter("email");

        HttpSession session = request.getSession();  // Create or retrieve the session
        session.setAttribute("email", email);        // Save the email to the session

        try (PrintWriter out = response.getWriter()) {
            String query = "SELECT * FROM customers WHERE email = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, email);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    try {
                        sendResetPasswordEmail(email);
                        response.sendRedirect("/Mega_City/Customer/Reset_Password.html");
                    } catch (MessagingException e) {
                        e.printStackTrace();
                        out.println("<h2>Error sending email: " + e.getMessage() + "</h2>");
                    }
                } else {
                    out.println("<h2>Email not found.</h2>");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("<h2>Database error: " + e.getMessage() + "</h2>");
        }
    }

    private void sendResetPasswordEmail(String email) throws MessagingException {
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
        message.setFrom(new InternetAddress(FROM_EMAIL));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
        message.setSubject("Password Reset Request Notification");
        message.setText("We received a request to reset your password. If you did not request this, please ignore this message. If you need further assistance, contact support.");

        Transport.send(message);
    }
}
