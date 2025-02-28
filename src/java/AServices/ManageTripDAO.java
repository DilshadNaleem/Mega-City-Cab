package AServices;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import DatabaseConnection.*;

public class ManageTripDAO {

    public Map<String, ManageTripClass> fetchTrips(String status, String date) {
        Map<String, ManageTripClass> manageTrip = new HashMap<>();
        
        // Modify the SQL query to remove the date filter condition from the WHERE clause
        String sql = "SELECT vb.*, v.driver_email FROM vehicle_bookings vb " +
                     "JOIN vehicles v ON vb.vehicle_name = v.vehicle_name " +
                     "WHERE (? IS NULL OR vb.status = ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, status);
            stmt.setString(2, status);

            // Execute the query with only the status filter (date condition is removed)
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
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

                manageTrip.put(trip.getUniqueId(), trip);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return manageTrip;
    }
}
