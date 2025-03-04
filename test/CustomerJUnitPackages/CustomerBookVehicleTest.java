package CustomerJUnitPackages;

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
import Customer.*;
import Customer.CService.*;

public class CustomerBookVehicleTest {
    private bookVehicleServlet bookvehicleServlet;
    private vehicleBookingDAO vehiclebookingdao;
    private BookVehicleBooking bookvehiclebuilder;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private MockHttpSession session;
    private PrintWriter writer;

    @Before
    public void setUp() throws Exception {
        bookvehicleServlet = new bookVehicleServlet();
        vehiclebookingdao = new vehicleBookingDAO();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        session = new MockHttpSession();
        writer = new PrintWriter(System.out);
        
        // Set the session explicitly in request
        request.setSession(session);
    }

    @Test
    public void testDoPostRegisterVehicle() throws ServletException, IOException, Exception {
        try {
            String vehicleName = "Toyota";
            String bookingdate = "2025-03-03";
            String returnDate = "2025-03-05";
            String bookingTime = "19:38:00";
            String totalFare = "200000.00";
            String rentPerday = "1000";
            String email = "dilshadnaleem13@gmail.com";
            String customername = "John Sample";
            String uniqueId = bookvehicleServlet.getUniqueId();

            // Set parameters in request
            request.setParameter("vehicleName", vehicleName);
            request.setParameter("bookingDate", bookingdate);
            request.setParameter("bookingTime", bookingTime);
            request.setParameter("returndate", returnDate);
            request.setParameter("rentperday", rentPerday);
            request.setParameter("totalPrice", totalFare);

            // Ensure session exists
            HttpSession session = request.getSession();
            if (session == null) {
                throw new NullPointerException("Session is null. Unable to proceed.");
            }

            session.setAttribute("email", email);
            session.setAttribute("customerName", customername);

            // Build booking object
            BookVehicleBooking booking = new BookVehicleBooking.Builder()
                    .setVehicleName(vehicleName)
                    .setUniqueId(uniqueId)
                    .setBookingDate(bookingdate)
                    .setBookingTime(bookingTime)
                    .setReturnDate(returnDate)
                    .setRentPerDay(rentPerday)
                    .setCustomerName(customername)
                    .setTotalFare(totalFare)
                    .setEmail(email)
                    .build();

            // Add booking to DAO
            boolean result = vehiclebookingdao.addBooking(booking);
            assertTrue("Booking should be added successfully", result);
        } catch (NullPointerException e) {
            System.err.println("Error: " + e.getMessage());
            fail("Test failed due to null session.");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Test encountered an unexpected error.");
        }
    }
    
    
    @Test
    public void testDoPostSendEmail() throws ServletException, IOException, Exception
    {
        try{
            
        
        String toEmail = "dilshadnaleem13@gmail.com";
        String firstName = "John";
        String lastName = "Abraham";
        String vehicle = "Lamborghini";
        String date = "2025-03-03";
        String time = "19:38:00";
        String returnDate = "2025-03-05";
        Double totalFare= Double.parseDouble("20000.00");
        String driver = "Sample";
        String driverContact = "0725958832";
        
         bookvehicleServlet.sendEmail(toEmail, firstName, lastName, vehicle, date, time, returnDate, totalFare, driver,driverContact);
    }
    
    catch (Exception ex)
    {
        ex.printStackTrace();
        fail("Exception email not send: " + ex.getMessage());
    }
   }
    
    @Test 
    public void testDoPostRegisteredDate() throws ServletException, IOException, Exception
    {
    try{
         String vehicleName = "Toyota";
            String bookingdate = "2025-03-03";
            String returnDate = "2025-03-05";
            String bookingTime = "19:38:00";
            String totalFare = "200000.00";
            String rentPerday = "1000";
            String email = "dilshadnaleem13@gmail.com";
            String customername = "John Sample";
            String uniqueId = bookvehicleServlet.getUniqueId();

            // Set parameters in request
            request.setParameter("vehicleName", vehicleName);
            request.setParameter("bookingDate", bookingdate);
            request.setParameter("bookingTime", bookingTime);
            request.setParameter("returndate", returnDate);
            request.setParameter("rentperday", rentPerday);
            request.setParameter("totalPrice", totalFare);

            // Ensure session exists
            HttpSession session = request.getSession();
            if (session == null) {
                throw new NullPointerException("Session is null. Unable to proceed.");
            }

            session.setAttribute("email", email);
            session.setAttribute("customerName", customername);

            // Build booking object
            BookVehicleBooking booking = new BookVehicleBooking.Builder()
                    .setVehicleName(vehicleName)
                    .setUniqueId(uniqueId)
                    .setBookingDate(bookingdate)
                    .setBookingTime(bookingTime)
                    .setReturnDate(returnDate)
                    .setRentPerDay(rentPerday)
                    .setCustomerName(customername)
                    .setTotalFare(totalFare)
                    .setEmail(email)
                    .build();

            // Add booking to DAO
            boolean result = vehiclebookingdao.addBooking(booking);
            assertTrue("Vehicle Booked Already", result);
        } catch (NullPointerException e) {
            System.err.println("Error: " + e.getMessage());
            fail("Test failed due to null session.");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Test encountered an unexpected error.");
        }
    }
}

