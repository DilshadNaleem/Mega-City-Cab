
package Admin;

import jakarta.mail.MessagingException;


public class ApprovalEmailService {
    public static void sendApproveEmail(String driverEmail) throws MessagingException
    {
        EmailService emailService = new EmailService();
        String subject = "Vehicle Approval confirmation";
        String body = "Dear Driver, \n\n Your vehicle had been approved now its online in Mega City Cab service for Rentatl. \n\nBest Regards, \nMega City Cab";
        emailService.sendEmail(driverEmail, subject,body);
    }
}
