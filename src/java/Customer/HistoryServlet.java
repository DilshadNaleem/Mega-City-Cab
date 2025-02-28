package Customer;

import Customer.CService.OrderHistory;
import Customer.CService.HistoryRepository;
import Customer.CService.HistoryService;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.RequestDispatcher;
import java.util.List;

public class HistoryServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private HistoryService historyService;

    @Override
    public void init() throws ServletException {
        this.historyService = new HistoryService(new HistoryRepository());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
        if (email == null) {
            email = request.getParameter("email");
            session.setAttribute("email", email);
        }

        try {
            List<OrderHistory> historyList = historyService.getHistoryByEmail(email);
            request.setAttribute("historyList", historyList);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/Customer/My_History.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            throw new ServletException("Error retrieving history: " + e.getMessage(), e);
        }
    }
}
