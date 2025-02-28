package AServices;

import java.util.Map;

public class ManageTripService {
    
    private final ManageTripDAO tripDAO = new ManageTripDAO(); // Dependency Injection

    public Map<String, ManageTripClass> getFilteredTrips(String status, String date) {
        return tripDAO.fetchTrips(status, date);
    }
}
