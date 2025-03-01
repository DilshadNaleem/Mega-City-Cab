package Admin.Servlets;

import AServices.StaffManageService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import DatabaseConnection.*;


public class ManageStaffServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Session check: If admin is not logged in, redirect to login page
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("email") == null) {
            response.sendRedirect("/Mega_City/Admin/Admin_Login.html");
            return;
        }

        // Get search query from request parameters (if any)
        String searchQuery = request.getParameter("searchQuery");
        if (searchQuery == null) {
            searchQuery = ""; // Default to an empty string if no search query is provided
        }

        try {
            // Establish database connection
            Connection conn = DatabaseConnection.getConnection();
            if (conn == null) {
                System.out.println("Database connection failed!");
                return;
            }

            // SQL Query: Fetch all drivers (including those without vehicles)
            String sql = "SELECT d.*, " +
                         "       COALESCE(v.vehicle_name, 'No vehicle added yet') AS vehicle_name, " +
                         "       COALESCE(SUM(vb.total_fare), 0) AS total_fare_completed " +
                         "FROM driver d " +
                         "LEFT JOIN vehicles v ON d.email = v.driver_email " + 
                         "LEFT JOIN vehicle_bookings vb ON v.vehicle_name = vb.vehicle_name AND vb.status = 'Completed' " + 
                         "WHERE d.first_name LIKE ? OR d.last_name LIKE ? OR d.email LIKE ? " + 
                         "GROUP BY d.unique_id, d.first_name, d.last_name, d.email, v.vehicle_name;";

            PreparedStatement stmt = conn.prepareStatement(sql);
            String searchPattern = "%" + searchQuery + "%";
            stmt.setString(1, searchPattern);
            stmt.setString(2, searchPattern);
            stmt.setString(3, searchPattern);

            ResultSet rs = stmt.executeQuery();

            // Store data in list
            List<StaffManageService> staffList = new ArrayList<>();
            while (rs.next()) {
                StaffManageService staff = new StaffManageService();
                staff.setUniqueId(rs.getString("unique_id"));
                staff.setFirstName(rs.getString("first_name"));
                staff.setLastName(rs.getString("last_name"));
                staff.setEmail(rs.getString("email"));
                staff.setNic(rs.getString("nic"));
                staff.setContact(rs.getString("contact_number"));
                staff.setCreatedat(rs.getString("created_at"));
                staff.setStatus(rs.getString("status"));
                staff.setVehicleName(rs.getString("vehicle_name"));
                staff.setTotalEarnings(rs.getDouble("total_fare_completed"));

                staffList.add(staff);
            }

            // Pass data to JSP
            request.setAttribute("staffList", staffList);
            request.setAttribute("searchQuery", searchQuery);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/Admin/Manage_Staff.jsp");
            dispatcher.forward(request, response);

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.err.println("SQL Exception: " + ex.getMessage());
        }
    }
}
