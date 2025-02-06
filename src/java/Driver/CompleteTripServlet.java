package Driver;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;

public class CompleteTripServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String bookingId = request.getParameter("bookingId");

        if (bookingId != null) {
            try {
                Connection conn = DatabaseConnection.getConnection();

                // Fetch return date, rent per day, and customer email
                String fetchQuery = "SELECT return_date, rent_per_day, customer_email FROM vehicle_bookings WHERE unique_id = ?";
                PreparedStatement fetchPs = conn.prepareStatement(fetchQuery);
                fetchPs.setString(1, bookingId);
                ResultSet rs = fetchPs.executeQuery();

                String customerEmail = null;
                if (rs.next()) {
                    LocalDate returnDate = LocalDate.parse(rs.getString("return_date"));
                    LocalDate returnedDate = LocalDate.now(); // Set returned_date as today
                    double rentPerDay = rs.getDouble("rent_per_day");
                    customerEmail = rs.getString("customer_email");

                    // Calculate fine if the vehicle is returned late
                    double fine = 0;
                    if (returnedDate.isAfter(returnDate)) {
                        long overdueDays = ChronoUnit.DAYS.between(returnDate, returnedDate);
                        fine = overdueDays * 2500;  // Example fine per day
                    }

                    // Update returned_date, fine, and status
                    String updateQuery = "UPDATE vehicle_bookings SET returned_date = NOW(), fine = ?, status = 'Completed' WHERE unique_id = ?";
                    PreparedStatement updatePs = conn.prepareStatement(updateQuery);
                    updatePs.setDouble(1, fine);
                    updatePs.setString(2, bookingId);
                    updatePs.executeUpdate();

                    // Send confirmation email to the customer
                    sendEmail(customerEmail, fine);
                }

                response.sendRedirect("/Mega_City/Driver/Bookings.jsp");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void sendEmail(String customerEmail, double fine) {
        String host = "smtp.gmail.com";  // SMTP host for Gmail
        final String user = "hypermarket403@gmail.com"; // Sender's email
        final String password = "prny fbme inmd nkzb"; // App-specific password for Gmail

        // Setup properties
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");  // SMTP port for Gmail
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true"); // Enable STARTTLS

        // Get the default Session object
        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        });

        try {
            // Create a default MimeMessage object
            MimeMessage message = new MimeMessage(session);

            // Set From: header field
            message.setFrom(new InternetAddress(user));

            // Set To: header field
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(customerEmail));

            // Set Subject: header field
            message.setSubject("Trip Completion Confirmation");

            // Set the actual message
            String emailContent = "Dear Customer,\n\nYour vehicle booking is complete. ";
            if (fine > 0) {
                emailContent += "You have been charged a fine of Rs. " + fine + " for the delayed return of the vehicle.";
            } else {
                emailContent += "Thank you for returning the vehicle on time. No fine has been applied.";
            }
            emailContent += "\n\nBest regards,\nMega City Rentals";

            message.setText(emailContent);

            // Send the message
            System.out.println("Preparing to send email...");
            Transport.send(message);
            System.out.println("Email sent successfully.");

        } catch (MessagingException e) {
            System.out.println("Error sending email: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
