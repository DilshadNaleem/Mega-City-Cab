package Admin;

import Admin.Interface.EmailServiceInterface;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EmailService implements EmailServiceInterface{
    private static final String FROM_EMAIL = "hypermarket403@gmail.com";
    private static final String EMAIL_PASSWORD = "prny fbme inmd nkzb";
    
    public void sendEmail(String toEmail, String subject, String body) throws MessagingException
    {
        Properties pro = new Properties();
        pro.put("mail.smtp.host", "smtp.gmail.com");
        pro.put("mail.smtp.port", "587");
        pro.put("mail.smtp.auth", "true");
        pro.put("mail.smtp.starttls.enable", "true");
        
        Session session = Session.getInstance(pro, new Authenticator()
        {
           protected PasswordAuthentication getPasswordAuthentication()
           {
               return new PasswordAuthentication(FROM_EMAIL, EMAIL_PASSWORD);
           }
        });
        
        Message msg = new MimeMessage(session);
        try 
        {
            msg.setFrom(new InternetAddress(FROM_EMAIL, "Mega City"));
        }
        catch (AddressException e)
        {
            throw new MessagingException("Invalid sender address or display name", e);
        } 
        catch (UnsupportedEncodingException ex) 
        {
            Logger.getLogger(EmailService.class.getName()).log(Level.SEVERE, "Unsupported encoding exception", ex);
            throw new MessagingException("Error setting the sender's email address", ex);  // Rethrow as MessagingException
        }
        
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
        msg.setSubject(subject);
        msg.setText(body);
        
        Transport.send(msg);
    }
    
    public void sendOTP (String toEmail, String otp) throws MessagingException 
    {
        String subject = "Your OTP for Registration";
        String body = "Your OTP is " + otp + "\n Please do not share with anyone.";
        sendEmail(toEmail, subject, body);
    }
}
