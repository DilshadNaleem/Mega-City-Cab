package Driver;

import Driver.Class.VehicleSearchService;
import Driver.Class.VehicleHtmlPrinter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;
import DatabaseConnection.*;

public class VehicleRetrieveServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = null;
        ResultSet rs = null;

        // Get the search query from the request (if provided)
        String searchQuery = request.getParameter("search");

        // Set content type for HTML response
        response.setContentType("text/html");

        try {
            // Create instances of VehicleSearchService and VehicleHtmlPrinter
            VehicleSearchService searchService = new VehicleSearchService();
            VehicleHtmlPrinter htmlPrinter = new VehicleHtmlPrinter();

            // Get the database connection
            conn = DatabaseConnection.getConnection();
            if (conn == null) {
                response.getWriter().write("Database connection failed.");
                return;
            }

            // Get vehicles based on the search query
            rs = searchService.searchVehicles(conn, searchQuery);

            // Prepare the PrintWriter to send the response
            PrintWriter out = response.getWriter();

            // Retrieve the session from the request
            HttpSession session = request.getSession(); 

            // Print the vehicle details using VehicleHtmlPrinter, passing the session
            htmlPrinter.printVehicleDetails(out, rs, searchQuery, session);

        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().write("Error retrieving vehicle details: " + e.getMessage());
        } finally {
            // Close database resources
            try {
                if (rs != null) rs.close();
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
}
