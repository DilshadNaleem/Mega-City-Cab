package Customer;

import Customer.CService.VehicleServiceImpl;
import Customer.CService.VehicleService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.util.*;

public class ProductServlet extends HttpServlet {

    private VehicleService vehicleService;

    @Override
    public void init() throws ServletException {
        vehicleService = new VehicleServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String vehicleType = request.getParameter("vehicleType");
        String searchQuery = request.getParameter("searchQuery");  // Get search query

        List<Map<String, String>> vehicleDetails = new ArrayList<>();

        try {
            if (vehicleType != null && !vehicleType.isEmpty()) {
                // If vehicleType is provided, perform search within this type
                if (searchQuery != null && !searchQuery.isEmpty()) {
                    vehicleDetails = vehicleService.searchVehiclesInType(vehicleType, searchQuery);
                } else {
                    vehicleDetails = vehicleService.getVehiclesByType(vehicleType);
                }
            } else {
                // If no vehicleType provided, perform search across all vehicle types
                if (searchQuery != null && !searchQuery.isEmpty()) {
                    vehicleDetails = vehicleService.searchVehicles(searchQuery);
                } else {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write("Error: No search criteria provided.");
                    return;
                }
            }

            // Set the vehicle details as a request attribute
            request.setAttribute("vehicleDetails", vehicleDetails);

            // Forward the request to the JSP for rendering
            RequestDispatcher dispatcher = request.getRequestDispatcher("/Customer/Vehicle_Category.jsp");
            dispatcher.forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Database error occurred.");
        }
    }
}
