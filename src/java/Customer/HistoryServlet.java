package Customer;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.RequestDispatcher;

// Assuming DatabaseConnection is a separate class handling DB connections


public class HistoryServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        // Get the session or create one if it doesn't exist
        HttpSession session = request.getSession();

        // Retrieve email from session
        String email = (String) session.getAttribute("email");

        // If email is not set in session, get from request parameter and set it in session
        if (email == null) {
            email = request.getParameter("email");
            session.setAttribute("email", email);
        }

        try {
            Connection conn = DatabaseConnection.getConnection();
            if (conn == null) {
                out.println("Database connection failed.");
                return;
            }

            // Retrieve history details
            String sql = "SELECT * FROM vehicle_bookings WHERE customer_email = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            // Store results in an ArrayList
            ArrayList<OrderHistory> historyList = new ArrayList<>();
            while (rs.next()) {
                OrderHistory history = new OrderHistory();
                history.setVehicleName(rs.getString("vehicle_name"));
                history.setBookingDate(rs.getString("booking_date"));
                history.setReturnDate(rs.getString("return_date"));
                history.setBookingTime(rs.getString("booking_date_created"));
                history.setCustomerName(rs.getString("customer_name"));
                history.setTotalFare(rs.getString("total_fare"));
                history.setRentPerDay(rs.getString("rent_per_day"));
                history.setStatus(rs.getString("status"));
                historyList.add(history);
            }

            // Set history list as a request attribute and forward to JSP
            request.setAttribute("historyList", historyList);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/Customer/My_History.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            out.println("Error retrieving history: " + e.getMessage());
        }
    }
}
