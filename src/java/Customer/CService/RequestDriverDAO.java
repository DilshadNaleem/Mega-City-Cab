package Customer.CService;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import DatabaseConnection.*;


public class RequestDriverDAO {

    private static final Logger logger = Logger.getLogger(RequestDriverDAO.class.getName());

    public boolean addRequest(RequestDriverClass driver) {
        String query = "INSERT INTO request_driver (unique_id, customer_email, description, created_at, status) VALUES (?,?,?,NOW(),0)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, UniqueId());
            stmt.setString(2, driver.getCustomeremail());
            stmt.setString(3, driver.getDescription());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Error while adding driver request: " + ex.getMessage(), ex);
            return false;
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Unexpected error: " + ex.getMessage(), ex);
            return false;
        }
    }
    
    public String UniqueId() throws Exception
    {
        String uniqueId = "RDriver_01";
        try
        {
            String lastUniqueId = getLastUniqueId();
            if(lastUniqueId != null)
            {
                int lastNumber = Integer.parseInt(lastUniqueId.substring(4));
                uniqueId = "RDriver_" + String.format("%02d", lastNumber + 1);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return uniqueId;
    }
    
    public String getLastUniqueId() throws Exception
    {
       String query = "SELECT unique_id FROM request_driver ORDER BY unique_id DESC LIMIT 1" ;
       
       try(Connection conn = DatabaseConnection.getConnection();
               PreparedStatement stmt = conn.prepareStatement(query);
               ResultSet rs = stmt.executeQuery())
       {
           if(rs.next())
           {
               return rs.getString("unique_id");
           }
       }
       return null;
    }
}
