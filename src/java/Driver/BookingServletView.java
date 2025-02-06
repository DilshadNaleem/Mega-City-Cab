package Driver;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class BookingServletView extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        ArrayList<BookingView> bookingViewList = new ArrayList<>();

        // Check if driver email is already set in session
        String sessionEmail = (String) session.getAttribute("driveremail");
        if (sessionEmail == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn == null) {
                System.out.println("Database connection is null.");
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database Connection Error");
                return;
            }

            // SQL query to join vehicle_bookings and vehicles
            String query = "SELECT vb.*, v.driver_email FROM vehicle_bookings vb " +
                           "INNER JOIN vehicles v ON vb.vehicle_name = v.vehicle_name " +
                           "WHERE vb.status = 'Completed' AND v.driver_email = ?";
            
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, sessionEmail);
                try (ResultSet rs = stmt.executeQuery()) {

                    boolean hasBookings = false;
                    while (rs.next()) {
                        hasBookings = true;
                        BookingView view = new BookingView();
                        view.setUniqueId(rs.getString("unique_id"));
                        view.setVehicleName(rs.getString("vehicle_name"));
                        view.setBookingDate(rs.getString("booking_date"));
                        view.setReturnDate(rs.getString("return_date"));
                        view.setReturenedDate(rs.getString("returned_date"));
                        view.setCustomerEmail(rs.getString("customer_email"));
                        view.setCustomerName(rs.getString("customer_name"));
                        view.setTotalFare(rs.getDouble("total_fare"));
                        view.setBookedDate(rs.getString("booking_date_created"));
                        view.setFine(rs.getDouble("fine"));
                        view.setRentPerDay(rs.getDouble("rent_per_day"));

                        bookingViewList.add(view);
                        System.out.println("Booking Added: " + view.getUniqueId());
                    }

                    if (!hasBookings) {
                        System.out.println("No bookings found for this driver.");
                    }
                }
            }

            // Set the booking list as an attribute in the request
            request.setAttribute("bookingView", bookingViewList);
            request.getRequestDispatcher("/Driver/Totalbookings.jsp").forward(request, response);

        } catch (SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
            ex.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database Error");
        } catch (Exception ex) {
            System.err.println("Exception: " + ex.getMessage());
            ex.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred.");
        }
    }
}
