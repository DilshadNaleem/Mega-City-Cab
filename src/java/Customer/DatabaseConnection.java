package Customer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    
    private static final String DB_URL = "jdbc:mysql://localhost:3306/customer_db"; // Corrected URL syntax
    private static final String DB_USER = "root";  
    private static final String DB_PASSWORD = "";  

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        // Load MySQL JDBC driver
        Class.forName("com.mysql.cj.jdbc.Driver");
        // Return a database connection
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);  // Corrected spelling
    }
}
