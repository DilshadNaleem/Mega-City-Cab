package Customer;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import jakarta.servlet.ServletException;  // Jakarta servlet imports
import jakarta.servlet.http.HttpServlet;  // Jakarta servlet imports
import jakarta.servlet.http.HttpServletRequest;  // Jakarta servlet imports
import jakarta.servlet.http.HttpServletResponse;  // Jakarta servlet imports
import java.util.Random;
import jakarta.mail.*;  // Jakarta mail imports
import jakarta.mail.internet.*;  // Jakarta mail imports
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

        // Database connection and insert logic
        try {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish database connection
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // SQL query to insert data into the 'customers' table
            String sql = "INSERT INTO customers (unique_id, first_name, last_name, email, contact_number, password, status, created_at) VALUES (?, ?, ?, ?, ?, ?, 0, NOW())";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, uniqueId);
            stmt.setString(2, firstname);
            stmt.setString(3, lastname);
            stmt.setString(4, email);
            stmt.setString(5, contactNumber);
            stmt.setString(6, password);

            // Execute the insert query
            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                out.println("<h1>Registration Successful! OTP Sent to your email.</h1>");
                sendOTPEmail(email, otp, out);  // Send OTP email to the user
                out.println("<a href='verifyOtp.html'>Go to OTP Verification</a>");
            } else {
                out.println("<h1>Registration Failed. Please try again.</h1>");
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

    // Method to generate a 4-digit OTP
    private String generateOTP() {
        Random rand = new Random();
        return String.format("%04d", rand.nextInt(10000));  // Generates a random 4-digit OTP
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
