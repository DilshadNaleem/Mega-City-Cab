package Customer;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.logging.*;

public class DashboardServlet extends HttpServlet {

    // Logger for the servlet
    private static final Logger logger = Logger.getLogger(DashboardServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Set content type for the response (it will still be HTML in the JSP)
        response.setContentType("text/html");

        // Start logging
        logger.info("DashboardServlet: Handling GET request");

        // Connect to the database
        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn != null) {
                logger.info("Database connection established successfully.");
            } else {
                logger.warning("Failed to establish database connection.");
                request.setAttribute("error", "Error: Could not connect to the database.");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
                return;
            }

            // SQL query to fetch vehicle data
            String query = "SELECT vehicle_cat, vehicle_image FROM vehicle_types";
            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {

                // List to store vehicle data
                List<Map<String, String>> vehicles = new ArrayList<>();

                // Loop through the result set and add data to the list
                while (rs.next()) {
                    Map<String, String> vehicle = new HashMap<>();
                    vehicle.put("vehicleName", rs.getString("vehicle_cat"));
                    vehicle.put("imagePath", rs.getString("vehicle_image"));
                    vehicles.add(vehicle);
                }

                // Log number of vehicles fetched
                logger.info("Fetched " + vehicles.size() + " vehicles from the database.");

                // Set the vehicle data as a request attribute to pass it to the JSP
                request.setAttribute("vehicles", vehicles);

                // Forward to the JSP for rendering
                request.getRequestDispatcher("/Customer/oldDashboard.jsp").forward(request, response);

            } catch (SQLException e) {
                // SQL exception handling
                logger.severe("SQL Error: " + e.getMessage());
                request.setAttribute("error", "Error fetching vehicle data: " + e.getMessage());
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            // Handle database connection errors
            logger.severe("Database connection error: " + e.getMessage());
            request.setAttribute("error", "Error: Could not connect to the database.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        } catch (Exception e) {
            // General exception handling
            logger.severe("Unexpected error: " + e.getMessage());
            request.setAttribute("error", "An unexpected error occurred: " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        } finally {
            // Log the completion of the request handling
            logger.info("DashboardServlet: GET request handling completed.");
        }
    }
}
