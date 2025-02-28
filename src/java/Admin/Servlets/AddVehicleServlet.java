package Admin.Servlets;

import AServices.FileUploader;
import AServices.VehicleRepository;
import AServices.VehicleUniqueIdGenerator;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.UUID;
import java.sql.*;

public class AddVehicleServlet extends HttpServlet {
private VehicleRepository vehicleService;

    public void init() 
    {
        vehicleService = new VehicleRepository(new FileUploader());
    }
    

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String lastUniqueId = vehicleService.getLastUniqueID();
        String uniqueId = VehicleUniqueIdGenerator.generateUniqueId(lastUniqueId);
        String vehicleType = request.getParameter("category");
        String vehicleDescription = request.getParameter("description");
        String image = "vehicle_types/" + request.getParameter("image");
        
        try 
        {
           vehicleService.saveVehicle(uniqueId, vehicleType, vehicleDescription, image);
           response.getWriter().write("Vehicle Added");
        }catch (SQLException ex)
        {
            ex.printStackTrace();
            response.getWriter().write("Error addding vehicle" + ex.getMessage());
        }
    }
}
