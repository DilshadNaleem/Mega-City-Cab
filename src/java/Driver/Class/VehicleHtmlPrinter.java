package Driver.Class;

import java.io.PrintWriter;
import java.sql.*;
import jakarta.servlet.http.HttpSession;

public class VehicleHtmlPrinter {

    public void printVehicleDetails(PrintWriter out, ResultSet rs, String searchQuery, HttpSession session) throws SQLException {
        // Retrieve the driver email from the session
        String driverEmail = (String) session.getAttribute("driveremail");

        // If no driver email is found, show an error or redirect to login
        if (driverEmail == null) {
            out.println("<p>You are not logged in. Please log in first.</p>");
            return;
        }

        // Start the HTML response with Bootstrap CDN for styling
        out.println("<html><head>");
        out.println("<link href='https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css' rel='stylesheet'>");
        out.println("<script src='https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js'></script>");
        out.println("<script src='https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js'></script>");
        out.println("</head><body>");
        
        // Container for the content
        out.println("<div class='container mt-5'>");
        out.println("<h2 class='text-center mb-4'>Vehicle Details</h2>");

        // Add search form with Bootstrap styling (if needed for new searches)
        out.println("<form method='get' action='VehicleRetrieveServlet' class='form-inline mb-4 justify-content-center'>");
        out.println("Search: <input type='text' name='search' class='form-control mr-2' placeholder='Enter vehicle name or brand' value='" + (searchQuery != null ? searchQuery : "") + "' />");
        out.println("<input type='submit' class='btn btn-primary' value='Search' />");
        out.println("</form>");

        // Start the HTML table to display data with Bootstrap styling
        out.println("<table class='table table-bordered table-striped'>");
        out.println("<thead class='thead-dark'><tr>");
        out.println("<th>Vehicle Name</th><th>Year</th><th>Brand</th><th>Condition</th><th>Mileage</th><th>Rent Per Day</th><th>Image</th><th>Vehicle Type</th><th>Action</th>");
        out.println("</tr></thead>");
        out.println("<tbody>");

        // Iterate through the result set and print data in table rows
        while (rs.next()) {
            String vehicleEmail = rs.getString("driver_email");

            // Check if the vehicle is associated with the logged-in driver (only show vehicles of the logged-in driver)
            if (vehicleEmail != null && vehicleEmail.equals(driverEmail)) {
                String vehicleName = rs.getString("vehicle_name");
                String vehicleYear = rs.getString("vehicle_year");
                String brandName = rs.getString("brand_name");
                String condition = rs.getString("vehicle_condition");
                String mileage = rs.getString("mileage");
                String rentPerDay = rs.getString("rent_per_day");
                String imagePath = rs.getString("vehicle_image_path");
                String vehicleType = rs.getString("vehicle_cat");
                String vehicleStatus = rs.getString("status"); // Assuming there's a "status" column in the DB

                // Construct the full image path (ensure the URL is correct)
                String fullImagePath = "http://localhost:8080/Mega_City/" + imagePath;

                out.println("<tr>");
                out.println("<td>" + vehicleName + "</td>");
                out.println("<td>" + vehicleYear + "</td>");
                out.println("<td>" + brandName + "</td>");
                out.println("<td>" + condition + "</td>");
                out.println("<td>" + mileage + "</td>");
                out.println("<td>" + rentPerDay + "</td>");
                
                // Display the image if available
                if (imagePath != null && !imagePath.isEmpty()) {
                    out.println("<td><img src='" + fullImagePath + "' alt='Vehicle Image' class='img-fluid' width='100' height='100'></td>");
                } else {
                    out.println("<td>No image available</td>");
                }
                
                out.println("<td>" + vehicleType + "</td>");
                // Display the vehicle status (or a default message if status is unavailable)
                out.println("<td>" + (vehicleStatus != null ? vehicleStatus : "Status not available") + "</td>");
                out.println("</tr>");
            }
        }

        // Close the table and finish the HTML page
        out.println("</tbody>");
        out.println("</table>");
        out.println("</div>"); // Close container

        out.println("</body></html>");
    }
}
