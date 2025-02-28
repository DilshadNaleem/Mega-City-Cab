package Driver;

import Driver.Class.FileService;
import Driver.Class.VehicleService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;

public class VehicleSaveServlet extends HttpServlet {

    private VehicleService vehicleService;

    @Override
    public void init() {
        vehicleService = new VehicleService(new FileService()); // Inject FileService here
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve form data
        String vehicleName = request.getParameter("vehicle_name");
        String vehicleYear = request.getParameter("vehicle_year");
        String brandName = request.getParameter("brand_name");
        String condition = request.getParameter("condition");
        String mileage = request.getParameter("mileage");
        String rentPerDay = request.getParameter("rent_per_day");
        String color = request.getParameter("color");
        String vehicleType = request.getParameter("type");
        String vehicleImage = "/vehicle_uploads/" +request.getParameter("vehicle_image"); // Now taking image path as a string

        // Retrieve driver's email from session
        HttpSession session = request.getSession();
        String driverEmail = (String) session.getAttribute("driveremail");
        if (driverEmail == null) {
            response.getWriter().write("Driver email is required.");
            return;
        }

        // Save the vehicle to the database
        try {
            vehicleService.saveVehicle(vehicleName, vehicleYear, brandName, condition, mileage, rentPerDay, vehicleImage, driverEmail, color, vehicleType);
            response.getWriter().write("Vehicle added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().write("Error adding vehicle: " + e.getMessage());
        }
    }
}
