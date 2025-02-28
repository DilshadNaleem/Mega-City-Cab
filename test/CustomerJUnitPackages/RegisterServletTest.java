package CustomerJUnitPackages;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import Customer.*;
import Customer.CService.*;

public class RegisterServletTest {
    
    private RegisterServlet registerServlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private PrintWriter writer;
    private EmailService emailservice;
    private DatabaseUtility dbUtility;
    private UniqueIdGenerator idGenerator;
    private OtpGenerator otpGenerator;
    private PasswordHasher hasher;
    private Customer customer;
    
    @Before 
    public void setUp() throws Exception {
        registerServlet = new RegisterServlet();
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
    public void testDoPostValidRegister() throws ServletException, IOException {
        String firstname = "dilshad";
        String lastName = "naleem";
        String email = "dilshadnaleem@gmail.com";
        String contactNumber = "0725958832";
        String password = "123456789";
        String nic = "12345678910";
        
        ((MockHttpServletRequest) request).setParameter("firstname", firstname);
        ((MockHttpServletRequest) request).setParameter("lastname", lastName);
        ((MockHttpServletRequest) request).setParameter("email", email);
        ((MockHttpServletRequest) request).setParameter("contact_number", contactNumber);
        ((MockHttpServletRequest) request).setParameter("password", password);
        ((MockHttpServletRequest) request).setParameter("nic", nic);
        
        HttpServletResponse responseMock = (HttpServletResponse) response;
        PrintWriter out = responseMock.getWriter();
        
        try {
            if (dbUtility.isEmailRegistered(email)) {
                out.println("<script>alert('Email already registered!');window.location.href='/Mega_City/Customer/Signin.html';</script>");
            } else {
                String uniqueId = idGenerator.generateUniqueId(dbUtility);
                String otp = otpGenerator.generateOTP();
                String hashedPassword = hasher.hashPassword(password);
                
                Customer customer = new Customer(uniqueId, firstname, lastName, email, contactNumber, hashedPassword, otp, nic);
                
                if (dbUtility.insertCustomer(customer)) {
                    HttpSession session = request.getSession();
                    session.setAttribute("email", email);
                    session.setAttribute("otp", customer.getOtp());
                    emailservice.sendOTP(email, otp);
                    out.println("<script>alert('Registration successful! Check your email for OTP.');window.location.href='./Customer/verification.html';</script>");
                } else {
                    out.println("<script>alert('Registration failed!');window.location.href='/Mega_City/Customer/Signin.html';</script>");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception occurred during registration process: " + e.getMessage());
        }
    }
}
