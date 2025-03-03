
package AdminJUnitPackages;
import MockHttp.MockHttpServletResponse;
import MockHttp.MockHttpSession;
import MockHttp.MockHttpServletRequest;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import AServices.*;
import Admin.Servlets.*;

public class AdminVerifyOtpTest {
    private AdminVerifyOtpServlet verifyotpServlet;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private MockHttpSession session;
    private PrintWriter writer;
    
    
    @Before 
    public void setUp() throws Exception {
        verifyotpServlet = new AdminVerifyOtpServlet();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        session = new MockHttpSession();
        writer = new PrintWriter(System.out);
        
        request.setSession(session);
    }
    
    @Test 
    public void testDoPostInvalidOtp () throws ServletException, IOException, Exception
    {
        String otpEntered= "123456";
        String email = "John@gmail.com";
        String StoredOtp = "654321";
        
        request.setParameter("otp_code", StoredOtp);
        
        if(otpEntered == StoredOtp)
        {
            System.out.println("Success");
        }
        else
        {
            System.out.println("Error");
        }
    }
    
    @Test
    public void testDoPostValidOtp() throws ServletException, IOException, Exception
    {
        String otpEntered = "123456";
        String email = "John@gmail.com";
        String StoredOtp = "654321";
        
        request.setParameter("otp_code", StoredOtp);
        
        if(otpEntered == StoredOtp)
        {
            System.out.println("Success");
        }
        else
        {
            System.out.println("Error");
        }
    }
}
