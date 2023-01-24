package com.example.VintageShopAPI.addproduct;

import com.example.VintageShopAPI.db.DatabaseConnectionPool;
import org.json.JSONException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AddProductController extends Thread{
    public static HttpServletRequest request;
    DatabaseConnectionPool dbConnectionPool;

    public AddProductController() {
    }

    private static String getClientIp() {
        String remoteAddr = "";
        try {
            if (request != null) {
                try {
                    remoteAddr = request.getHeader("X-FORWARDED-FOR");
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                if (remoteAddr == null || "".equals(remoteAddr)) {
                    remoteAddr = request.getRemoteAddr();
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return remoteAddr;
    }


    private void setRequest(HttpServletRequest request) {
        AddProductController.request = request;

    }

    @PostMapping("/v1/addProduct")
    public ResponseEntity<Map<String, Object>> AddProduct(
            @RequestParam(defaultValue = "") String idproduct,
            @RequestParam(defaultValue = "") String productName,
            @RequestParam(defaultValue = "") String description,
            @RequestParam(defaultValue = "") String selectedOption
//            @RequestParam(defaultValue = "") String image1,
//            @RequestParam(defaultValue = "") String image2,
//            @RequestParam(defaultValue = "") String image3,
//            @RequestParam(defaultValue = "") String image4,
//            @RequestParam(defaultValue = "") String image5
    )throws NoSuchAlgorithmException, JSONException, java.io.IOException{

        Map<String, Object> response = new HashMap<>();


        AddProductModel AddProductmodel = new AddProductModel();



        return ResponseEntity.ok(response);
    }
}
