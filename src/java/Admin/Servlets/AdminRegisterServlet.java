package Admin.Servlets;


import AServices.Admin;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
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
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import AServices.DatabaseUtility;
import AServices.DatabaseUtility;
import Admin.EmailService;
import AServices.OtpGenerator;
import AServices.PasswordHasher;
import AServices.UniqueIdGenerator;
public class AdminRegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String email = request.getParameter("email");
        String nic = request.getParameter("nic");
        String contact = request.getParameter("contact");
        String password = request.getParameter("password");
        
        DatabaseUtility dbUtility = new DatabaseUtility();
        UniqueIdGenerator idGen = new UniqueIdGenerator();
        OtpGenerator otpGenerator = new OtpGenerator();
        PasswordHasher hasher = new PasswordHasher();
        EmailService emailService = new EmailService();
        
        try {
           if (dbUtility.isEmailRegistered(email))
                    {
                        out.println("<script>alert ('Email is Already Registered!'); window.location.href='/Mega_City/Admin/Login.html'</script>");
                    } 
           else
           {
               String uniqueId = idGen.generateUniqueId(dbUtility);
               String otp = otpGenerator.generateOTP();
               String hashedPassword = hasher.hashPassword(password);
               
               Admin admin = new Admin(uniqueId, firstname, lastname, email, contact, nic, hashedPassword,otp);
               if(dbUtility.insertAdmin(admin))
               {
                   HttpSession session = request.getSession();
                   session.setAttribute ("adminemail", admin.getEmail());
                   session.setAttribute("otp", admin.getOtp());
                   emailService.sendOTP(email, otp);
                   out.println("<script>alert('Registration successful! Check your email for OTP.');window.location.href='./Admin/verification.html';</script>");
               } else
               {
                    out.println("<script>alert('Registration failed!');window.location.href='/Mega_City/Admin/Login.html';</script>");
               }
           }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            out.println("<h1> Error: " + ex.getMessage()+" </h1>");
        }
        
    }
}
