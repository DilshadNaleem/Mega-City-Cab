package Driver;

import Driver.Class.BookingDetails;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import DatabaseConnection.*;

public class BookingsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        ArrayList<BookingDetails> bookingList = new ArrayList<>();

        try {
            // Get the driver's email from session
            String sessionEmail = (String) session.getAttribute("driveremail"); // assuming session stores the email with the key "email"
            if (sessionEmail == null) {
                response.sendRedirect("/login.jsp"); // Redirect if session email is not found (not logged in)
                return;
            }

            // Connect to the database
            Connection conn = DatabaseConnection.getConnection();

            // Modify the query to filter by driver_email (from session)
            String query = "SELECT vb.*, v.driver_email FROM vehicle_bookings vb " +
                           "JOIN vehicles v ON vb.vehicle_name = v.vehicle_name " +
                           "WHERE v.driver_email = ?";  // Filter by driver email

            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, sessionEmail); // Set the driver email as a parameter

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                BookingDetails booking = new BookingDetails();
                booking.setUniqueId(rs.getString("unique_id"));
                booking.setVehicleName(rs.getString("vehicle_name"));
                booking.setBookingDate(rs.getString("booking_date"));
                booking.setReturnDate(rs.getString("return_date"));
                booking.setBookingTime(rs.getString("booking_date_created"));
                booking.setCustomerName(rs.getString("customer_name"));
                booking.setTotalFare(rs.getDouble("total_fare"));
                booking.setRentPerDay(rs.getDouble("rent_per_day"));
                booking.setStatus(rs.getString("status"));
                booking.setDriverEmail(rs.getString("driver_email"));

                // Calculate fine if return date has passed
                LocalDate returnDate = LocalDate.parse(rs.getString("return_date"));
                LocalDate currentDate = LocalDate.now();
                
                if (returnDate.isBefore(currentDate)) {
                    long overdueDays = ChronoUnit.DAYS.between(returnDate, currentDate);
                    double fine = overdueDays * 2500; // Fine per day

                    // Update fine in the database
                    String updateQuery = "UPDATE vehicle_bookings SET fine = ? WHERE unique_id = ?";
                    PreparedStatement updatePs = conn.prepareStatement(updateQuery);
                    updatePs.setDouble(1, fine);
                    updatePs.setString(2, rs.getString("unique_id"));
                    updatePs.executeUpdate();
                }

                bookingList.add(booking);
            }

            // Set the booking list in the session and redirect to the JSP page
            session.setAttribute("bookingList", bookingList);
            response.sendRedirect("/Mega_City/Driver/Bookings.jsp");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
