package Customer;

import java.sql.*;

public class DatabaseConnection {
   // Database configuration
    private static final String DB_URL = "jdbc:mysql://localhost:3306/mega_city";  
    private static final String DB_USER = "root";  
    private static final String DB_PASSWORD = "";  

    // Method to get a connection to the database
    public static Connection getConnection() throws SQLException {
        try {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Establish database connection
            return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new SQLException("Database connection error: " + e.getMessage());
        }
    }

    // Method to close the connection
    public static void closeConnection(Connection conn) {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
