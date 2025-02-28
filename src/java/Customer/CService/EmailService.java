package Customer.CService;

import Customer.Interface.EmailServiceInterface;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;

public class EmailService implements EmailServiceInterface {
    private static final String FROM_EMAIL = "hypermarket403@gmail.com";
    private static final String EMAIL_PASSWORD = "prny fbme inmd nkzb";

    @Override
    public void sendEmail(String toEmail, String subject, String body) throws MessagingException {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM_EMAIL, EMAIL_PASSWORD);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(FROM_EMAIL));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
        message.setSubject(subject);
        message.setText(body);

        Transport.send(message);
    }

    // Helper method for OTP emails
    public void sendOTP(String toEmail, String otp) throws MessagingException {
        String subject = "Your OTP for Registration";
        String body = "Your OTP is: " + otp + "\nPlease do not share this with anyone.";
        sendEmail(toEmail, subject, body); // Reuse the generic email-sending method
    }
}
