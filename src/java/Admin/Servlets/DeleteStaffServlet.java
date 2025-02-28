package Admin.Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.*;
import DatabaseConnection.*;

public class DeleteStaffServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("email") == null) {
            response.sendRedirect("/Mega_City/Admin/Admin_Login.html");
            return;
        }

        String sql = "DELETE FROM driver WHERE unique_id = ?";
        String message = "";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String staff = request.getParameter("id");
            stmt.setString(1, staff);

            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated > 0) {
                message = "Staff deleted successfully!";
            } else {
                message = "Failed to delete staff. ID not found.";
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            message = "SQL Exception: " + ex.getMessage();
        } catch (Exception ex) {
            ex.printStackTrace();
            message = "Exception: " + ex.getMessage();
        }

        // Send the alert message and reload the page
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<script type='text/javascript'>");
        out.println("alert('" + message + "');");
        out.println("window.location.href=document.referrer;");
        out.println("</script>");
    }
}
