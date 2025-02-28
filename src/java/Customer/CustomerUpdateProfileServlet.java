
package Customer;

import Customer.CService.SessionHelper;
import Customer.CService.CustomerEditProfileClass;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class CustomerUpdateProfileServlet extends HttpServlet {
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String driverEmail = SessionHelper.getLoggedIncustomer(request);
        if(driverEmail == null)
        {
            response.sendRedirect("/Customer/Signin.html");
            return;
        }
        
        String firstName = request.getParameter("firstName");
        String LastName = request.getParameter("lastName");
        String contactNumber = request.getParameter("contactNumber");
         
        
        if(isInvalidInput(firstName,LastName,contactNumber))
        {
            request.setAttribute("errorMessage", "Invalid Input");
            request.getRequestDispatcher("/Customer/Edit_Profile.jsp").forward(request, response);
            return;
        }
        
        boolean UpdateSuccess = CustomerEditProfileClass.updateDriverProfile(firstName,LastName, contactNumber,driverEmail);
        
        if(UpdateSuccess)
        {
            response.sendRedirect("/Mega_City/DashboardServlet");
        } else
        {
            request.setAttribute("errorMessage", "Error Updating Profile");
              request.getRequestDispatcher("/Customer/Edit_Profile.jsp").forward(request, response);
        } 
    }
    
    private boolean isInvalidInput(String firstName, String lastName,String contactNumber) {
        return firstName == null || lastName == null || contactNumber == null|| firstName.trim().isEmpty() || lastName.trim().isEmpty()|| contactNumber.trim().isEmpty();
    }
}