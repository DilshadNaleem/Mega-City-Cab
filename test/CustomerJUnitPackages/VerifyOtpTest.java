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

public class VerifyOtpTest {
    private VerifyOtpServlet verifyOtpServlet;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private MockHttpSession session;
    private PrintWriter writer;

    @Before
    public void setUp() throws Exception {
        verifyOtpServlet = new VerifyOtpServlet();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        session = new MockHttpSession();
        writer = new PrintWriter(System.out);
        
        request.setSession(session); // Ensure session is set in the request
    }

    @Test
    public void testDoPostInvalidOtp() throws ServletException, IOException {
        // Setup mock data
        String otpEntered = "123456";  // OTP entered by user
        String email = "dilshad@gmail.com";
        String storedOtp = "654321";  // Stored OTP, which does not match the entered OTP

        // Set parameters and session attributes
        request.setParameter("otp_code", otpEntered);
        
        if(otpEntered == storedOtp)
        {
            System.out.println("Success");
        }
        else
        {
            System.out.println("Error");
        }
    }
    
    @Test
    public void testDoPostValidOtp() throws ServletException, IOException {
        String otpEntered = "123456";
        String email = "abc@gmail.com";
        String storedOtp = "123456";
        
        request.setParameter("otp_code", otpEntered);
        
        if(otpEntered == storedOtp)
        {
            System.out.println("Success");
        }
        else
        {
            System.out.println("Error");
        }
    }
}
