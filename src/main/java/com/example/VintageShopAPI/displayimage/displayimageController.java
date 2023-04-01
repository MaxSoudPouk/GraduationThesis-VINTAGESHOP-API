package com.example.VintageShopAPI.displayimage;

import com.example.VintageShopAPI.db.DatabaseConnectionPool;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@RestController
@CrossOrigin
public class displayimageController {

    public static HttpServletRequest request;
    DatabaseConnectionPool dbConnectionPool;

    BufferedImage bufferedImage;

    private void setRequest(HttpServletRequest request) {
        displayimageController.request = request;

    }

    @RequestMapping
            (value = "/v1/displayimage", method = {
                    RequestMethod.GET,
                    RequestMethod.POST},
                    produces = MediaType.IMAGE_PNG_VALUE)
    public BufferedImage BufferedImage(
            @RequestParam(defaultValue = "") String filename
    ) {

        if (filename.equals("")) {

            return null;
        }

        try {
            String FILE_PATH_ROOT = "H:/";
            bufferedImage = ImageIO.read(new File(FILE_PATH_ROOT + filename).getAbsoluteFile());
            return bufferedImage;
        } catch (IOException e) {
            return null;
        }


    }

}

