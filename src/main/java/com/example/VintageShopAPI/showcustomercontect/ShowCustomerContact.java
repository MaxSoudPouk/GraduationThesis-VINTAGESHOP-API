package com.example.VintageShopAPI.showcustomercontect;

import com.example.VintageShopAPI.db.Config;
import com.example.VintageShopAPI.db.DatabaseConnectionPool;
import org.json.JSONException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class ShowCustomerContact {
    @GetMapping("/v1/ShowCustomerContact")
    public ResponseEntity<Map<String, Object>> AddProduct(
            @RequestParam("") String token

    ) throws JSONException {
        Map<String, Object> response = new HashMap<>();

        List<Map<String, String>> resultMsg = new ArrayList<>();


        if (token.equals("")) {


            response.put("resultCode", "406");
            response.put("resultMsg", "parameter not acceptable");
            response.put("extraPara", "");

            return ResponseEntity.ok(response);

        }
        //=========================== DB ===========================//
        PreparedStatement pstmt;
        ResultSet rs;
        DatabaseConnectionPool dbConnectionPool = null;
        Connection connection1 = null;
        String strRetunr = null;
        Statement statementtAuth = null;
        ResultSet resultSettAuth = null;
        Connection conntAuth = null;
        String sql = "SELECT cm.message_id, cm.customer_email, cm.message_customer, cm.date_time,ct.customer_first_name, ct.customer_address, ct.customer_url_image, ct.customer_phonenumber\n" +
                "FROM customer_message cm\n" +
                "INNER JOIN customer_tb ct\n" +
                "ON cm.customer_id = ct.customer_id\n"+
                "WHERE cm.message_status_delete = 1 ";

//=========================== vintage0001 ===========================//
        try {
            dbConnectionPool = new DatabaseConnectionPool(
                    Config.driverServr,
                    Config.dburlServr,
                    Config.dbUserNameServr,
                    Config.dbPasswordServr);
            connection1 = dbConnectionPool.getConnection();
            PreparedStatement statement = connection1.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();


            while (resultSet.next()) {
                Map<String, String> product_detail = new HashMap<>();

                String customer_email = resultSet.getString("customer_email");
                String message_customer = resultSet.getString("message_customer");
                String date_time = resultSet.getString("date_time");
                String customer_first_name = resultSet.getString("customer_first_name");
                String customer_address = resultSet.getString("customer_address");
                String customer_url_image = resultSet.getString("customer_url_image");
                String customer_phonenumber = resultSet.getString("customer_phonenumber");
                String message_id = resultSet.getString("message_id");

                product_detail.put("customer_email", String.valueOf(customer_email));
                product_detail.put("message_customer", String.valueOf(message_customer));
                product_detail.put("date_time", String.valueOf(date_time));
                product_detail.put("customer_first_name", String.valueOf(customer_first_name));
                product_detail.put("customer_address", String.valueOf(customer_address));
                product_detail.put("customer_url_image", String.valueOf(customer_url_image));
                product_detail.put("customer_phonenumber", String.valueOf(customer_phonenumber));
                product_detail.put("message_id", String.valueOf(message_id));

                resultMsg.add(product_detail);


            }
        } catch (SQLException ex) {

            response.put("resultCode", "216");
            response.put("resultMsg", "Error query Data Product");
            response.put("extraPara", "");

            return ResponseEntity.ok(response);
        }

        response.put("resultCode", "200");
        response.put("productIDShow", "vintage0001");
        response.put("contactdetail", resultMsg);
        return ResponseEntity.ok(response);
    }
}
