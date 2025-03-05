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

public class RequestDriverServletTest {
    private RequestDriverServlet driverServlet;
    private RequestDriverClass driverClass;
    private RequestDriverDAO driverDao;
    private MockHttpSession session;
    private MockHttpServletResponse response;
    private MockHttpServletRequest request;
    
    @Before 
    public void setUp() throws Exception {
        driverServlet = new RequestDriverServlet();
        driverClass = new RequestDriverClass();
        driverDao = new RequestDriverDAO();
        session = new MockHttpSession();
        response = new MockHttpServletResponse();
        request = new MockHttpServletRequest();
        
        // Set session email before calling addRequest
        session.setAttribute("email", "dilshadnaleem13@gmail.com");
        request.setSession(session);
    }
    
    @Test
    public void testDoPostRequestDriver() throws ServletException, IOException, Exception {
        String uniqueId = driverDao.UniqueId();
        String customerEmail = (String) session.getAttribute("email"); // Get session email
        String description = "Promote as Driver";
        
        request.setParameter("unique_id", uniqueId);
        request.setParameter("description", description);
        
        // Set values inside driverClass before passing to DAO
        driverClass.setUniqueId(uniqueId);
        driverClass.setCustomeremail(customerEmail);
        driverClass.setDescription(description);
        
        boolean isAdded = driverDao.addRequest(driverClass);

        // Assertion for test validation
        assertTrue("Driver request should be added successfully", isAdded);
    }
}
