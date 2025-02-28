
package Customer.CService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


public class SessionHelper {
    
    public static String getLoggedIncustomer(HttpServletRequest request)
    {
        HttpSession session = request.getSession(false);
        if(session == null || session.getAttribute("email") == null)
        {
            return null;
        }
        return(String) session.getAttribute("email");
    }
}
