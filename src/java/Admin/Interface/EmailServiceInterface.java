
package Admin.Interface;

import jakarta.mail.MessagingException;


public interface EmailServiceInterface {
    void sendEmail(String toEmail, String subject, String body) throws MessagingException;
}
