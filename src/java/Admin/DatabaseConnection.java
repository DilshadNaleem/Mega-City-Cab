package Admin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/Mega_City";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";
    
    public static Connection getConnection() throws SQLException, ClassNotFoundException {
    
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
    
}
