package Admin.Servlets;

import AServices.FileUploader;
import AServices.VehicleRepository;
import AServices.VehicleUniqueIdGenerator;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

public class AddVehicleServlet extends HttpServlet {
    private VehicleRepository vehicleService;

    @Override
    public void init() {
        vehicleService = new VehicleRepository(new FileUploader());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String lastUniqueId = vehicleService.getLastUniqueID();
        String uniqueId = VehicleUniqueIdGenerator.generateUniqueId(lastUniqueId);
        String vehicleType = request.getParameter("category");
        String vehicleDescription = request.getParameter("description");
        String image = request.getParameter("image");

        // Ensure values are not null or empty
        if (vehicleType == null || vehicleType.trim().isEmpty()) {
            response.getWriter().write("<script type='text/javascript'>"
                    + "alert('Error: Vehicle category cannot be empty!');"
                    + "window.location.href=document.referrer;"
                    + "</script>");
            return;
        }

        if (image == null || image.trim().isEmpty()) {
            response.getWriter().write("<script type='text/javascript'>"
                    + "alert('Error: Vehicle image is required!');"
                    + "window.location.href=document.referrer;"
                    + "</script>");
            return;
        }

        // Prepend the folder path to the image
        String imagePath = "vehicle_types/" + image;

        try {
            vehicleService.saveVehicle(uniqueId, vehicleType, vehicleDescription, imagePath);
            response.getWriter().write("<script type='text/javascript'>"
                    + "alert('Vehicle added successfully!');"
                    + "window.location.href=document.referrer;"
                    + "</script>");
        } catch (SQLException ex) {
            ex.printStackTrace();
            response.getWriter().write("<script type='text/javascript'>"
                    + "alert('Error adding vehicle: " + ex.getMessage() + "');"
                    + "window.location.href=document.referrer;"
                    + "</script>");
        }
    }
}
