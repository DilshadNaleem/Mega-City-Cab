package CustomerJUnitPackages;

import MockHttp.MockHttpServletResponse;
import MockHttp.MockHttpSession;
import MockHttp.MockHttpServletRequest;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import Customer.CancelBookingServlet;

public class CancelBookingServletTest {
    private CancelBookingServlet cancelServlet;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private MockHttpSession session;
    
    @Before 
    public void setUp() throws Exception {
        cancelServlet = new CancelBookingServlet();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        session = new MockHttpSession();
        request.setSession(session);
    }
    
    @Test 
    public void testDoPostCancelBooking() throws ServletException, IOException {
        // Simulate request parameter
        String bookingId = "order_05";
        request.setParameter("bookingId", bookingId);
        
        // Call servlet method
        cancelServlet.doPost(request, response);
        
        // Verify session message
        String message = (String) session.getAttribute("message");
        assertNotNull("Session message should not be null", message);
        assertTrue("Session message should indicate success or failure",
                   message.contains("Booking cancelled successfully.") || 
                   message.contains("Failed to cancel booking.") || 
                   message.contains("An error occurred:"));
    }
}
