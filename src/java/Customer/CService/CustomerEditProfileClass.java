
package Customer.CService;

import java.sql.*;
import java.sql.PreparedStatement;
import DatabaseConnection.*;

public class CustomerEditProfileClass {
  public static boolean updateDriverProfile(String firstName, String lastName, String phoneNumber, String email) {
    String Sql = "UPDATE customers SET first_name = ?, last_name = ?, contact_number = ? WHERE email = ?";

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(Sql)) {

        if (conn == null) {
            return false;
        }

        stmt.setString(1, firstName);
        stmt.setString(2, lastName);
        stmt.setString(3, phoneNumber);
        stmt.setString(4, email);

        return stmt.executeUpdate() > 0;  // Return true if the update was successful
    } catch (SQLException ex) {
        ex.printStackTrace();
        return false;
    }
}

}
