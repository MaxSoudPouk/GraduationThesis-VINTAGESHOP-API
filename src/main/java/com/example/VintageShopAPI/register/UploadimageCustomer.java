package com.example.VintageShopAPI.register;

import io.jsonwebtoken.io.IOException;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class UploadimageCustomer {
//	DateTimeFormatter weekFormatter = DateTimeFormatter.ofPattern("WWYYYY");
//	  String formattedWeek = Date.format(weekFormatter);


    public boolean image_csutomer(String UPLOAD_DIRECTORY, String fileName, MultipartFile customer_url_image) throws IOException, java.io.IOException {

//		  System.out.println(" UPLOAD_IMAGE1========: " + UPLOAD_DIRECTORY);

        Path uploadPath = Paths.get(UPLOAD_DIRECTORY);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = customer_url_image.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);


            return true;

        } catch (IOException | java.io.IOException ioe) {
            return false;
            // throw new IOException("Could not save image file: " + fileName, ioe);
        }

    }

}


