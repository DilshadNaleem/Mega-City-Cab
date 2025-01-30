package Driver;

import jakarta.servlet.http.Part;
import java.io.*;
import java.nio.file.*;

public class FileService {

    public String saveFile(Part filePart) throws IOException {
        String fileName = getFileName(filePart);  // Extract the file name
        String uploadDir = "C:\\Users\\HP\\Documents\\NetBeansProjects\\Mega_City\\web\\vehicle_uploads";  // Directory path

        File uploadFolder = new File(uploadDir);
        if (!uploadFolder.exists()) {
            uploadFolder.mkdirs(); // Create folder if doesn't exist
        }

        // Save the file to the specified directory
        String imagePath = "/vehicle_uploads/" + fileName;  // Path for the image
        try (InputStream inputStream = filePart.getInputStream();
             OutputStream outputStream = new FileOutputStream(new File(uploadDir + File.separator + fileName))) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }

        return imagePath;
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
