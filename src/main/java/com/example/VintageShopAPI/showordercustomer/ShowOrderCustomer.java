package com.example.VintageShopAPI.showordercustomer;

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
public class ShowOrderCustomer {

    @GetMapping("/v1/ShowOrderCustomer")
    public ResponseEntity<Map<String, Object>> AddProduct(
            @RequestParam(defaultValue = "") String token,
            @RequestParam(defaultValue = "") String OrderID

    ) throws JSONException {
        Map<String, Object> response = new HashMap<>();
        List<Map<String, String>> resultMsg = new ArrayList<>();

        if (token.equals("") || OrderID.equals("")) {


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

        String sql = "SELECT o.order_id, o.customer_id, c.customer_first_name AS customer_name, o.total_price, o.order_amount, o.transaction_id, o.order_date, c.customer_url_image, c.customer_phonenumber\n" +
                "FROM order_tb o\n" +
                "INNER JOIN customer_tb c ON o.customer_id = c.customer_id\n" +
                "WHERE order_id='" + OrderID + "'";
        String sql1 = "SELECT product_name, quantity, price\n" +
                "FROM order_detail\n" +
                "WHERE order_id = '" + OrderID + "';";

//=========================== vintage0001 ===========================//


        try {
            dbConnectionPool = new DatabaseConnectionPool(Config.driverServr, Config.dburlServr, Config.dbUserNameServr, Config.dbPasswordServr);
            connection1 = dbConnectionPool.getConnection();
            PreparedStatement statement = connection1.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            String order_id = "";
            String customer_id = "";
            String order_amount = "";
            String customer_name = "";
            String total_price = "";
            String transaction_id = "";
            String order_date = "";
            String customer_url_image = "";
            String customer_phonenumber = "";
            while (resultSet.next()) {

                order_id = resultSet.getString("order_id");
                customer_id = resultSet.getString("customer_id");
                order_amount = resultSet.getString("order_amount");
                customer_name = resultSet.getString("customer_name");
                total_price = resultSet.getString("total_price");
                transaction_id = resultSet.getString("transaction_id");
                order_date = resultSet.getString("order_date");
                customer_url_image = resultSet.getString("customer_url_image");
                customer_phonenumber = resultSet.getString("customer_phonenumber");

//
//                product_detail.put("OrderID", String.valueOf(order_id));
//                product_detail.put("CustomerID", String.valueOf(customer_id));
//                product_detail.put("OrderAmount", String.valueOf(order_amount));
//                product_detail.put("customerName", String.valueOf(customer_name));
//                product_detail.put("TotalPrice", String.valueOf(total_price));
//                product_detail.put("transaction_id", String.valueOf(transaction_id));
//                product_detail.put("order_date", String.valueOf(order_date));
//                product_detail.put("customer_url_image", String.valueOf(customer_url_image));
//                product_detail.put("customer_phonenumber", String.valueOf(customer_phonenumber));


            }


            PreparedStatement statement1 = connection1.prepareStatement(sql1);

            ResultSet resultSet1 = statement1.executeQuery();
            while (resultSet1.next()) {
                Map<String, String> product_detail = new HashMap<>();

                String quantity = resultSet1.getString("quantity");
                String price = resultSet1.getString("price");
                String product_name = resultSet1.getString("product_name");


                product_detail.put("Quantity", String.valueOf(quantity));
                product_detail.put("Price", String.valueOf(price));
                product_detail.put("ProductName", String.valueOf(product_name));


                resultMsg.add(product_detail);

            }
            response.put("resultCode", "200");
            response.put("order_id", order_id);
            response.put("customer_id", customer_id);
            response.put("order_amount", order_amount);
            response.put("customer_name", customer_name);
            response.put("total_price", total_price);
            response.put("transaction_id", transaction_id);
            response.put("order_date", order_date);
            response.put("customer_url_image", customer_url_image);
            response.put("customer_phonenumber", customer_phonenumber);
            response.put("SaleItemDetail", resultMsg);
            return ResponseEntity.ok(response);

        } catch (SQLException ex) {

            response.put("resultCode", "216");
            response.put("resultMsg", "Error query Data Product");
            response.put("extraPara", "");

            return ResponseEntity.ok(response);
        }


    }
}
