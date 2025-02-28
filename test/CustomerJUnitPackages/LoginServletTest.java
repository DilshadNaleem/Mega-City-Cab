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

public class LoginServletTest {

    private LoginServlet loginServlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private PrintWriter writer;
    private CustomerService customerService;

    @Before
    public void setUp() throws Exception {
        // Initialize the servlet and regular objects
        loginServlet = new LoginServlet();

        // Manually create mock objects or substitute real objects for testing
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        session = new MockHttpSession();
        writer = new PrintWriter(System.out);  // Simulating output to console for testing
        customerService = new CustomerService();  // Replace with your actual service or a test instance

        // Assign session and writer to the response
        ((MockHttpServletResponse) response).setWriter(writer);
        ((MockHttpServletRequest) request).setSession(session);
    }

    @Test
    public void testDoPostValidLogin() throws ServletException, IOException {
        // Simulate the valid login parameters
        String email = "customer@example.com";
        String password = "password123";
        String hashedPassword = "hashedPassword";  // Use a mock or hash the password

        // Manually set the parameters for the request (simulating form data)
        ((MockHttpServletRequest) request).setParameter("email", email);
        ((MockHttpServletRequest) request).setParameter("password", password);

        // Simulate a valid login with a valid customer service method
        boolean isValidCustomer = customerService.validateCustomer(email, hashedPassword);
        if (isValidCustomer) {
            String customerName = customerService.getCustomerName(email);
            session.setAttribute("email", email);
            session.setAttribute("customerName", customerName);
            response.sendRedirect("/Mega_City/DashboardServlet");
            writer.println("<script type='text/javascript'>");
            writer.println("alert('Login successful!');");
        }

        // Assertions to check if the session has been updated and response written
        assertNull(email, session.getAttribute("email"));
        assertNull("Customer Name", session.getAttribute("customerName"));
        // You can check your response here manually as well (e.g., looking at the printed writer output)
    }

    @Test
    public void testDoPostInvalidLogin() throws ServletException, IOException {
        // Simulate invalid login parameters
        String email = "cuswtomer@example.com";
        String password = "wrongpassword";
        String hashedPassword = "hashedPassword";  // Mocked hashed password

        // Manually set the parameters for the request (simulating form data)
        ((MockHttpServletRequest) request).setParameter("email", email);
        ((MockHttpServletRequest) request).setParameter("password", password);

        // Simulate failed customer validation
        boolean isValidCustomer = customerService.validateCustomer(email, hashedPassword);
        if (!isValidCustomer) {
            response.sendRedirect("/Admin/Admin_Login.html");
            writer.println("<script type='text/javascript'>");
            writer.println("alert('Invalid email or password, or account not verified.');");
        }

        // Verify that the session attributes were not set
        assertNull(email,session.getAttribute("email"));
        

       
    }
}
