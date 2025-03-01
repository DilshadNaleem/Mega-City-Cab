package Driver;

import Driver.Class.TotalEarnings;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import DatabaseConnection.*;

public class TotalEarningsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        ArrayList<TotalEarnings> totalEarnings = new ArrayList<>();

        // Get driver email from session
        String sessionEmail = (String) session.getAttribute("driveremail");

        if (sessionEmail == null) {
            response.sendRedirect("Driver/Login.html");
            return;
        }

        
        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn == null) {
                System.out.println("Database connection is null");
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database Connection Error");
                return;
            }

            // SQL query to fetch completed bookings for the logged-in driver
            String query = "SELECT vb.unique_id, vb.vehicle_name, vb.booking_date, vb.returned_date, " +
                           "vb.customer_name, vb.total_fare, vb.fine " +
                           "FROM vehicle_bookings vb " +
                           "INNER JOIN vehicles v ON vb.vehicle_name = v.vehicle_name " +
                           "WHERE vb.status = 'Completed' AND v.driver_email = ?";

            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, sessionEmail);

                try (ResultSet rs = stmt.executeQuery()) {
                    boolean hasEarnings = false;

                    while (rs.next()) {
                        hasEarnings = true;
                        TotalEarnings earnings = new TotalEarnings();
                        earnings.setUniqueId(rs.getString("unique_id"));
                        earnings.setVehiclename(rs.getString("vehicle_name"));
                        earnings.setBookingdate(rs.getString("booking_date"));
                        earnings.setReturneddate(rs.getString("returned_date"));
                        earnings.setCustomername(rs.getString("customer_name"));
                        earnings.setTotalFare(rs.getString("total_fare"));
                        earnings.setFine(rs.getString("fine"));

                        totalEarnings.add(earnings);
                        System.out.println("Earnings Added: " + earnings.getUniqueId());
                    }

                    if (!hasEarnings) {
                        System.out.println("No bookings found for the driver.");
                    }
                }
            }

            // Set the total earnings data in request scope
            request.setAttribute("totalEarnings", totalEarnings);
            request.getRequestDispatcher("/Driver/TotalEarnings.jsp").forward(request, response);

        } catch (SQLException ex) {
            System.err.println("SQL Exception: " + ex.getMessage());
            ex.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database Error");
        } catch (Exception ex) {
            System.err.println("Exception: " + ex.getMessage());
            ex.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred.");
        }
    }
}
