package Admin.Servlets;

import AServices.VehicleConfirmClass;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.RequestDispatcher;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Map;
import DatabaseConnection.*;

public class VehicleManageServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        if (session == null || session.getAttribute("email") == null) {
            response.sendRedirect("/Mega_City/Admin/Admin_Login.html");
            return;
        }

        
        try {
            Connection conn = DatabaseConnection.getConnection();
            if (conn == null) {
                System.out.println("Database connection Failed");
                return;
            }

            String sql = "SELECT * FROM vehicles"; // Fixed table name "vehciles" to "vehicles"
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            Map<String, VehicleConfirmClass> vehicleClassconfirm = new HashMap<>();
            while (rs.next()) {
                VehicleConfirmClass vehicleClass = new VehicleConfirmClass();
                vehicleClass.setId(rs.getString("unique_id"));
                vehicleClass.setVehicleName(rs.getString("vehicle_name"));
                vehicleClass.setYear(rs.getString("vehicle_year"));
                vehicleClass.setBrandName(rs.getString("brand_name"));
                vehicleClass.setCondition(rs.getString("vehicle_condition"));
                vehicleClass.setMilage(rs.getString("mileage"));
                vehicleClass.setColor(rs.getString("color"));
                vehicleClass.setRentPerDay(rs.getString("rent_per_day"));
                vehicleClass.setVehcileCat(rs.getString("vehicle_cat"));
                vehicleClass.setDriverEmail(rs.getString("driver_email"));
                vehicleClass.setImagePath(rs.getString("vehicle_image_path"));
                vehicleClass.setStatus(rs.getString("status"));

                vehicleClassconfirm.put(vehicleClass.getId(),vehicleClass);
            }

            // Debugging to ensure the list is populated
            System.out.println("Vehicle list size: " + vehicleClassconfirm.size());

            // Forward before any output is sent
            request.setAttribute("vehicleClassconfirm", vehicleClassconfirm);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/Admin/VehicleManage.jsp");
            dispatcher.forward(request, response);

        } catch (SQLException ex) {
            ex.printStackTrace();
            // Log or handle the error
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet to manage vehicle data.";
    }
}
