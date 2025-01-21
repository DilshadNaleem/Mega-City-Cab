
package Customer;

public class EmailService {
    
    public void sendResetPasswordEmail(String email, String token) {
        String resetPasswordLink = "http://localhost:8080/Mega_City/Customer/Reset_Password.html?token=" + token;
        String subject = "Password Reset Request";
        String body = "We received a request to reset your password. Please click the link below to reset it:\n\n" + resetPasswordLink;
        
        // Use your email sending logic (e.g., using JavaMail or any other library)
        // Send email to the user with the reset link
    }
}
