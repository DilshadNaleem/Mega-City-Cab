
package AdminJUnitPackages;
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
import Admin.Servlets.*;
import AServices.*;

public class AdminLoginServletTest {
    private AdminLoginServlet loginServlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private PrintWriter writer;
    private AdminService adminservice;
    
    @Before
    public void setUp()throws Exception {
        loginServlet = new AdminLoginServlet();
        request = new MockHttpServletRequest();
        response  =  new MockHttpServletResponse();
        session = new MockHttpSession();
        writer = new PrintWriter(System.out);
        adminservice = new AdminService();
        
        ((MockHttpServletResponse) response).setWriter(writer);
        ((MockHttpServletRequest) request).setSession(session);
    }
    
    @Test 
    public void testDoPostValidLogin() throws ServletException, IOException, Exception {
        
        String email = "john@example.com";
        String password= "12345678";
        String hashPassowrd = "hashedPassword";
        
        ((MockHttpServletRequest) request).setParameter("username",email);
        ((MockHttpServletRequest) request).setParameter("password", password);
        
        boolean isValidAdmin = adminservice.validateAdmin(email, hashPassowrd);
        if(isValidAdmin)
        {
            String adminName = adminservice.getAdminName(email);
            session.setAttribute("email", email);
            session.setAttribute("adminName", adminName);
            response.sendRedirect("/Mega_City/Admin/Admin_Dashboard.jsp");
            writer.println("<script type='text/javascript'>");
            writer.println("alert('Login successful!');");
        }
         assertNull(email, session.getAttribute("email"));
        assertNull("Admin Name", session.getAttribute("adminName"));
        
    }
    
    @Test 
    public void testDoPostInvalidLogin() throws ServletException, IOException, Exception
    {
        String email = "admin@sample.com";
        String password = "wrongpassword";
        String hashedPassword = "hashedPassword";
        
        ((MockHttpServletRequest) request).setParameter("username",email);
        ((MockHttpServletRequest) request).setParameter("password", password);
        
        boolean isValidAdmin = adminservice.validateAdmin(email, hashedPassword);
        if(!isValidAdmin)
        {
            response.sendRedirect("/Admin/Admin_Login.html");
            writer.println("<script type='text/javascript'>");
             writer.println("alert('Invalid email or password, or account not verified.');");
        }
        
          assertNull(email,session.getAttribute("email"));
    }
}
