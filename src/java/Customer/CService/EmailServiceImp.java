package Customer.CService;

import Customer.Interface.EmailServiceInterface;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;

public class EmailServiceImp implements EmailServiceInterface {

    
    public void sendBookingConfirmation(String toEmail, String customerName, String vehicle, String bookingDate, 
                                        String bookingTime, String returnDate, double totalFare, String driver, String driverContact) {
        String fromEmail = "hypermarket403@gmail.com";
        String emailPassword = "prny fbme inmd nkzb";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, emailPassword);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Booking Confirmation - " + vehicle);
            String content = "<h3>Dear " + customerName + ",</h3>"
                    + "<p>Your booking has been confirmed:</p>"
                    + "<p>Vehicle: " + vehicle + "<br>Pickup Date: " + bookingDate + "<br>Return Date: " + returnDate
                    + "<br>Total Fare: Rs. " + totalFare + "</p>";
            message.setContent(content, "text/html");
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendEmail(String toEmail, String subject, String body) throws MessagingException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
