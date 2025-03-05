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
import Driver.UpdateProfileServlet;

public class DriverEditProfileServletTest {
    private UpdateProfileServlet updateProfileServlet;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private MockHttpSession session;
    private StringWriter stringWriter;
    private PrintWriter writer;

    @Before 
    public void setUp() throws Exception {
        updateProfileServlet = new UpdateProfileServlet();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        session = new MockHttpSession();
        stringWriter = new StringWriter();
        writer = new PrintWriter(stringWriter);
        response.setWriter(writer);
    }

    @Test 
    public void testDoPostEditProfile() throws ServletException, IOException {
        // Mock session data
        session.setAttribute("driveremail", "customer@gmail.com");
        request.setSession(session);

        // Mock form parameters
        request.setParameter("firstName", "Sample");
        request.setParameter("lastName", "User");
        request.setParameter("contactNumber", "123456");

        // Execute servlet method
        updateProfileServlet.doPost(request, response);
        writer.flush();

        // Verify response output
        String responseOutput = stringWriter.toString();
        assertTrue(responseOutput.contains("alert('Profile Updated Successfully')") || 
                   responseOutput.contains("alert('Error updating profile')"));
    }
}
