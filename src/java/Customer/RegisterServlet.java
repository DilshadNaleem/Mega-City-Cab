package Customer;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import jakarta.servlet.ServletException;  
import jakarta.servlet.http.HttpServlet;  
import jakarta.servlet.http.HttpServletRequest;  
import jakarta.servlet.http.HttpServletResponse;  
import jakarta.servlet.http.HttpSession;  
import java.util.Random;
import jakarta.mail.*;  
import jakarta.mail.internet.*;  
import java.security.MessageDigest;
import java.util.Properties;

public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Database configuration
    private static final String DB_URL = "jdbc:mysql://localhost:3306/mega_city";  
    private static final String DB_USER = "root";  
    private static final String DB_PASSWORD = "";  

    // Email configuration
    private static final String FROM_EMAIL = "hypermarket403@gmail.com";
    private static final String EMAIL_PASSWORD = "prny fbme inmd nkzb";  // Use App Password for Gmail

    // Main POST method to handle user registration
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Retrieve form data
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String email = request.getParameter("email");
        String contactNumber = request.getParameter("contact_number");
        String password = request.getParameter("password");

        // Generate a unique ID for the customer
        String uniqueId = generateUniqueId();

        // Generate an OTP for email verification
        String otp = generateOTP();

        // Create customer object
        Customer customer = new Customer(uniqueId, firstname, lastname, email, contactNumber, password, otp);

        // Database connection and insert logic
        try {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish database connection
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            String checkEmailquery = "SELECT email FROM customers WHERE email = ? ";
            PreparedStatement checkStmt = conn.prepareStatement(checkEmailquery);
            checkStmt.setString(1, email);
            ResultSet rs = checkStmt.executeQuery();
            
            if (rs.next()) {
                out.println("<script type='text/javascript'>");
                out.println("alert('Email already registered. Please use a different email.');");
                out.println("window.location.href = '/Mega_City/Customer/Signin.html';");  // Redirect to registration page
                out.println("</script>");
            } else {
                // SQL query to insert data into the 'customers' table
                String sql = "INSERT INTO customers (unique_id, first_name, last_name, email, contact_number, password, status, created_at) VALUES (?, ?, ?, ?, ?, ?, 0, NOW())";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, customer.getUniqueId());
                stmt.setString(2, customer.getFirstName());
                stmt.setString(3, customer.getLastName());
                stmt.setString(4, customer.getEmail());
                stmt.setString(5, customer.getContactNumber());
                stmt.setString(6, hashPassword(customer.getPassword()));  // Hash the password before storing

                // Execute the insert query
                int rowsInserted = stmt.executeUpdate();
                if (rowsInserted > 0) {
                    // Create session and store email and OTP (without saving to DB)
                    HttpSession session = request.getSession();
                    session.setAttribute("email", customer.getEmail());  // Store email in session
                    session.setAttribute("otp", customer.getOtp());      // Store OTP in session

                    // Send OTP email to the user
                    sendOTPEmail(customer.getEmail(), customer.getOtp(), out);  

                    // Redirect to the OTP verification page
                    out.println("<script type='text/javascript'>");
                    out.println("alert('Registration Successful! OTP has been sent to your email.');");
                    out.println("window.location.href = './Customer/verification.html';");  // Redirect to verification.html
                    out.println("</script>");
                } else {
                    out.println("<script type='text/javascript'>");
                    out.println("alert('Registration Failed. Please try again.');");
                    out.println("window.location.href = '/Mega_City/Customer/Signin.html';");  // Redirect to registration page
                    out.println("</script>");
                }
            }
            // Close the database connection
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<h1>Error: Unable to process the request.</h1>");
            out.println("<p>" + e.getMessage() + "</p>");
        }
    }

    // Method to generate Unique ID for the customer (e.g., CUS_01, CUS_02, etc.)
    private String generateUniqueId() {
        String uniqueId = "CUS_01";  // Default ID for the first customer
        try {
            // Establish database connection
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // Query to get the latest unique_id (highest number)
            String query = "SELECT unique_id FROM customers ORDER BY unique_id DESC LIMIT 1";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            // If there's at least one customer, extract and increment the numeric part of the unique_id
            if (rs.next()) {
                String lastUniqueId = rs.getString("unique_id");
                int lastNumber = Integer.parseInt(lastUniqueId.substring(4));  // Extract numeric part
                uniqueId = "CUS_" + String.format("%02d", lastNumber + 1);  // Increment and format as CUS_XX
            }

            // Close the connection
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return uniqueId;
    }

    // Method to generate a 6-digit OTP
    private String generateOTP() {
        Random rand = new Random();
        return String.format("%06d", rand.nextInt(1000000));  // Generates a random 6-digit OTP
    }

    // Method to hash the password using SHA-256
    private String hashPassword(String password) {
        try {
            // Get the instance of SHA-256 algorithm
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = digest.digest(password.getBytes("UTF-8"));

            // Convert byte array into hexadecimal format
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashedBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Method to send OTP via email
    private void sendOTPEmail(String toEmail, String otp, PrintWriter out) {
        try {
            // Set up SMTP properties for Gmail
            Properties properties = new Properties();
            properties.put("mail.smtp.host", "smtp.gmail.com");
            properties.put("mail.smtp.port", "587");  // TLS port
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls.enable", "true");

            // Create mail session with authentication
            Session session = Session.getInstance(properties, new jakarta.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(FROM_EMAIL, EMAIL_PASSWORD);  // Authentication
                }
            });

            // Prepare the email message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM_EMAIL));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Your OTP for Registration");
            message.setText("Your OTP is: " + otp);

            // Send the email
            Transport.send(message);
            System.out.println("OTP sent successfully.");

        } catch (MessagingException e) {
            // Handle messaging exception
            e.printStackTrace();
            out.println("<h1>Error sending OTP email</h1>");
            out.println("<p>" + e.getMessage() + "</p>");
        }
    }
}
