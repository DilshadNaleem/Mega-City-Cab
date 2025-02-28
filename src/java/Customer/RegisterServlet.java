package Customer;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import Customer.CService.*;
// Register 
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String email = request.getParameter("email");
        String contactNumber = request.getParameter("contact_number");
        String password = request.getParameter("password");
        String nic = request.getParameter("nic");
        
        DatabaseUtility dbUtility = new DatabaseUtility();
        UniqueIdGenerator idGenerator = new UniqueIdGenerator();
        OtpGenerator otpGenerator = new OtpGenerator();
        PasswordHasher hasher = new PasswordHasher();
        EmailService emailService = new EmailService();

        try {
            if (dbUtility.isEmailRegistered(email)) {
                out.println("<script>alert('Email already registered!');window.location.href='/Mega_City/Customer/Signin.html';</script>");
            } else {
                String uniqueId = idGenerator.generateUniqueId(dbUtility);
                String otp = otpGenerator.generateOTP();
                String hashedPassword = hasher.hashPassword(password);

                Customer customer = new Customer(uniqueId, firstname, lastname, email, contactNumber, hashedPassword, otp,nic);
                if (dbUtility.insertCustomer(customer)) {
                    HttpSession session = request.getSession();
                    session.setAttribute("email", customer.getEmail());
                    session.setAttribute("otp", customer.getOtp());
                    emailService.sendOTP(email, otp);
                    out.println("<script>alert('Registration successful! Check your email for OTP.');window.location.href='./Customer/verification.html';</script>");
                } else {
                    out.println("<script>alert('Registration failed!');window.location.href='/Mega_City/Customer/Signin.html';</script>");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<h1>Error: " + e.getMessage() + "</h1>");
        }
    }
}
