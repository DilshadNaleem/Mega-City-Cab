package Customer.CService;
// Insertion
import java.sql.*;
import DatabaseConnection.*;
public class DatabaseUtility {
  

    public boolean isEmailRegistered(String email) throws SQLException {
        String query = "SELECT email FROM customers WHERE email = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }
    }

    public boolean insertCustomer(Customer customer) throws SQLException {
        String query = "INSERT INTO customers (unique_id, first_name, last_name, email, contact_number, password, status, created_at,nic,image,customer_type) VALUES (?, ?, ?, ?, ?, ?, 0, NOW(),?,'account.png','Regular')";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, customer.getUniqueId());
            stmt.setString(2, customer.getFirstName());
            stmt.setString(3, customer.getLastName());
            stmt.setString(4, customer.getEmail());
            stmt.setString(5, customer.getContactNumber());
            stmt.setString(6, customer.getPassword());
            stmt.setString(7,customer.getNic());
            return stmt.executeUpdate() > 0;
        }
    }

    public String getLastUniqueId() throws SQLException {
        String query = "SELECT unique_id FROM customers ORDER BY unique_id DESC LIMIT 1";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getString("unique_id");
            }
        }
        return null;
    }
}
