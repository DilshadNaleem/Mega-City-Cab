package AServices;

import java.sql.*;
import java.util.Map;
import java.util.HashMap;
import DatabaseConnection.*;

public class SearchCustomerBookings {

    // Method to search trips based on status and search query
    public Map<String, ManageTripClass> searchTrips(String searchQuery, String statusFilter) {
        Map<String, ManageTripClass> searchResults = new HashMap<>();
        
        // SQL query to fetch the trips with the status filter (and search query if provided)
        String sql = "SELECT vb.*, v.driver_email FROM vehicle_bookings vb " +
                     "JOIN vehicles v ON vb.vehicle_name = v.vehicle_name " +
                     "WHERE (? IS NULL OR vb.status = ?) " +
                     "AND (? IS NULL OR vb.unique_id LIKE ? OR vb.customer_name LIKE ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            // Set the parameters for the SQL query
            stmt.setString(1, statusFilter);
            stmt.setString(2, statusFilter != null ? statusFilter : "%");
            stmt.setString(3, searchQuery != null ? searchQuery : "%");
            stmt.setString(4, "%" + searchQuery + "%");
            stmt.setString(5, "%" + searchQuery + "%");

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                // Populate the trip object with data from the result set
                ManageTripClass trip = new ManageTripClass();
                trip.setUniqueId(rs.getString("unique_id"));
                trip.setVehiclename(rs.getString("vehicle_name"));
                trip.setBookingdate(rs.getString("booking_date"));
                trip.setReturndate(rs.getString("return_date"));
                trip.setReturneddate(rs.getString("returned_date"));
                trip.setBookingtime(rs.getString("booking_time"));
                trip.setCustomername(rs.getString("customer_name"));
                trip.setCustomeremail(rs.getString("customer_email"));
                trip.setTotalfare(rs.getDouble("total_fare"));
                trip.setCreatedaat(rs.getString("booking_date_created"));
                trip.setRentperday(rs.getString("rent_per_day"));
                trip.setFine(rs.getString("fine"));
                trip.setStatus(rs.getString("status"));
                trip.setDriverEmail(rs.getString("driver_email"));

                // Add the trip to the results map, keyed by the unique_id
                searchResults.put(trip.getUniqueId(), trip);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return searchResults;
    }
}
