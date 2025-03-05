package DriverJUnitPackages;

import Admin.EmailService;
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
import Driver.DriverRegisterServlet;
import DatabaseConnection.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DriverRegisterServletTest {
    private DriverRegisterServlet registerServlet;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private MockHttpSession session;
    private StringWriter stringWriter;
    private PrintWriter writer;

    @Before
    public void setUp() throws Exception {
        registerServlet = new DriverRegisterServlet();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        session = new MockHttpSession();
        stringWriter = new StringWriter();
        writer = new PrintWriter(stringWriter);
        response.setWriter(writer);
    }

    @Test
    public void testDoPostRegister() throws ServletException, IOException, SQLException {
        // Mock form input
        request.setParameter("firstname", "Abdul");
        request.setParameter("lastname", "Kalam");
        request.setParameter("email", "customer@gmail.com");
        request.setParameter("contact_number", "0725958832");
        request.setParameter("nic", "123456789");
        request.setParameter("password", "12345678");
        request.setSession(session);

        // Execute servlet method
        registerServlet.doPost(request, response);
        writer.flush();

        
        assertNotNull(session.getAttribute("otp"));
        assertEquals("customer@gmail.com", session.getAttribute("driveremail"));
        
       
        String responseOutput = stringWriter.toString();
        assertTrue(responseOutput.contains("alert('Registration Successful! OTP has been sent to your email.')"));

       
        Connection conn = DatabaseConnection.getConnection();
        String query = "SELECT * FROM driver WHERE email = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, "customer@gmail.com");
        ResultSet rs = stmt.executeQuery();
        assertTrue(rs.next()); 
        
       
        
    }
}
