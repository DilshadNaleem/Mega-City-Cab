
package CustomerJUnitPackages;

import MockHttp.MockHttpServletResponse;
import MockHttp.MockHttpSession;
import MockHttp.MockHttpServletRequest;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import Customer.*;
import Customer.CService.*;

public class EditProfileTest {
    private CustomerEditProfileClass editprofile;
    private HttpServletResponse response;
    private HttpServletRequest request;
    private HttpSession session;
    private PrintWriter writer;
    private CustomerUpdateProfileServlet profileupdateServlet;
    
    @Before 
    public void setUp() throws Exception 
    {
        editprofile = new CustomerEditProfileClass();
        response = new MockHttpServletResponse();
        request = new MockHttpServletRequest();
        session = new MockHttpSession();
        writer = new PrintWriter(System.out);
        profileupdateServlet = new CustomerUpdateProfileServlet();
    }
    
    @Test
    public void testDoPostEditProfile() throws ServletException, IOException, Exception
    {
     try 
      {
        String firstname = "John";
        String lastname = "Abraham";
        String email = "hypermarket403@gmail.com";
        String contactnumber = "1234567891";
        
        ((MockHttpServletRequest) request).setParameter("firstName", firstname);
        ((MockHttpServletRequest) request).setParameter("lastName", lastname);
        ((MockHttpServletRequest) request).setParameter("contactNumber", contactnumber);
        
        String sessionemail = (String) session.getAttribute("email");
        
        boolean UpdateSuccess = editprofile.updateDriverProfile(firstname, lastname, contactnumber, email);
        if(UpdateSuccess)
        {
            response.sendRedirect("/Mega_City/DashboardServlet");
        } 
         else
        {
            request.setAttribute("errorMessage", "Error updating Profile");
            request.getRequestDispatcher("/Customer/EditProfile.jsp").forward(request, response);
        }
    } catch(Exception ex)
    {
        ex.printStackTrace();
        System.out.println("Error: " + ex.getMessage());
    }
}
}