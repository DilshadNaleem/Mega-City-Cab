package Driver;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import Driver.DatabaseConnection;
import jakarta.mail.Session;
import jakarta.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.Random;
import jakarta.mail.*;  
import jakarta.mail.internet.*;  
import java.util.Properties;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;





public class DriverRegisterServlet extends HttpServlet {
private static final long serialVersionUID = 1L;
    private static final String FROM_EMAIL = "hypermarket403@gmail.com";
    private static final String EMAIL_PASSWORD = "prny fbme inmd nkzb";
    
    
@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String email = request.getParameter("email");
        String contactnumber = request.getParameter("contact_number");
        String nic = request.getParameter("nic");
        String password = request.getParameter("password");
        
        String uniqueId = generateUniqueId();
        String otp = generateOTP();
        
        Driver driver = new Driver (uniqueId, firstname, lastname, email, password, otp, contactnumber, nic);
        String hashedPassword = hashPassword(password);
        
        Connection conn = null;
        
        try {
           conn = DatabaseConnection.getConnection();
           String sql = "INSERT INTO driver (unique_id, first_name, last_name, email, password, contact_number,nic, image, created_at, status) VALUES (?,?,?,?,?,?,?,'account.png',NOW(),0)";
           PreparedStatement stmt = conn.prepareStatement(sql);
           stmt.setString(1, driver.getUniqueId());
           stmt.setString(2, driver.getFirstName());
           stmt.setString(3, driver.getLastName());
           stmt.setString(4, driver.getEmail());
           stmt.setString(5, hashedPassword);
           stmt.setString(6, driver.getPhonenumber());
           stmt.setString(7, driver.getNic());
           
           int rowsInserted = stmt.executeUpdate();
           if (rowsInserted > 0)
           {
               HttpSession session = request.getSession();
               session.setAttribute("driveremail", email);
               session.setAttribute("otp", otp);
               
               sendOTPEmail (email, otp, out);
               
                out.println("<script type='text/javascript'>");
                out.println("alert('Registration Successful! OTP has been sent to your email.');");
                out.println("window.location.href = './Driver/verification.html';");
                out.println("</script>");
           } else 
           {
                out.println("<script type='text/javascript'>");
                out.println("alert('Registration Failed. Please try again.');");
                out.println("window.location.href = 'registration.html';");
                out.println("</script>");
           }
            
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            out.println("<h1>Error: Unable to process the request.</h1>");
            out.println("<p>" + ex.getMessage() + "</p>");
        } finally 
        {
            DatabaseConnection.closeConnection(conn);
        }
    }
    
    private String generateUniqueId()
    {
        String uniqueId = "DR_01";
        Connection conn = null;
        
        try 
        {
           conn = DatabaseConnection.getConnection();
           String query = "SELECT unique_id FROM driver ORDER BY unique_id DESC LIMIT 1";
           PreparedStatement stmt = conn.prepareStatement(query);
           ResultSet rs = stmt.executeQuery();
           
           if (rs.next())
           {
               String lastUniqueId = rs.getString("unique_id");
               int lastNumber = Integer.parseInt(lastUniqueId.substring(3));
               uniqueId = "DR_" + String.format("%02d" + lastNumber + 1);
           }
        } 
        
        catch (Exception ex)
        {
            ex.printStackTrace();
        } finally 
        {
            DatabaseConnection.closeConnection(conn);
        }
        return uniqueId;
    }
    
    private String generateOTP() 
    {
        Random rand = new Random();
        return String.format("%06d", rand.nextInt(1000000));
    }
    
    private void sendOTPEmail (String toEmail, String otp, PrintWriter out)
    {
        try 
        {
            Properties pro = new Properties();
            pro.put("mail.smtp.host", "smtp.gmail.com");
            pro.put("mail.smtp.port", "587");
            pro.put("mail.smtp.auth", "true");
            pro.put("mail.smtp.starttls.enable","true");
            
            Session session = Session.getInstance(pro, new jakarta.mail.Authenticator()
                    {
                        protected PasswordAuthentication getPasswordauthentication ()
                        {
                            return new PasswordAuthentication (FROM_EMAIL, EMAIL_PASSWORD);
                        }
                    });
            
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("Mega City Cab"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Your OTP for Registration");
            message.setText("Your OTP is: " + otp);
            
            Transport.send(message);
            System.out.println("OTP Sent Successfully");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            out.println("<h1>Error sending OTP email</h1>");
            out.println("<p>" + ex.getMessage() + "</p>");
        }
    }
    
     private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
