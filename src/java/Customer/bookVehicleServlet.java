package Customer;

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
import DatabaseConnection.*;

public class bookVehicleServlet extends HttpServlet {

  @Override
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
    String driverName = "";
    String driverContact = "";

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

        // Fetch driver details for the selected vehicle
        String driverQuery = "SELECT driver_name, driver_email FROM vehicles WHERE vehicle_name = ?";
        try (PreparedStatement ps = conn.prepareStatement(driverQuery)) {
            ps.setString(1, vehicleName);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    driverName = rs.getString("driver_name");
                    driverContact = rs.getString("driver_email");
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
        sendEmail(customerEmail, firstName, lastName, vehicleName, bookingDate, bookingTime, returnDate, totalFare, driverName, driverContact);
    } else {
        System.out.println("No email found in session. Email not sent.");
    }

    // Redirect to confirmation page
    response.sendRedirect("confirmationPage.jsp");
}


    private void sendEmail(String toEmail, String firstName, String lastName, String vehicle, String date, String time, String returnDate, double totalFare, String driver, String driverContact) {
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
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Booking Confirmation - " + vehicle);

            String emailContent = "<h3>Dear " + firstName + " " + lastName + ",</h3>"
                    + "<p>Your vehicle booking has been confirmed.</p>"
                    + "<p><b>Vehicle:</b> " + vehicle + "</p>"
                    + "<p><b>Pickup Date:</b> " + date + "</p>"
                    + "<p><b>Pickup Time:</b> " + time + "</p>"
                    + "<p><b>Return Date:</b> " + returnDate + "</p>"
                    + "<p><b>Total Fare:</b> Rs. " + totalFare + "</p>"
                    + "<p>Thank you for choosing our service!</p>";

            message.setContent(emailContent, "text/html");

            // Send email
            Transport.send(message);
            System.out.println("Email sent successfully to " + toEmail);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    
private String getUniqueId() {
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
