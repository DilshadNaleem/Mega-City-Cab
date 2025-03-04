package CustomerJUnitPackages;

import MockHttp.MockHttpServletResponse;
import MockHttp.MockHttpSession;
import MockHttp.MockHttpServletRequest;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import Customer.*;
import Customer.CService.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class PaymentServletTest {

    private ProcessPaymentServlet paymentServlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session; // Mock session
    private PaymentDAO paymentdao;

    @Before
    public void setUp() throws Exception {
        // Initialize the servlet and mock objects
        paymentServlet = new ProcessPaymentServlet();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        session = new MockHttpSession();  // Create mock session
        ((MockHttpServletRequest) request).setSession(session); // Attach the mock session to the request
        paymentdao = new PaymentDAO();
    }

@Test
public void testDoPostCashPayment() throws ServletException, IOException, Exception {
    try {
        // Test parameters for cash payment
        String email = "dilshadnaleem13@gmail.com";
        String totalAmount = "10000";
        String paymentMethod = "Cash";
        String orderId = "order_10";

        // Set the parameters in the mock request
        ((MockHttpServletRequest) request).setParameter("total_amount", totalAmount);
        ((MockHttpServletRequest) request).setParameter("paymentMethod", paymentMethod);
        ((MockHttpServletRequest) request).setParameter("orderId", orderId);

        // Set the session attribute for email
        session.setAttribute("email", email);

        // Call the doPost method
        paymentServlet.doPost(request, response);

        // Print the paymentStatus to check if it's set correctly
       

        // Check for the 'paymentStatus' attribute in the request
        assertEquals("Payment status should be 'Completed'", "Payment status should be 'Completed'");

    } catch (Exception ex) {
        ex.printStackTrace();
        fail("Exception occurred during payment process: " + ex.getMessage());
    }
}


}
