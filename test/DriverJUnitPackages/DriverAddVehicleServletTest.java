
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
import Driver.VehicleSaveServlet;
import Driver.Class.VehicleService;
import Driver.Class.FileService;

public class DriverAddVehicleServletTest {
    private VehicleSaveServlet vehicleServlet;
    private VehicleService vehicleService;
    private MockHttpSession session;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private PrintWriter writer;
    private FileService fileservice;
    
    
    @Before 
    public void setUp() throws Exception
    {
        vehicleServlet = new VehicleSaveServlet();
        session = new MockHttpSession();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        vehicleService = new VehicleService(fileservice);
        writer = new PrintWriter(System.out);
        
         session.setAttribute("driveremail", "customer@gmail.com");
    }
    
    @Test 
    public void testdoPostAddVehicle() throws ServletException, IOException, Exception
    {
        String vehicleName = "sample";
        String vehicleYear = "2019";
        String brandName = "BMW";
        String condition = "Low Mileage";
        String mileage = "10000";
        String rentPerday = "1250";
        String color = "BLUE";
        String type = "Luxury";
        String VehicleImage = "Screenshot10";
        String driverEmail = "customer@gmail.com";
        
        driverEmail = (String) session.getAttribute("driveremail");
        if(driverEmail.isEmpty())
        {
            System.out.println("Session email is blank");
        }
        
        try 
        {
             vehicleService.saveVehicle(vehicleName, vehicleYear, brandName, condition, mileage, rentPerday, mileage, driverEmail, color, type);
            PrintWriter out = response.getWriter();
             out.println("<script type='text/javascript'>");
            out.println("alert('Vehicle added successfully!');");
            out.println("window.location.href = 'VehiclePage.jsp';");  // Redirect to the desired page after success
            out.println("</script>");
        }
        catch (Exception ex)
        {
           ex.printStackTrace();
            PrintWriter out = response.getWriter();
            out.println("<script type='text/javascript'>");
            out.println("alert('Error adding vehicle: " + ex.getMessage() + "');");
            out.println("window.location.href = 'VehiclePage.jsp';");  // Redirect to the desired page after error
            out.println("</script>");
        }
    }
}
