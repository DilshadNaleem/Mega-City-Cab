package Customer.CService;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface VehicleService {
    List<Map<String, String>> getVehiclesByType(String vehicleType) throws SQLException;
    List<Map<String, String>> searchVehiclesInType(String vehicleType, String searchQuery) throws SQLException;
    List<Map<String, String>> searchVehicles(String searchQuery) throws SQLException;  // Added general search
}
