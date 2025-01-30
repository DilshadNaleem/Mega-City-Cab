package Admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

@MultipartConfig // Add this annotation to enable file uploads
public class AddVehicleServlet extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // Handle form fields
            String category = request.getParameter("category");
            String description = request.getParameter("description");

            // Debugging logs
            System.out.println("Category: " + category);
            System.out.println("Description: " + description);

            // File upload directory
            String uploadPath = "C:\\Users\\HP\\Documents\\NetBeansProjects\\Mega_City\\web\\vehicle_types\\";

            // Ensure upload directory exists
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir(); // If directory doesn't exist, create it
            }

            // Process file part
            Part filePart = request.getPart("image");
            if (filePart == null) {
                throw new ServletException("File part is missing. Check the form enctype and 'name' attribute.");
            }

            System.out.println("File part received.");

            // Extract file name
            String fileName = extractFileName(filePart);
            if (fileName == null || fileName.isEmpty()) {
                throw new ServletException("File name is invalid.");
            }

            System.out.println("Extracted file name: " + fileName);

            // Define the save path
            String savePath = uploadPath + fileName;
            filePart.write(savePath); // Write the file to disk

            System.out.println("File saved at: " + savePath);

            // Relative path to store in the database (excluding the local file system path)
            String imagePath = "vehicle_types/" + fileName;

            // Save data to the database
            Connection conn = DatabaseConnection.getConnection();
            if (conn == null) {
                throw new ServletException("Database connection is null.");
            }

            String sql = "INSERT INTO vehicle_types (vehicle_cat, vehicle_desc, vehicle_image, created_at) VALUES (?, ?, ?, NOW())";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, category);
            stmt.setString(2, description);
            stmt.setString(3, imagePath);
            stmt.executeUpdate();

            System.out.println("Data saved to database.");

            // Respond to the client
            response.getWriter().println("Vehicle added successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Error processing request: " + e.getMessage(), e);
        }
    }

    // Utility method to extract the file name from the Part header
    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        if (contentDisp != null) {
            for (String content : contentDisp.split(";")) {
                if (content.trim().startsWith("filename")) {
                    return content.substring(content.indexOf("=") + 2, content.length() - 1);
                }
            }
        }
        return null;
    }
}
