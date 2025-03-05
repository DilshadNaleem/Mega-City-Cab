package AdminJUnitPackages;

import MockHttp.MockHttpServletResponse;
import MockHttp.MockHttpSession;
import MockHttp.MockHttpServletRequest;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import Admin.Servlets.*;
import AServices.*;
import java.io.StringWriter;

public class DeleteVehicleTypeServletTest {
    private DeleteVehicleTypeServlet vehicleServlet;
    private VehicleRepository vehicleRepository;
    private HttpSession session;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private PrintWriter writer;
    private VehicleUniqueIdGenerator idgenerator;
    
    @Before
    public void setUp() throws ServletException {
        vehicleServlet = new DeleteVehicleTypeServlet();
        vehicleRepository = new VehicleRepository(new FileUploader());
        session = new MockHttpSession();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        idgenerator = new VehicleUniqueIdGenerator();
    }
    
    @Test
    public void testdopostDeleteVehicleType() throws ServletException, IOException, Exception {
        String vehicleId = "VEHTYPE_06";
        ((MockHttpServletRequest) request).setParameter("vehicleId", vehicleId);

        // Capture the response output using StringWriter
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        
        // Set the writer to the mock response
        ((MockHttpServletResponse) response).setWriter(writer);

        // Call the servlet method
        vehicleServlet.doPost(request, response);

        // Ensure all content is written
        writer.flush();

        // Get the response content as a string
        String responseContent = stringWriter.toString().trim();

        // Debugging: Print response content to check what is actually returned
        System.out.println("Response Content: " + responseContent);

        // Validate expected messages
        if (responseContent.contains("alert('Vehicle deleted Successfully!')")) {
            System.out.println("Test Passed: Vehicle was deleted Successfully!");
        } else if (responseContent.contains("alert('Vehicle not found!')")) {
            System.out.println("Test Passed: Vehicle was not found.");
        } else {
            fail("Unexpected response: " + responseContent);
        }
    }
}
