package Customer.CService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import DatabaseConnection.*;

public class vehicleBookingDAO {

    public boolean addBooking(BookVehicleBooking booking) {
        String query = "INSERT INTO vehicle_bookings (vehicle_name, unique_id, booking_date, booking_time, return_date, rent_per_day, customer_name, total_fare, customer_email, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, 'Booked')";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, booking.getVehicleName());
            stmt.setString(2, booking.getUniqueId());
            stmt.setString(3, booking.getBookingDate());
            stmt.setString(4, booking.getBookingTime());
            stmt.setString(5, booking.getReturnDate());
            stmt.setString(6, booking.getRentPerDay());
            stmt.setString(7, booking.getCustomerName());
            stmt.setString(8, booking.getTotalFare());
            stmt.setString(9, booking.getEmail());

            int rowsAffected = stmt.executeUpdate();

            // Check and update customer type if needed
            int tripCount = getBookingCount(booking.getCustomerName());
            if (tripCount >= 5) {
                updateCustomerTypeToLoyalty(booking.getCustomerName());
            }

            return rowsAffected > 0;  // Return true if the booking was successfully added

        } catch (SQLException e) {
            e.printStackTrace();
            return false;  // Return false if there was an error
        }
    }

    public int getBookingCount(String customerName) {
        String query = "SELECT COUNT(*) FROM vehicle_bookings WHERE customer_name = ? AND status = 'Completed'";
        int count = 0;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, customerName);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }

    public void updateCustomerTypeToLoyalty(String customerName) {
        String[] nameParts = customerName.trim().split("\\s+", 2); // Split by spaces, handling extra spaces
        String firstName = nameParts[0].trim();
        String lastName = (nameParts.length > 1) ? nameParts[1].trim() : ""; // Handle cases where there's only one name

        String query = "UPDATE customers SET customer_type = 'Loyalty' WHERE TRIM(LOWER(first_name)) = ? AND TRIM(LOWER(last_name)) = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, firstName.toLowerCase()); // Convert to lowercase for consistency
            stmt.setString(2, lastName.toLowerCase());

            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Customer " + customerName + " is now Loyalty.");
            } else {
                System.out.println("No customer updated. Check first_name and last_name.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
