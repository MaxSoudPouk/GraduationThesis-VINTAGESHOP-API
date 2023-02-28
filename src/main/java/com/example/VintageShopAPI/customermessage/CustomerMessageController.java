package com.example.VintageShopAPI.customermessage;

import com.example.VintageShopAPI.db.Config;
import com.example.VintageShopAPI.db.DatabaseConnectionPool;
import com.example.VintageShopAPI.deleteproduct.DeleteProductController;
import com.example.VintageShopAPI.security.JWT_Security_Encode_Decode_Java;
import org.json.JSONException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@CrossOrigin
public class CustomerMessageController {
    public static HttpServletRequest request;
    DatabaseConnectionPool dbConnectionPool;


    private void setRequest(HttpServletRequest request) {
        CustomerMessageController.request = request;

    }
    @PostMapping("/v1/CustomerMessage")
    public ResponseEntity<Map<String, Object>> AddProduct(
            @RequestParam(defaultValue = "") String customer_ID,
            @RequestParam(defaultValue = "") String customer_name,
            @RequestParam(defaultValue = "") String customer_email,
            @RequestParam(defaultValue = "") String token,
            @RequestParam(defaultValue = "") String message_customer

    ) throws JSONException {

        Map<String, Object> response = new HashMap<>();

        List<Map<String, String>> resultMsg = new ArrayList<>();


        if (
                customer_name.equals("") ||
                customer_email.equals("") ||
                        message_customer.equals("")){


            response.put("resultCode", "406");
            response.put("resultMsg", "parameter not acceptable");
            response.put("extraPara", "");

            return ResponseEntity.ok(response);

        }

//        validate JWT
        boolean jwtencoderesult = false;

        JWT_Security_Encode_Decode_Java encode_Decode_Java = new JWT_Security_Encode_Decode_Java();
        jwtencoderesult = encode_Decode_Java.deCodeJWT_validate(token, customer_email, customer_ID);

        if (jwtencoderesult) {
            //====================== Create date ======================//

            Calendar currentDate111 = Calendar.getInstance();
            SimpleDateFormat formatter111 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            formatter111.setTimeZone(TimeZone.getTimeZone("Asia/Bangkok"));
            String dateNow111 = formatter111.format(currentDate111.getTime());
            String datepro111 = dateNow111.toString();

//====================== END Create date ======================//
//=========================== DB ===========================//
            PreparedStatement pstmt;
            ResultSet rs;
            DatabaseConnectionPool dbConnectionPool = null;
            Connection connection1 = null;
            String strRetunr = null;
            Statement statementtAuth = null;
            ResultSet resultSettAuth = null;
            Connection conntAuth = null;
            String sql = "insert into customer_message (customer_id, customer_name, customer_email, date_time, message_customer) " +
                    "values (?, ?, ?, ?, ?); ";
            try {
                dbConnectionPool = new
                        DatabaseConnectionPool(Config.driverServr,
                        Config.dburlServr,
                        Config.dbUserNameServr,
                        Config.dbPasswordServr);
                connection1 = dbConnectionPool.getConnection();
                PreparedStatement statement = connection1.prepareStatement(sql);

                statement.setString(1, customer_ID);
                statement.setString(2, customer_name);
                statement.setString(3, customer_email);
                statement.setString(4, datepro111);
                statement.setString(5, message_customer);
                int rowsUpdated = statement.executeUpdate();

                if (rowsUpdated > 0) {

                    response.put("resultCode", "200");
                    response.put("resultMsg", "OK, success");
                    response.put("date", datepro111);

                    return ResponseEntity.ok(response);
                }

            } catch (SQLException ex) {

                response.put("resultCode", "216");
                response.put("resultMsg", "Error query Data Product");
                response.put("extraPara", "");

                return ResponseEntity.ok(response);
            }

//=========================== DB ===========================//
        } else {

            response.put("resultCode", "498");
            response.put("ResultMsg", "Invalid_Token");
            return ResponseEntity.ok(response);

        }
        response.put("resultCode", "000");
        response.put("resultMsg", "NULL");

        return ResponseEntity.ok(response);
    }
}
