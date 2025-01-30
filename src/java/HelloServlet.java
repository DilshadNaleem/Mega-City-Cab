import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class HelloServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Set an attribute to pass to the JSP
        request.setAttribute("message", "Hello, World from Me!");
        
        // Forward the request to the JSP
        request.getRequestDispatcher("/hello.jsp").forward(request, response);
    }
}
