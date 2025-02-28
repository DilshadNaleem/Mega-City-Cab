package Customer.CService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import DatabaseConnection.*;

public class HistoryRepository {

    public List<OrderHistory> fetchHistoryByEmail(String email) throws Exception {
        List<OrderHistory> historyList = new ArrayList<>();

        String sql = "SELECT * FROM vehicle_bookings WHERE customer_email = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    OrderHistory history = new OrderHistory();
                    history.setUniqueId(rs.getString("unique_id"));
                    history.setVehicleName(rs.getString("vehicle_name"));
                    history.setBookingDate(rs.getString("booking_date"));
                    history.setReturnDate(rs.getString("return_date"));
                    history.setBookingTime(rs.getString("booking_date_created"));
                    history.setCustomerName(rs.getString("customer_name"));
                    history.setTotalFare(rs.getString("total_fare"));
                    history.setRentPerDay(rs.getString("rent_per_day"));
                    history.setStatus(rs.getString("status"));
                    historyList.add(history);
                }
            }
        }
        return historyList;
    }
}
