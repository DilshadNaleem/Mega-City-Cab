
package Admin;

import jakarta.mail.MessagingException;


public class RejectEmailService {
    public static void sendRejectMail(String driverEmail) throws MessagingException 
    {
        EmailService emailService = new EmailService();
        String subject = "Vehicle Rejected";
        String body = "Dear Driver, \n\n Your vehicle has been Rejected due to insufficient details. Please try again with the Suficient Details.\n\n Best Regards, \nMega City Cab";
        emailService.sendEmail(driverEmail,subject,body);
    }
}
