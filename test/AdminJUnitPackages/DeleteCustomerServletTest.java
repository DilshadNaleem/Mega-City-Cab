package AdminJUnitPackages;

import MockHttp.MockHttpServletResponse;
import MockHttp.MockHttpSession;
import MockHttp.MockHttpServletRequest;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.StringWriter;
import java.io.PrintWriter;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import Admin.Servlets.*;

public class DeleteCustomerServletTest {
    private DeleteCustomerServlet deleteServlet;
    private MockHttpSession session;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private StringWriter stringWriter;
    private PrintWriter writer;

    @Before
    public void setUp() throws Exception {
        deleteServlet = new DeleteCustomerServlet();
        session = new MockHttpSession();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();

     
        request.setSession(session);

        
        stringWriter = new StringWriter();
        writer = new PrintWriter(stringWriter);
    }

    @Test
    public void testDoPost_DeleteCustomer_Success() throws ServletException, IOException {
       
        String sessionEmail = "dilshadnaleem13@gmail.com";
        session.setAttribute("email", sessionEmail);

       
        assertEquals("Session email attribute should match", sessionEmail, session.getAttribute("email"));

       
        String customerId = "CUS_02";
        request.setParameter("id", customerId);

        
        assertNotNull("Customer ID parameter should not be null", request.getParameter("id"));

       
        response.setWriter(writer);

        // Call the doPost method of the servlet
        deleteServlet.doPost(request, response);

        // Flush and get the response content
        writer.flush();
        String responseContent = stringWriter.toString().trim();

        // Validate the response content
        assertTrue("Response should contain success alert", responseContent.contains("alert('Customer deleted Successfully!')"));
        assertTrue("Redirection path is incorrect", responseContent.contains("window.location.href = '/Mega_City/Admin/Admin_Dashboard.jsp';"));
    }
}
