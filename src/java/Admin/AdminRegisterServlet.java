package Admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import jakarta.servlet.ServletException;  
import jakarta.servlet.http.HttpServlet;  
import jakarta.servlet.http.HttpServletRequest;  
import jakarta.servlet.http.HttpServletResponse;  
import jakarta.servlet.http.HttpSession;  
import java.util.Random;
import jakarta.mail.*;  
import jakarta.mail.internet.*;  
import java.util.Properties;

public class AdminRegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Database configuration
    private static final String DB_URL = "jdbc:mysql://localhost:3306/mega_city";  
    private static final String DB_USER = "root";  
    private static final String DB_PASSWORD = "";  
   
    private static final String FROM_EMAIL = "hypermarket403@gmail.com";
    private static final String EMAIL_PASSWORD = "prny fbme inmd nkzb";
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("firstname");
        String email = request.getParameter("email");
        String contactnumber = request.getParameter("contact");
        String NIC = request.getParameter("nic");
        String password = request.getParameter("password");
        
        String uniqueId = generateUniqueId();
        String otp = generateOTP();
        
        Admin admin = new Admin (uniqueId,firstname,lastname,email,contactnumber,otp,NIC,password);
        
        
        try 
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            Connection conn = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
            
            String sql = "INSERT INTO admin (unique_id, first_name,last_name,email,contact_number,password,NIC,status, created_at) VALUES (?,?,?,?,?,?,?,0,NOW())";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1,admin.getUniqueId());
            stmt.setString(2,admin.getFirstName());
            stmt.setString(3,admin.getLastName());
            stmt.setString(4,admin.getEmail());
            stmt.setString(5,admin.getContactNumber());
            stmt.setString(6,admin.getPassword());
            stmt.setString(7,admin.getNic());
            
            int rowsInserted = stmt.executeUpdate();
            if(rowsInserted > 0)
            {
                HttpSession session = request.getSession();
                session.setAttribute("email", email);
                session.setAttribute("otp", otp);
                
                sendOTPEmail (email, otp, out);
                
                out.println("<script type='text/javascript'>");
                out.println("alert('Registration Successful! OTP has been sent to your email.');");
                out.println("window.location.href = './Customer/verification.html';");  // Redirect to verification.html
                out.println("</script>");
            } else {
                out.println("<script type='text/javascript'>");
                out.println("alert('Registration Failed. Please try again.');");
                out.println("window.location.href = 'registration.html';");  // Redirect to registration page
                out.println("</script>");
            }

            // Close the database connection
            conn.close();
                
            }
        catch (Exception e)
                {
            e.printStackTrace();
            out.println("<h1>Error: Unable to process the request.</h1>");
            out.println("<p>" + e.getMessage() + "</p>");
                }
    }
    
    private String generateUniqueId() {
        String uniqueId = "AD_01";
        
        try {
            Connection conn = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
            
            String query = "SELECT uniqu_id FROM admin ORDER BY unique_id DESC LIMIT 1";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            
            if(rs.next()) 
            {
                String lastUniqueId = rs.getString("unique_id");
                int lastNumber = Integer.parseInt(lastUniqueId.substring(4));
                uniqueId = "AD_" + String.format("%02d", lastNumber + 1);
            }
            
            conn.close();
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return uniqueId;
    }
    
private String generateOTP ()
{
    Random rand = new Random();
    return String.format ("%06d", rand.nextInt(1000000));
}

private void sendOTPEmail (String toEmail, String otp, PrintWriter out)
{
    try 
    {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port","587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        
        
        Session session = Session.getInstance(properties, new jakarta.mail.Authenticator()
        {
            protected PasswordAuthentication getPasswordAuthentication()
            {
                return new PasswordAuthentication(FROM_EMAIL,EMAIL_PASSWORD);
            }
        });
        
        Message message  = new MimeMessage(session);
        message.setFrom(new InternetAddress(FROM_EMAIL));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
        message.setSubject("Your OTP for Registration");
        message.setText("Your OTP is: " + otp);
        
        Transport.send(message);
        System.out.println("OTP send Successfully");
    }
    catch (MessagingException e)
    {
        e.printStackTrace();
        out.println("<h1>Error sending OTP email</h1>");
        out.println("<p>" + e.getMessage() + "</p>");
    }
}
}
