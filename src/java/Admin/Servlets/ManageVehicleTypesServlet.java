package Admin.Servlets;

import AServices.ManageVehicleTypeClass;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.util.ArrayList;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.*;
import DatabaseConnection.*;

public class ManageVehicleTypesServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("email") == null) {
            response.sendRedirect("/Admin/Admin_Login.html");
            return;
        }

        try {
            Connection conn = DatabaseConnection.getConnection();
            if (conn == null) {
                System.out.println("Database Connection failed");
                return;
            }

            String sql = "SELECT * FROM vehicle_types";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet ts = stmt.executeQuery();

            ArrayList<ManageVehicleTypeClass> vehicleList = new ArrayList<>();
            while (ts.next()) {
                ManageVehicleTypeClass vtype = new ManageVehicleTypeClass();
                vtype.setId(ts.getString("unique_id"));
                vtype.setVehicleCategory(ts.getString("vehicle_cat"));
                vtype.setVehicleDescription(ts.getString("vehicle_desc"));
                vtype.setVehicleImage(ts.getString("vehicle_image"));

                vehicleList.add(vtype);
            }

            System.out.println("Vehicle list size: " + vehicleList.size());

            request.setAttribute("vehicleList", vehicleList);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/Admin/Manage_Vehicle_Types.jsp");
            dispatcher.forward(request, response);

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("SQL Exception: " + ex.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Exception: " + ex.getMessage());
        }
    }
}
