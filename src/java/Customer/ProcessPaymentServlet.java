package Customer;

import Customer.CService.PaymentClass;
import Customer.CService.PaymentDAO;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.RequestDispatcher;
import DatabaseConnection.*;

public class ProcessPaymentServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(ProcessPaymentServlet.class.getName());

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        // Step 1: Retrieve email from session
        String customerEmail = (String) session.getAttribute("email");
        LOGGER.info("Session email retrieved: " + customerEmail);

        if (customerEmail == null) {
            LOGGER.warning("Customer email is null. Redirecting to Signin.");
            response.sendRedirect("Mega_City/Customer/Signin.html");
            return;
        }

        // Step 2: Retrieve parameters from the request
        String totalAmount = request.getParameter("total_amount");
        String paymentMethod = request.getParameter("paymentMethod");
        String orderId = request.getParameter("orderId");

        // Log the retrieved parameters
        LOGGER.info("Total Amount: " + totalAmount);
        LOGGER.info("Payment Method: " + paymentMethod);
        LOGGER.info("Order ID: " + orderId);

        // Validate parameters
        if (totalAmount == null || totalAmount.isEmpty() || paymentMethod == null || paymentMethod.isEmpty() || orderId == null || orderId.isEmpty()) {
            LOGGER.warning("Invalid data. One or more parameters are empty.");
            response.sendRedirect("PaymentHistory.jsp?error=Invalid data");
            return;
        }

        // Step 3: Create payment object
        PaymentDAO paymentDAO = new PaymentDAO();
        PaymentClass payment = new PaymentClass();
        payment.setCustomeremail(customerEmail);
        payment.setTotal(totalAmount);
        payment.setPaymentmethod(paymentMethod);
        payment.setOrderId(orderId);

        // Step 4: Add payment to the database
        boolean success = paymentDAO.addPayment(payment);

        if (success) {
            LOGGER.info("Payment successful.");

            // Step 4.1: Update the payment status to 'Completed' after payment success
            try (Connection conn = DatabaseConnection.getConnection()) {
                String updateQuery = "UPDATE payments SET payment_status = 'Completed' WHERE order_id = ?";
                try (PreparedStatement stmt = conn.prepareStatement(updateQuery)) {
                    stmt.setString(1, orderId);
                    int rowsUpdated = stmt.executeUpdate();

                    if (rowsUpdated > 0) {
                        LOGGER.info("Payment status updated to 'Completed'.");

                        // Fetch payment history after updating
                        List<PaymentClass> historyList = paymentDAO.getPaymentHistoryByEmail(customerEmail);
                        request.setAttribute("historyList", historyList);
                        RequestDispatcher dispatcher = request.getRequestDispatcher("PaymentHistory.jsp");
                        dispatcher.forward(request, response);
                    } else {
                        LOGGER.warning("Failed to update payment status.");
                        response.sendRedirect("PaymentHistory.jsp?error=Payment successful, but status update failed.&orderId=" + orderId);
                    }
                }
            } catch (SQLException e) {
                LOGGER.severe("Error updating payment status: " + e.getMessage());
                response.sendRedirect("PaymentHistory.jsp?error=Error updating payment status.&orderId=" + orderId);
            }
        } else {
            LOGGER.warning("Payment failed.");
            response.sendRedirect("PaymentHistory.jsp?error=Payment failed&orderId=" + orderId);
        }
    }
}
