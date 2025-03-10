package Customer;

import Admin.EmailService;
import Customer.CService.vehicleBookingDAO;
import Customer.CService.BookVehicleBooking;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import DatabaseConnection.DatabaseConnection;
import java.io.UnsupportedEncodingException;
import java.lang.System.Logger;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import jakarta.servlet.RequestDispatcher;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.logging.Level;




public class bookVehicleServlet extends HttpServlet {
 private vehicleBookingDAO vehicleBookingDAO;

    // Constructor or setter for dependency injection
    public void setBookingDAO(vehicleBookingDAO bookingDAO) {
        this.vehicleBookingDAO = bookingDAO;
    }
  @Override
public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    // Get form values
    String vehicleName = request.getParameter("vehicleName");
    String bookingDate = request.getParameter("bookingDate");
    String bookingTime = request.getParameter("bookingTime");
    String returnDate = request.getParameter("returndate");
    String rentPerDay = request.getParameter("rentperday");
    double totalFare = Double.parseDouble(request.getParameter("totalPrice"));
    String uniqueId = getUniqueId();
    // Get customer details from session
    HttpSession session = request.getSession();
    String customerName = (String) session.getAttribute("customerName");
    String customerEmail = (String) session.getAttribute("email");

    // Initialize DAO
    vehicleBookingDAO bookingDAO = new vehicleBookingDAO();

    // Fetch first and last name from the database
    String firstName = "";
    String lastName = "";
    String driverEmail = "";
    String contact = "";

    try (Connection conn = DatabaseConnection.getConnection()) {
        // Check for overlapping bookings for the selected vehicle
        String checkBookingQuery = "SELECT booking_date, return_date FROM vehicle_bookings WHERE vehicle_name = ? " +
                "AND ((? BETWEEN booking_date AND return_date) OR (? BETWEEN booking_date AND return_date))" + "AND status = 'Booked'";
        try (PreparedStatement ps = conn.prepareStatement(checkBookingQuery)) {
            ps.setString(1, vehicleName);
            ps.setString(2, bookingDate);  // Check if bookingDate is within the booked period
            ps.setString(3, returnDate);   // Check if returnDate is within the booked period

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // If there's a booking found in the same period, show an alert with the message
                    String bookedUntilDate = rs.getString("return_date");
                    String alertMessage = "The vehicle is already booked until " + bookedUntilDate;
                    response.setContentType("text/html");
                    response.getWriter().println("<script type='text/javascript'>alert('" + alertMessage + "');window.location='/Mega_City/DashboardServlet';</script>");
                    return; // Stop further processing
                }
            }
        }
        
      
        // Fetch customer details
        String customerQuery = "SELECT first_name, last_name FROM customers WHERE CONCAT(first_name, ' ', last_name) = ?";
        try (PreparedStatement ps = conn.prepareStatement(customerQuery)) {
            ps.setString(1, customerName);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    firstName = rs.getString("first_name");
                    lastName = rs.getString("last_name");
                }
            }
        }
        
       
        
        String driverQuery = "SELECT d.email, d.contact_number FROM vehicles v INNER JOIN driver d ON v.driver_email = d.email WHERE v.vehicle_name = ?";

        try (PreparedStatement ps = conn.prepareStatement(driverQuery)) {
            ps.setString(1, vehicleName);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    driverEmail = rs.getString("email");
                    contact = rs.getString("contact_number");
                }
            }
        }

        
    } catch (Exception e) {
        e.printStackTrace();
    }

    // Check if customer qualifies for a loyalty discount
    int tripCount = bookingDAO.getBookingCount(customerName);
    if (tripCount >= 5) {
        totalFare *= 0.90; // Apply 10% discount
    }

    // Create booking record
    // Create booking record using Builder pattern
BookVehicleBooking booking = new BookVehicleBooking.Builder()
    .setVehicleName(vehicleName)
    .setUniqueId(uniqueId)
    .setBookingDate(bookingDate)
    .setBookingTime(bookingTime)
    .setReturnDate(returnDate)
    .setRentPerDay(rentPerDay)
    .setCustomerName(customerName)
    .setTotalFare(String.valueOf(totalFare))
    .setEmail(customerEmail)
    .build();

bookingDAO.addBooking(booking);

    // Update customer type to loyalty if eligible
    if (tripCount >= 5) {
        bookingDAO.updateCustomerTypeToLoyalty(customerName);
    }

    // Send email confirmation using session email
    if (customerEmail != null && !customerEmail.isEmpty()) {
        sendEmail(customerEmail, firstName, lastName, vehicleName, bookingDate, bookingTime, returnDate, totalFare, driverEmail, contact);
    } else {
        System.out.println("No email found in session. Email not sent.");
    }
    
    request.setAttribute("vehicleName", vehicleName);
    request.setAttribute("bookingDate", bookingDate);
    request.setAttribute("bookingTime", bookingTime);
    request.setAttribute("returnDate", returnDate);
    request.setAttribute("totalFare", totalFare);
    request.setAttribute("driverEmail", driverEmail);
    request.setAttribute("contact", contact);

    // Forward to confirmation JSP
    RequestDispatcher dispatcher = request.getRequestDispatcher("/Customer/confirmation.jsp");
    dispatcher.forward(request, response);

    response.setContentType("text/html");
response.getWriter().println("<script type='text/javascript'>alert('Booking successful!'); window.location='/Mega_City/DashboardServlet';</script>");

}


    public void sendEmail(String toEmail, String firstName, String lastName, String vehicle, String date, String time, String returnDate, double totalFare, String driver, String contactNumber) throws UnsupportedEncodingException {
    String fromEmail = "hypermarket403@gmail.com"; // Your email
    String emailPassword = "prny fbme inmd nkzb"; // Your email password (use App Password if using Gmail)
    String host = "smtp.gmail.com";

    // Set up email properties
    Properties properties = new Properties();
    properties.put("mail.smtp.auth", "true");
    properties.put("mail.smtp.starttls.enable", "true");
    properties.put("mail.smtp.host", host);
    properties.put("mail.smtp.port", "587");

    // Create a session with authentication
    Session session = Session.getInstance(properties, new Authenticator() {
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(fromEmail, emailPassword);
        }
    });

    try {
        // Compose the message
        Message message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(fromEmail, "Mega City"));
        } catch (AddressException e) {
            throw new MessagingException("Invalid sender or display name", e);
        } catch (UnsupportedEncodingException ex) {
            java.util.logging.Logger.getLogger(bookVehicleServlet.class.getName()).log(java.util.logging.Level.SEVERE, "Unsupported encoding exception", ex);
            throw new MessagingException("Error setting the sender's email address", ex);
        }
        
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
        message.setSubject("Booking Confirmation - " + vehicle);

        // Construct the email content with Mega City branding
        String emailContent = "<html>"
                + "<body style='font-family: Arial, sans-serif; margin: 0; padding: 0;'>"
                + "<table width='100%' cellpadding='0' cellspacing='0' style='background-color: #1e2a3a; padding: 20px; color: #ffffff;'>"
                + "<tr>"
                + "<td style='text-align: center; font-size: 24px; font-weight: bold;'>"
                + "<img src='https://example.com/logo.png' alt='Mega City' width='200'/>"
                + "</td>"
                + "</tr>"
                + "</table>"

                + "<table width='100%' cellpadding='20' cellspacing='0' style='padding: 40px 20px;'>"
                + "<tr>"
                + "<td style='background-color: #ffffff; border-radius: 10px; padding: 20px;'>"
                + "<h3>Dear " + firstName + " " + lastName + ",</h3>"
                + "<p>Your vehicle booking has been successfully confirmed. Here are the details:</p>"

                + "<table width='100%' cellpadding='5' cellspacing='0' style='border-collapse: collapse;'>"
                + "<tr><td><b>Vehicle:</b></td><td>" + vehicle + "</td></tr>"
                + "<tr><td><b>Pickup Date:</b></td><td>" + date + "</td></tr>"
                + "<tr><td><b>Pickup Time:</b></td><td>" + time + "</td></tr>"
                + "<tr><td><b>Return Date:</b></td><td>" + returnDate + "</td></tr>"
                + "<tr><td><b>Total Fare:</b></td><td>Rs. " + totalFare + "</td></tr>"
                + "<tr><td><b>Driver Email:</b></td><td>" + driver + "</td></tr>"
                + "<tr><td><b>Driver Contact:</b></td><td>" + contactNumber + "</td></tr>"
                + "</table>"

                + "<p>If you have any questions, feel free to contact us. Thank you for choosing Mega City!</p>"

                + "</td>"
                + "</tr>"
                + "</table>"

                + "<table width='100%' cellpadding='0' cellspacing='0' style='background-color: #1e2a3a; padding: 10px 0; color: #ffffff;'>"
                + "<tr>"
                + "<td style='text-align: center;'>"
                + "<p>&copy; 2025 Mega City | All Rights Reserved</p>"
                + "</td>"
                + "</tr>"
                + "</table>"
                + "</body>"
                + "</html>";

        // Set the content of the email
        message.setContent(emailContent, "text/html");

        // Send email
        Transport.send(message);
        System.out.println("Email sent successfully to " + toEmail);
        
       
    } catch (MessagingException e) {
        e.printStackTrace();
    }
    
}

    
public String getUniqueId() {
    String uniqueId = "order_01"; // Default for first entry

    try (Connection conn = DatabaseConnection.getConnection()) {
        String query = "SELECT unique_id FROM vehicle_bookings ORDER BY booking_id DESC LIMIT 1";
        try (PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                String lastId = rs.getString("unique_id"); // Get last unique_id
                if (lastId != null && lastId.startsWith("order_")) {
                    String numberPart = lastId.substring(6); // Extract numeric part
                    int num = Integer.parseInt(numberPart); // Convert to int
                    num++; // Increment by 1
                    uniqueId = String.format("order_%02d", num); // Format as "order_XX"
                }
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    return uniqueId;
}


}
