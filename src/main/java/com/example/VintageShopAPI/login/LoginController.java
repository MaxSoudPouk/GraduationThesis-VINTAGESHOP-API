package com.example.VintageShopAPI.login;

import com.example.VintageShopAPI.addproduct.AddProductController;
import com.example.VintageShopAPI.db.Config;
import com.example.VintageShopAPI.db.DatabaseConnectionPool;
import com.example.VintageShopAPI.security.JWT_Security_Encode_Decode_Java;
import org.json.JSONException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@CrossOrigin
public class LoginController {
    public static HttpServletRequest request;


    private void setRequest(HttpServletRequest request) {
        LoginController.request = request;

    }

    @PostMapping("/v1/login")
    public ResponseEntity<Map<String, Object>> Login(
            @RequestParam(defaultValue = "") String customer_email,
            @RequestParam(defaultValue = "") String customer_password,
            @RequestParam(defaultValue = "") String user_detail

    ) throws JSONException {
        Map<String, Object> response = new HashMap<>();

        if (
                customer_email.equals("") || customer_password.equals("") || user_detail.equals("")
        ) {

            response.put("resultCode", "406");
            response.put("resultMsg", "parameter not acceptable");
            response.put("extraPara", "");

            return ResponseEntity.ok(response);

        }

        //========================== DB CONFIG ==========================//
        String URL_image1 = "";

        PreparedStatement pstmt;
        ResultSet rs;
        DatabaseConnectionPool dbConnectionPool = null;
        Connection connection1 = null;
        String strRetunr = null;
        Statement statementtAuth = null;
        ResultSet resultSettAuth = null;
        Connection conntAuth = null;

        String sql = "SELECT customer_id,customer_first_name,customer_url_image " +
                "from customer_tb  where customer_email= '" + customer_email + "' and customer_password = '" + customer_password + "'  LIMIT 1";
        String sqladmin = "SELECT customer_id,customer_first_name,customer_url_image " +
                "from admin_tb  where customer_email= '" + customer_email + "' and customer_password = '" + customer_password + "'  LIMIT 1";
        //========================== DB CONFIG END ==========================//
        if (user_detail.equals("Customer User")) {
            try {
                dbConnectionPool = new DatabaseConnectionPool(
                        Config.driverServr,
                        Config.dburlServr,
                        Config.dbUserNameServr,
                        Config.dbPasswordServr);
                connection1 = dbConnectionPool.getConnection();
                PreparedStatement statement = connection1.prepareStatement(sql);

                ResultSet resultSet = statement.executeQuery();


                // System.out.println("sql====="+ sql);
                if (resultSet != null && resultSet.next()) {
                    String Customer_ID = resultSet.getString("customer_id");
                    String customerurlimage = resultSet.getString("customer_url_image");


                    // Gen JWT
                    String jwtTokenStrng = "";

                    // createJWTSec(String id, long ttlMillis, String userName, String userID)

                    JWT_Security_Encode_Decode_Java encode_Decode_Java = new JWT_Security_Encode_Decode_Java();
                    long ttlMillis = 604800000; // 604800000 = 1 week, 3 h = 10800000 ms
                    jwtTokenStrng = encode_Decode_Java.createJWTSec1(ttlMillis, customer_email, Customer_ID);


                    response.put("resultCode", "200");
                    response.put("resultMsg", "OK, success");
                    response.put("Token", jwtTokenStrng);
                    response.put("Customer_ID", Customer_ID);
                    response.put("Customer_Email", customer_email);
                    response.put("customer_url_image", customerurlimage);
                    response.put("user_detail", user_detail);


                    return ResponseEntity.ok(response);
                } else {
                    response.put("resultCode", "217");
                    response.put("resultMsg", "Fail, Login not Success");

                    return ResponseEntity.ok(response);
                }

            } catch (SQLException ex) {

                response.put("resultCode", "216");
                response.put("resultMsg", "Error query Data Product");
                response.put("extraPara", "");

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
                    response.put("resultCode", "000");
                    response.put("resultMsg", "NULL");

                    return ResponseEntity.ok(response);
                }
            }
        } else if (user_detail.equals("Admin User")) {
            try {
                dbConnectionPool = new DatabaseConnectionPool(
                        Config.driverServr,
                        Config.dburlServr,
                        Config.dbUserNameServr,
                        Config.dbPasswordServr);
                connection1 = dbConnectionPool.getConnection();
                PreparedStatement statement = connection1.prepareStatement(sqladmin);

                ResultSet resultSet = statement.executeQuery();


                // System.out.println("sql====="+ sql);
                if (resultSet != null && resultSet.next()) {
                    String Customer_ID = resultSet.getString("customer_id");
                    String customerurlimage = resultSet.getString("customer_url_image");


                    // Gen JWT
                    String jwtTokenStrng = "";

                    // createJWTSec(String id, long ttlMillis, String userName, String userID)

                    JWT_Security_Encode_Decode_Java encode_Decode_Java = new JWT_Security_Encode_Decode_Java();
                    long ttlMillis = 604800000; // 604800000 = 1 week, 3 h = 10800000 ms
                    jwtTokenStrng = encode_Decode_Java.createJWTSec1(ttlMillis, customer_email, Customer_ID);


                    response.put("resultCode", "200");
                    response.put("resultMsg", "OK, success");
                    response.put("Token", jwtTokenStrng);
                    response.put("Customer_ID", Customer_ID);
                    response.put("Customer_Email", customer_email);
                    response.put("customer_url_image", customerurlimage);
                    response.put("user_detail", user_detail);


                    return ResponseEntity.ok(response);
                } else {
                    response.put("resultCode", "217");
                    response.put("resultMsg", "Fail, Login not Success");

                    return ResponseEntity.ok(response);
                }

            } catch (SQLException ex) {

                response.put("resultCode", "216");
                response.put("resultMsg", "Error query Data Product");
                response.put("extraPara", "");

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
                    response.put("resultCode", "000");
                    response.put("resultMsg", "NULL");

                    return ResponseEntity.ok(response);
                }
            }
        } else {
            response.put("resultCode", "000");
            response.put("resultMsg", "NULL");

            return ResponseEntity.ok(response);
        }
    }

}
