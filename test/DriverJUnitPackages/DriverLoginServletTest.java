package DriverJUnitPackages;

import MockHttp.MockHttpServletRequest;
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
import Driver.DriverLoginServlet;

public class DriverLoginServletTest {
    private DriverLoginServlet loginServlet;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private MockHttpSession session;
    private StringWriter stringWriter;
    private PrintWriter writer;

    @Before 
    public void setUp() throws Exception {
        loginServlet = new DriverLoginServlet();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        session = new MockHttpSession();
        stringWriter = new StringWriter();
        writer = new PrintWriter(stringWriter);
        response.setWriter(writer);
    }

    @Test
    public void testDoPostValidateLogin() throws ServletException, IOException {
        // Mock form input
        request.setParameter("username", "customer@gmail.com");
        request.setParameter("password", "12345678");
        request.setSession(session);

        // Execute servlet method
        loginServlet.doPost(request, response);
        writer.flush();

        // Verify session attributes
        assertNotNull(session.getAttribute("driveremail"));
        assertNotNull(session.getAttribute("driverName"));
        
        // Verify response contains success alert
        String responseOutput = stringWriter.toString();
        assertTrue(responseOutput.contains("alert('Login successful!');"));
    }
}
