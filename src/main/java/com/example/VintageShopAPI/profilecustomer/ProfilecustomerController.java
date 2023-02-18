package com.example.VintageShopAPI.profilecustomer;

import com.example.VintageShopAPI.addproduct.AddProductController;
import com.example.VintageShopAPI.db.Config;
import com.example.VintageShopAPI.db.DatabaseConnectionPool;
import org.json.JSONException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.*;
import java.util.*;

@RestController
@CrossOrigin
public class ProfilecustomerController {
    public static HttpServletRequest request;

    private void setRequest(HttpServletRequest request) {
        ProfilecustomerController.request = request;

    }


    @GetMapping("/v1/profilecustomer")
    public ResponseEntity<Map<String, Object>> AddProduct(
            @RequestParam(defaultValue = "") String customerLoginID,
            @RequestParam(defaultValue = "") String customerLoginEmail

    ) throws JSONException {

        Map<String, Object> response = new HashMap<>();


        if (
                customerLoginID.equals("")
                        || customerLoginEmail.equals("")

        ) {

            response.put("resultCode", "406");
            response.put("resultMsg", "parameter not acceptable");
            response.put("extraPara", "");

            return ResponseEntity.ok(response);

        }


        //=====================URL save to db===========================

        PreparedStatement pstmt;
        ResultSet rs;
        DatabaseConnectionPool dbConnectionPool = null;
        Connection connection1 = null;
        String strRetunr = null;
        Statement statementtAuth = null;
        ResultSet resultSettAuth = null;
        Connection conntAuth = null;

        String query = "SELECT customer_first_name, customer_last_name, customer_phonenumber, customer_address, customer_url_image, customer_email " +
                "FROM customer_tb " +
                "WHERE customer_id = '"+customerLoginID+"' OR customer_email = '"+customerLoginEmail+"';";


        try {
            dbConnectionPool = new DatabaseConnectionPool(
                    Config.driverServr, Config.dburlServr,
                    Config.dbUserNameServr,
                    Config.dbPasswordServr);
            connection1 = dbConnectionPool.getConnection();
            PreparedStatement statement = connection1.prepareStatement(query);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String firstName = resultSet.getString("customer_first_name");
                String lastName = resultSet.getString("customer_last_name");
                String phoneNumber = resultSet.getString("customer_phonenumber");
                String address = resultSet.getString("customer_address");
                String customeremail = resultSet.getString("customer_email");
                String customerurlimage = resultSet.getString("customer_url_image");
                // Do something with the result set


                response.put("resultCode", "200");
                response.put("resultMsg", "OK, success");
                response.put("FirstName", firstName);
                response.put("LastName", lastName);
                response.put("PhoneNumber", phoneNumber);
                response.put("Address", address);
                response.put("Email", customeremail);
                response.put("URL_image", customerurlimage);


                return ResponseEntity.ok(response);

            }


        } catch (SQLException ex) {

            response.put("resultCode", "216");
            response.put("resultMsg", "Error query Data Profile");
            response.put("extraPara", "===");

            return ResponseEntity.ok(response);
        } finally {

            try {

                dbConnectionPool.freeConnection(connection1);
                // release resources
                // dbConnectionPool.destroy();

                if (conntAuth != null) {
                    conntAuth.close();
                }

                if (statementtAuth != null) {
                    statementtAuth.close();
                }

                if (resultSettAuth != null) {
                    resultSettAuth.close();
                }
            } catch (Exception e3) {

            }
        }
        response.put("resultCode", "000");
        response.put("resultMsg", "NULL");

        return ResponseEntity.ok(response);
    }

}
