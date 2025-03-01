package Customer.CService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import DatabaseConnection.*;


public class PaymentDAO {

    // Method to add payment
    public boolean addPayment(PaymentClass payment) {
        String query = "INSERT INTO payments (unique_id, customer_email, total_amount, payment_method, created_at, payment_status, order_id) VALUES (?,?,?,?,NOW(),'0',?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, UniqueID());
            stmt.setString(2, payment.getCustomeremail());
            stmt.setString(3, payment.getTotal());
            stmt.setString(4, payment.getPaymentmethod());
            stmt.setString(5, payment.getOrderId());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.err.println("SQL Exception: " + ex.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println("Exception: " + ex.getMessage());
        }
        return false;
    }

    // Generate unique payment ID
    public String UniqueID() throws Exception {
        String uniqueId = "Payment_01";
        try {
            String lastUniqueId = getLastUniqueId();
            if (lastUniqueId != null) {
                int lastNumber = Integer.parseInt(lastUniqueId.substring(4));
                uniqueId = "Payment_" + String.format("%02d", lastNumber + 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Exception: " + e.getMessage());
        }
        return uniqueId;
    }

    // Fetch the last unique payment ID
    public String getLastUniqueId() throws Exception {
        String query = "SELECT unique_Id FROM payments ORDER BY unique_ID DESC LIMIT 1";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getString("unique_id");
            }
        }
        return null;
    }

    // Method to fetch the payment status for a given order_id
    public String getPaymentStatus(String orderId) throws SQLException {
        String query = "SELECT payment_status FROM payments WHERE order_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, orderId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("status");
                }
            }
        }
        return null;  // If no matching order_id is found
    }

    // Method to fetch payment history by customer email
    public List<PaymentClass> getPaymentHistoryByEmail(String customerEmail) throws SQLException {
        List<PaymentClass> paymentHistory = new ArrayList<>();
        String query = "SELECT order_id, payment_method, total_amount, payment_status FROM payments WHERE customer_email = ? ORDER BY created_at DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, customerEmail);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    PaymentClass payment = new PaymentClass();
                    payment.setOrderId(rs.getString("order_id"));
                    payment.setPaymentmethod(rs.getString("payment_method"));
                    payment.setTotal(rs.getString("total_amount"));
                    payment.setStatus(rs.getString("payment_status"));
                    paymentHistory.add(payment);
                }
            }
        }
        return paymentHistory;
    }
}
