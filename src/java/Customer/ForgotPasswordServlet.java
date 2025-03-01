package Customer;

import Customer.CService.ForgotPasswordEmail;
import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.mail.MessagingException;
import DatabaseConnection.*;
//

public class ForgotPasswordServlet extends HttpServlet {

    private Connection conn;

    @Override
    public void init() throws ServletException {
        try {
            conn = DatabaseConnection.getConnection(); // Assuming DatabaseConnection is correctly implemented
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException("Database connection error", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        String email = request.getParameter("email");

        HttpSession session = request.getSession(); // Create or retrieve the session
        session.setAttribute("email", email); // Save the email to the session

        try (PrintWriter out = response.getWriter()) {
            String query = "SELECT * FROM customers WHERE email = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, email);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    // Send reset password email using the ForgotPasswordEmail class
                    ForgotPasswordEmail emailService = new ForgotPasswordEmail();
                    try {
                        emailService.sendResetPasswordEmail(email);
                        response.sendRedirect("/Mega_City/Customer/Reset_Password.html");
                    } catch (MessagingException e) {
                        e.printStackTrace();
                        out.println("<h2>Error sending email: " + e.getMessage() + "</h2>");
                    }
                } else {
                    out.println("<h2>Email not found.</h2>");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("<h2>Database error: " + e.getMessage() + "</h2>");
        }
    }

    @Override
    public void destroy() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
