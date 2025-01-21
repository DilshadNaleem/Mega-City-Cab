
package Customer;

import java.sql.*;
import java.util.UUID;

public class TokenService {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/mega_city";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    private Connection conn;

    public TokenService() throws SQLException {
        try {
            // Initialize database connection
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Database connection error", e);
        }
    }

    // Method to generate and save token to DB
    public void generateToken(String email) throws SQLException {
        // Generate a unique token
        String token = UUID.randomUUID().toString();

        // SQL to insert the token into the database
        String query = "INSERT INTO password_recovery_tokens (email, token) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            stmt.setString(2, token);
            stmt.executeUpdate();
        }
    }
}
