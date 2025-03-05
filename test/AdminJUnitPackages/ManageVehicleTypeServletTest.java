package AdminJUnitPackages;

import MockHttp.MockHttpServletResponse;
import MockHttp.MockHttpSession;
import MockHttp.MockHttpServletRequest;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import Admin.Servlets.*;
import AServices.*;

public class ManageVehicleTypeServletTest {
    private EditVehicleTypeServlet editVehicleServlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private PrintWriter writer;
    private StringWriter stringWriter; // StringWriter to capture the response

    @Before 
    public void setUp() throws Exception {
        editVehicleServlet = new EditVehicleTypeServlet();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        session = new MockHttpSession();
        
        stringWriter = new StringWriter(); // Create StringWriter to capture response
        writer = new PrintWriter(stringWriter); // PrintWriter writes to StringWriter
        
        ((MockHttpServletResponse) response).setWriter(writer);
        ((MockHttpServletRequest) request).setSession(session);
    }

    @Test
public void testDoPostEditVehicleCategory() throws ServletException, IOException {
    try {
        // Define parameters for vehicle update
        String vehicleId = "VEHTYPE_03";
        String vehicleCategory = "dil";
        String description = "23,000 km done";
        String vehicleImage = "vehicle/Screenshot(19)";

        // Set request parameters
        ((MockHttpServletRequest) request).setParameter("vehicleId", vehicleId);
        ((MockHttpServletRequest) request).setParameter("vehicleCategory", vehicleCategory);
        ((MockHttpServletRequest) request).setParameter("vehicleDescription", description);
        ((MockHttpServletRequest) request).setParameter("vehicleImage", vehicleImage);

        // Call the Servlet's doPost method
        editVehicleServlet.doPost(request, response);

        // Capture the response output
        String responseContent = stringWriter.toString().trim(); // Capture from stringWriter

        // Normalize spaces and newlines for both actual and expected output
        String expectedScript = "<script type='text/javascript'>alert('Vehicle type updated successfully!');window.location.href=document.referrer;</script>";
        
        // Normalize both the expected and actual script by removing spaces
        String normalizedResponseContent = responseContent.replaceAll("\\s+", "");
        String normalizedExpectedScript = expectedScript.replaceAll("\\s+", "");

        // Compare the output with the expected script (after normalization)
        assertEquals("Expected script not found in response", normalizedExpectedScript, normalizedResponseContent);
    } catch (Exception ex) {
        ex.printStackTrace();
        fail("Unexpected exception occurred during vehicle update: " + ex.getMessage());
    }
}

}
