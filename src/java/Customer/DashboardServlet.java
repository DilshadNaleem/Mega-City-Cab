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
        // Set content type for the response
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

            // SQL queries to fetch vehicle categories and images
            String categoryQuery = "SELECT vehicle_cat FROM vehicle_types";
            String imageQuery = "SELECT vehicle_image FROM vehicle_types";

            // Lists to store vehicle categories and images
            List<String> categories = new ArrayList<>();
            List<String> images = new ArrayList<>();

            // Fetch categories
            try (Statement stmt = conn.createStatement(); ResultSet categoryRs = stmt.executeQuery(categoryQuery)) {
                while (categoryRs.next()) {
                    categories.add(categoryRs.getString("vehicle_cat"));
                }
                logger.info("Fetched " + categories.size() + " categories from the database.");
            }

            // Fetch images
            try (Statement stmt = conn.createStatement(); ResultSet imageRs = stmt.executeQuery(imageQuery)) {
                while (imageRs.next()) {
                    images.add(imageRs.getString("vehicle_image"));
                }
                logger.info("Fetched " + images.size() + " images from the database.");
            }

            // Combine categories and images into a list of maps
            List<Map<String, String>> vehicles = new ArrayList<>();
            for (int i = 0; i < Math.min(categories.size(), images.size()); i++) {
                Map<String, String> vehicle = new HashMap<>();
                vehicle.put("vehicleName", categories.get(i));
                vehicle.put("imagePath", images.get(i));
                vehicles.add(vehicle);
            }

            // Log number of vehicles combined
            logger.info("Combined " + vehicles.size() + " vehicles from the database.");

            // Set the vehicle data as a request attribute to pass it to the JSP
            request.setAttribute("vehicles", vehicles);

            // Forward to the JSP for rendering
            request.getRequestDispatcher("/Customer/oldDashboard.jsp").forward(request, response);

        } catch (SQLException e) {
            // Handle database errors
            logger.severe("Database error: " + e.getMessage());
            request.setAttribute("error", "Error: Could not fetch vehicle data.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        } catch (Exception e) {
            // Handle general errors
            logger.severe("Unexpected error: " + e.getMessage());
            request.setAttribute("error", "An unexpected error occurred: " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        } finally {
            // Log the completion of the request handling
            logger.info("DashboardServlet: GET request handling completed.");
        }
    }
}
