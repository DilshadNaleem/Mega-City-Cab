package Customer.CService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
//
public class CustomerHelper {

    public static boolean isAccountVerified(Connection conn, String email) throws Exception {
        String query = "SELECT status FROM customers WHERE email = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("status") == 1;
            }
        }
        return false;
    }

    public static boolean updateAccountStatus(Connection conn, String email) throws Exception {
        String updateQuery = "UPDATE customers SET status = 1 WHERE email = ?";
        try (PreparedStatement stmt = conn.prepareStatement(updateQuery)) {
            stmt.setString(1, email);
            return stmt.executeUpdate() > 0;
        }
    }

    public static boolean isEmailRegistered(Connection conn, String email) throws Exception {
        String query = "SELECT email FROM customers WHERE email = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }
    }
}
