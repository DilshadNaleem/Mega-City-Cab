package Customer;

import Customer.CService.PaymentClass;
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
import DatabaseConnection.*;

public class PaymentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

   protected void doGet(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {
    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();

    // Get the session or create one if it doesn't exist
    HttpSession session = request.getSession();

    // Retrieve email from session
    String email = (String) session.getAttribute("email");

    // Print the sessioned email
    out.println("Sessioned Email: " + email);

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

       String sql = "SELECT vb.*, p.* \n" +
"FROM vehicle_bookings vb\n" +
"LEFT JOIN payments p ON vb.unique_id = p.order_id\n" +
"WHERE vb.customer_email = ?;";
PreparedStatement ps = conn.prepareStatement(sql);
ps.setString(1, email);
ResultSet rs = ps.executeQuery();

// Store results in an ArrayList
ArrayList<PaymentClass> historyList = new ArrayList<>();
while (rs.next()) {
    PaymentClass history = new PaymentClass();
    history.setOrderId(rs.getString("unique_id"));
    history.setPaymentId(rs.getString("vehicle_name")); // Fetching the correct payment_id from payments table
    history.setPaymentmethod(rs.getString("payment_method")); // Fetching from payments table
    history.setTotal(rs.getString("total_fare"));
    history.setStatus(rs.getString("payment_status")); // Payment status from the payments table
    history.setCreatedat(rs.getString("booking_date_created"));
    history.setCustomeremail(rs.getString("customer_email"));
    historyList.add(history);
}


        // Set history list as a request attribute and forward to JSP
        request.setAttribute("historyList", historyList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/Customer/Payments.jsp");
        dispatcher.forward(request, response);

    } catch (Exception e) {
        e.printStackTrace();
        out.println("Error retrieving payment history: " + e.getMessage());
    }
   }
}
