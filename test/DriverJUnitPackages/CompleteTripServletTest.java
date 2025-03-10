package DriverJUnitPackages;

import MockHttp.MockHttpServletRequest;
import MockHttp.MockHttpServletResponse;
import MockHttp.MockHttpSession;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import Driver.CompleteTripServlet;

public class CompleteTripServletTest {
    private CompleteTripServlet tripServlet;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private MockHttpSession session;
    
    @Before
    public void setUp() throws Exception {
        tripServlet = new CompleteTripServlet();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        session = new MockHttpSession();
        
        request.setSession(session);
        session.setAttribute("driveremail", "dilshadnaleem13@gmail.com");
    }
    
   @Test
public void testDoPost_ValidBookingId() throws ServletException, IOException {
    
    String bookingId = "order_07";
    request.setParameter("bookingId", bookingId);
    
    StringWriter stringWriter = new StringWriter();
    PrintWriter writer = new PrintWriter(stringWriter);
    response.setWriter(writer);
    
    tripServlet.doPost(request, response);
    writer.flush();
    
    String responseOutput = stringWriter.toString();
    
    assertTrue(responseOutput.contains("alert('Trip Completed. The user has been notified via email.');"));
    
    assertTrue(responseOutput.contains("window.location='/Mega_City/Driver/Bookings.jsp';"));
}

  
    @Test
    public void testDoPost_NoSession() throws ServletException, IOException {
        request.getSession().invalidate(); // Simulate no session
        
        tripServlet.doPost(request, response);
        
        assertTrue(response.getRedirectedUrl().equals("/Mega_City/Driver/Login.html"));
    }
}
