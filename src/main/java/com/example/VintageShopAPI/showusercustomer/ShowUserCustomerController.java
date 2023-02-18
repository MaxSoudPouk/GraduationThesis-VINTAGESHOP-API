package com.example.VintageShopAPI.showusercustomer;

import com.example.VintageShopAPI.addproduct.AddProductController;
import com.example.VintageShopAPI.db.Config;
import com.example.VintageShopAPI.db.DatabaseConnectionPool;
import org.json.JSONException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class ShowUserCustomerController {
    public static HttpServletRequest request;
    DatabaseConnectionPool dbConnectionPool;


    private void setRequest(HttpServletRequest request) {
        ShowUserCustomerController.request = request;

    }
    @GetMapping("/v1/ShowUserCustomer")
    public ResponseEntity<Map<String, Object>> AddProduct(
            @RequestParam(defaultValue = "") String token

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
        String sql = "SELECT customer_id, customer_first_name, customer_url_image, customer_phonenumber, customer_address FROM customer_tb;";
//=========================== DB ===========================//

        try {
            dbConnectionPool = new DatabaseConnectionPool(Config.driverServr, Config.dburlServr, Config.dbUserNameServr, Config.dbPasswordServr);
            connection1 = dbConnectionPool.getConnection();
            PreparedStatement statement = connection1.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();



            while (resultSet.next()) {
                Map<String, String> customer_detail = new HashMap<>();

                String customer_id = resultSet.getString("customer_id");
                String customer_first_name = resultSet.getString("customer_first_name");
                String customer_url_image = resultSet.getString("customer_url_image");
                String customer_phonenumber = resultSet.getString("customer_phonenumber");
                String customer_address = resultSet.getString("customer_address");


                customer_detail.put("customerId", String.valueOf(customer_id));
                customer_detail.put("customerName", String.valueOf(customer_first_name));
                customer_detail.put("customerProfile", String.valueOf(customer_url_image));
                customer_detail.put("customerPhoneNumber", String.valueOf(customer_phonenumber));
                customer_detail.put("customerAddress", String.valueOf(customer_address));

                resultMsg.add(customer_detail);

            }

            response.put("resultCode", "200");
            response.put("resultMsg", "OK, success");
            response.put("CustomerDetail", resultMsg);

            return ResponseEntity.ok(response);


        } catch (SQLException ex) {

            response.put("resultCode", "216");
            response.put("resultMsg", "Error query Data Product");
            response.put("extraPara", "");

            return ResponseEntity.ok(response);
        }
    }
}
