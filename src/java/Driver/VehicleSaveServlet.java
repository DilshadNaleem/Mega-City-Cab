package Driver;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;

@MultipartConfig
public class VehicleSaveServlet extends HttpServlet {

    private VehicleService vehicleService;

    // Constructor or initialization method
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
        String VehicleType = request.getParameter("type");

        // Retrieve image part
        Part filePart = request.getPart("vehicle_image");
        String imagePath = null;

        // Save the file and get the image path
        if (filePart != null && filePart.getSize() > 0) {
            imagePath = vehicleService.saveVehicleImage(filePart);
        }

        // Retrieve driver's email from session
        HttpSession session = request.getSession();
        String driverEmail = (String) session.getAttribute("driveremail");
        if (driverEmail == null) {
            response.getWriter().write("Driver email is required.");
            return;
        }

        // Save the vehicle to the database
        try {
            vehicleService.saveVehicle(vehicleName, vehicleYear, brandName, condition, mileage, rentPerDay, imagePath, driverEmail,color, VehicleType);
            response.getWriter().write("Vehicle added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().write("Error adding vehicle: " + e.getMessage());
        }
    }
}
