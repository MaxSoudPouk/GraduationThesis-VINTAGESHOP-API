package com.example.VintageShopAPI.showsaleitem;

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
public class ShowSaleitemController {
    @GetMapping("/v1/ShowSaleitemController")
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
        String sql = "SELECT o.order_id, o.customer_id, o.order_amount, o.order_date, c.customer_first_name, c.customer_phonenumber, c.customer_address \n" +
                "FROM order_tb o \n" +
                "JOIN customer_tb c ON o.customer_id = c.customer_id AND o.deleted_status = '1';";

//=========================== vintage0001 ===========================//


        try {
            dbConnectionPool = new DatabaseConnectionPool(Config.driverServr, Config.dburlServr, Config.dbUserNameServr, Config.dbPasswordServr);
            connection1 = dbConnectionPool.getConnection();
            PreparedStatement statement = connection1.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();


            while (resultSet.next()) {
                Map<String, String> product_detail = new HashMap<>();

                String order_id = resultSet.getString("order_id");
                String customer_id = resultSet.getString("customer_id");
                String order_amount = resultSet.getString("order_amount");
                String customer_first_name = resultSet.getString("customer_first_name");
                String customer_address = resultSet.getString("customer_address");
                String customer_phonenumber = resultSet.getString("customer_phonenumber");


                product_detail.put("OrderID", String.valueOf(order_id));
                product_detail.put("CustomerID", String.valueOf(customer_id));
                product_detail.put("OrderAmount", String.valueOf(order_amount));
                product_detail.put("CustomerFirstName", String.valueOf(customer_first_name));
                product_detail.put("CustomerAddress", String.valueOf(customer_address));
                product_detail.put("CustomerPhoneNumber", String.valueOf(customer_phonenumber));

                resultMsg.add(product_detail);

            }
        } catch (SQLException ex) {

            response.put("resultCode", "216");
            response.put("resultMsg", "Error query Data Product");
            response.put("extraPara", "");

            return ResponseEntity.ok(response);
        }

        response.put("resultCode", "200");
        response.put("SaleItemDetail", resultMsg);
        return ResponseEntity.ok(response);
    }
}