
package DriverJUnitPackages;

import MockHttp.MockHttpServletResponse;
import MockHttp.MockHttpSession;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import Driver.DriverVerifyOtpServlet;
import MockHttp.MockHttpServletRequest;

public class DriverVerifyOtpServletTest {
    private DriverVerifyOtpServlet verifyOtpServlet;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private MockHttpSession session;
    private PrintWriter writer;
    
    
    @Before
    public void setUp() throws Exception 
    {
        verifyOtpServlet =  new DriverVerifyOtpServlet();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        session = new MockHttpSession();
        writer = new PrintWriter(System.out);
        
        request.setSession(session);
    }
    
    @Test 
    public void testdoPostValidOtp() throws ServletException, IOException 
    {
        String otpEntered = "123456";
        String email = "customer@gmail.com";
        String storedOtp = "123456";
        
        request.setParameter("otp_code", otpEntered);
        
        if(otpEntered == storedOtp)
        {
            System.out.println("Success");
            response.sendRedirect("/Mega_City/Driver/Login.html");
        }
        else
        {
            System.out.println("Error");
            response.sendRedirect("/Mega_City/Driver/Signing.html");
        }
    }
    
    @Test
    public void testdoPostInvalidOtp() throws ServletException, IOException, Exception
    {
        String OtpEntered = "654321";
        String email = "customer@gmail.com";
        String storedOtp = "123456";
        
        request.setParameter("otp_code",OtpEntered);
        
        if(OtpEntered == storedOtp)
        {
            System.out.println("Success");
            response.sendRedirect("/Mega_City/Driver/Login.html");
        }
        else
        {
            System.out.println("Error");
            response.sendRedirect("/Mega_City/Driver/Signing.html");
        }
    }
}
