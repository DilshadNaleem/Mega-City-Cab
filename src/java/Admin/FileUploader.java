package Admin;

import jakarta.servlet.http.Part;
import java.io.File;
import java.io.IOException;

public class FileUploader {

    private final String uploadDirectory;

    public FileUploader(String uploadDirectory) {
        this.uploadDirectory = uploadDirectory;
    }

    public String uploadFile(Part filePart) throws IOException {
        if (filePart == null) {
            throw new IOException("File part is missing.");
        }

        String fileName = extractFileName(filePart);
        if (fileName == null || fileName.isEmpty()) {
            throw new IOException("Invalid file name.");
        }

        fileName = fileName.replaceAll("\\s+", "_");
        File uploadDir = new File(uploadDirectory);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        String savePath = uploadDirectory + fileName;
        filePart.write(savePath);

        return "vehicle_types/" + fileName;  // Relative path for database
    }

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
