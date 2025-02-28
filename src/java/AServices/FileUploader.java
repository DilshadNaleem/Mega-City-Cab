package AServices;

import jakarta.servlet.http.Part;
import java.io.*;
import java.nio.file.*;

public class FileUploader {

    public String saveFile(Part filePart) throws IOException {
        // Get the filename from the Part object
        String fileName = getFileName(filePart);
        
        if (fileName == null || fileName.isEmpty()) {
            throw new IOException("No file uploaded");
        }
        
        // Define the upload directory (server-side directory where the file will be saved)
        String uploadDir = "C:\\Users\\HP\\Documents\\NetBeansProjects\\Mega_City\\web\\vehicle_types";
        
        // Ensure the directory exists, create if necessary
        File uploadFolder = new File(uploadDir);
        if (!uploadFolder.exists()) {
            if (!uploadFolder.mkdirs()) {
                throw new IOException("Failed to create upload directory");
            }
        }

        // Construct the path where the image will be saved
        String filePath = uploadDir + File.separator + fileName;
        
        // Debug: Print out the full file path to verify
        System.out.println("Saving file to: " + filePath);

        // Save the file to the specified directory
        try (InputStream inputStream = filePart.getInputStream();
             OutputStream outputStream = new FileOutputStream(new File(filePath))) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            System.out.println("File saved successfully!");
        } catch (IOException e) {
            // Log the error for better debugging
            System.err.println("Error while saving the file: " + e.getMessage());
            throw e;
        }

        // Return the relative path where the image has been saved
        return "/vehicle_types/" + fileName;
    }

    private String getFileName(Part part) {
        String contentDisposition = part.getHeader("content-disposition");
        for (String cd : contentDisposition.split(";")) {
            if (cd.trim().startsWith("filename")) {
                return cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }
}
