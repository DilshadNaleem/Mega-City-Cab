package Customer;

import Customer.CService.RequestDriverDAO;
import Customer.CService.RequestDriverClass;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.PrintWriter;


public class RequestDriverServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Set content type for the response
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Check if the user is logged in
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("email") == null) {
            out.println("<html><body>");
            out.println("<h3>You need to log in first. Redirecting...</h3>");
            out.println("<script>setTimeout(function() { window.location = '/Mega_City/Customer/Signin.html'; }, 2000);</script>");
            out.println("</body></html>");
            return;
        }

        // Get the logged-in customer's email from the session
        String customerEmail = (String) session.getAttribute("email");

        // The description is fixed as per your requirement
        String description = "Request to Promote as Driver";

        // Create the RequestDriverClass object and set its properties
        RequestDriverClass driverRequest = new RequestDriverClass();
        driverRequest.setCustomeremail(customerEmail);
        driverRequest.setDescription(description);

        // Call the DAO method to add the request
        RequestDriverDAO dao = new RequestDriverDAO();
        boolean isAdded = dao.addRequest(driverRequest);

        // Handle the response based on the result
        if (isAdded) {
            out.println("<html><body>");
            out.println("<h3>Driver request added successfully!</h3>");
            out.println("<a href='/Mega_City/Customer/RequestDriver.jsp'>Go Back</a>");
            out.println("</body></html>");
        } else {
            out.println("<html><body>");
            out.println("<h3>Error while adding the driver request. Please try again later.</h3>");
            out.println("<a href='/Mega_City/Customer/RequestDriver.jsp'>Go Back</a>");
            out.println("</body></html>");
        }
    }
}
