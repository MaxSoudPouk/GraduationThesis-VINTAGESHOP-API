package com.example.VintageShopAPI.displayimage;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@RestController
@CrossOrigin
public class DisPlayImageController {
    private final Set<String> VALID_EXTENSIONS = new HashSet<>(Arrays.asList(".png", ".jpg"));
    @GetMapping(value = "/v1/displayimage")
    public ResponseEntity<?> getImage(@RequestParam String filename) throws IOException {


        if(filename.equals("")){
            return  ResponseEntity
                    .ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body("");
        }
        try {
            Path filePath = Path.of(filename);
            String fileExtension = getFileExtension(filePath);
            if (!VALID_EXTENSIONS.contains(fileExtension)) {
                return ResponseEntity.status(HttpStatus.REQUEST_HEADER_FIELDS_TOO_LARGE).body(new DisplayMessageImage("Invalid file type"));
            }

        String FILE_PATH_ROOT = "H:/";
        FileSystemResource imgFile = new FileSystemResource(FILE_PATH_ROOT + filename);
        byte[] bytes = StreamUtils.copyToByteArray(imgFile.getInputStream());
        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(bytes);
        } catch (NoSuchFileException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new DisplayMessageImage("File not found."));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new DisplayMessageImage("Internal server error"));
        }
    }
    private String getFileExtension(Path filePath) {
        String fileName = filePath.getFileName().toString();
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex == -1) {
            return "";
        }
        return fileName.substring(dotIndex);
    }
}

