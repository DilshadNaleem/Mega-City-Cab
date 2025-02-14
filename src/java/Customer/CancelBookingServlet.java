package Customer;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CancelBookingServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String bookingId = request.getParameter("bookingId");

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement("UPDATE vehicle_bookings SET status = 'Cancelled' WHERE unique_id = ?")) {
            
            ps.setString(1, bookingId);
            int result = ps.executeUpdate();

            if (result > 0) {
                request.getSession().setAttribute("message", "Booking cancelled successfully.");
            } else {
                request.getSession().setAttribute("message", "Failed to cancel booking.");
            }
        } catch (Exception e) {
            request.getSession().setAttribute("message", "An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
        response.sendRedirect("/Mega_City/Customer/My_History.jsp");
    }
}
