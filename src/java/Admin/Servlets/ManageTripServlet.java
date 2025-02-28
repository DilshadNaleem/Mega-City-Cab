package Admin.Servlets;

import AServices.ManageTripClass;
import AServices.ManageTripService;
import AServices.SearchCustomerBookings;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

public class ManageTripServlet extends HttpServlet {

    private final ManageTripService tripService = new ManageTripService(); // Service Layer
    private final SearchCustomerBookings searchCustomerBookings = new SearchCustomerBookings(); // Search class

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("email") == null) {
            response.sendRedirect("/Mega_City/Admin/Admin_Login.html");
            return;
        }

        // Fetch the status parameter from the request
        String statusFilter = request.getParameter("status");
        
        // If "all" is selected, pass null for status filter to fetch all trips
        if ("all".equals(statusFilter)) {
            statusFilter = null;
        }

        // Fetch search term from the request
        String searchQuery = request.getParameter("search");
        
        // Fetch search results based on the query
        Map<String, ManageTripClass> manageTrip;
        if (searchQuery != null && !searchQuery.isEmpty()) {
            manageTrip = searchCustomerBookings.searchTrips(searchQuery, statusFilter); // Use search functionality
        } else {
            // Fetch filtered trips based on the status
            manageTrip = tripService.getFilteredTrips(statusFilter, null);
        }

        // Set the trips to the request attribute and forward to JSP
        request.setAttribute("manageTrip", manageTrip);
        RequestDispatcher dis = request.getRequestDispatcher("/Admin/ManageTrip.jsp");
        dis.forward(request, response);
    }
}
