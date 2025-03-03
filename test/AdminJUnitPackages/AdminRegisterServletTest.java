
package AdminJUnitPackages;

import MockHttp.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import AServices.*;
import Admin.Servlets.AdminRegisterServlet;
import Admin.EmailService;

public class AdminRegisterServletTest {
    
    private AdminRegisterServlet registerServlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private PrintWriter writer;
    private EmailService emailservice;
    private DatabaseUtility dbUtility;
    private UniqueIdGenerator idGenerator;
    private OtpGenerator otpGenerator;
    private PasswordHasher hasher;
    private Admin admin;
    
    @Before 
    public void setUp() throws Exception {
        registerServlet = new AdminRegisterServlet();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        session = new MockHttpSession();
        writer = new PrintWriter(System.out);
        emailservice = new EmailService();
        dbUtility = new DatabaseUtility();
        idGenerator = new UniqueIdGenerator();
        otpGenerator = new OtpGenerator();
        hasher = new PasswordHasher();
        
    }
    
    @Test 
    public void testDoPostValidRegister() throws ServletException, IOException 
    {
        String firstName = "John";
        String lastName = "Abraham";
        String email = "dilshadnaleem@gmail.com";
        String contactNumber = "0725958832";
        String password = "123456789";
        String nic = "12345678910";
        
        ((MockHttpServletRequest) request).setParameter("firstname",firstName);
        ((MockHttpServletRequest) request).setParameter("lastname",lastName);
        ((MockHttpServletRequest) request).setParameter("email",email);
        ((MockHttpServletRequest) request).setParameter("nic",nic);
        ((MockHttpServletRequest) request).setParameter("contact",contactNumber);
        ((MockHttpServletRequest) request).setParameter("password",password);
        
        HttpServletResponse responseMock = (HttpServletResponse) response;
        PrintWriter out = responseMock.getWriter();
        
        try 
        {
            if(dbUtility.isEmailRegistered(email))
            {
                out.println("<script>alert ('Email is Already Registered!'); window.location.href='/Mega_City/Admin/Login.html'</script>");
            } else
            {
                 String uniqueId = idGenerator.generateUniqueId(dbUtility);
                String otp = otpGenerator.generateOTP();
                String hashedPassword = hasher.hashPassword(password);
                
                Admin admin = new Admin(uniqueId, firstName, lastName, email, contactNumber, nic, password, otp);
                
                if(dbUtility.insertAdmin(admin))
                {
                    HttpSession session = request.getSession();
                    session.setAttribute ("adminemail", email);
                    session.setAttribute("otp", admin.getOtp());
                    emailservice.sendOTP(email, otp);
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
            fail("Exception occurred during registration process: " + ex.getMessage());
        }
    }
}
