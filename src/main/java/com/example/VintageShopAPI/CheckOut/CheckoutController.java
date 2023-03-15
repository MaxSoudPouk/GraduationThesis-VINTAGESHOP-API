package com.example.VintageShopAPI.CheckOut;

import com.example.VintageShopAPI.customermessage.CustomerMessageController;
import com.example.VintageShopAPI.db.Config;
import com.example.VintageShopAPI.db.DatabaseConnectionPool;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONPointerException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class CheckoutController {
    public static HttpServletRequest request;


    private void setRequest(HttpServletRequest request) {
        CheckoutController.request = request;

    }
    @PostMapping("/v1/CheckoutOrder")
    public ResponseEntity<Map<String, Object>> AddProduct(
            @RequestBody String orders

    )  throws NoSuchAlgorithmException, IOException, JSONException, JSONPointerException {
        System.out.println("order =====" + orders);

        Map<String, String> person1 = new HashMap<>();
        Map<String, Object> response = new HashMap<>();
        List<Map<String, String>> resultMsg = new ArrayList<>();

        JSONObject jObj = new JSONObject(orders);
        JSONArray order = jObj.getJSONArray("order");
        String customer_id = jObj.getString("customer_id");
        String order_status = jObj.getString("order_status");
        String total_price = jObj.getString("total_price");
        String order_id = jObj.getString("order_id");
        String customer_name = jObj.getString("customer_name");
        String transaction_id = jObj.getString("transaction_id");


        //====================== Create date ======================//

        Calendar currentDate111 = Calendar.getInstance();
        SimpleDateFormat formatter111 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formatter111.setTimeZone(TimeZone.getTimeZone("Asia/Bangkok"));
        String dateNow111 = formatter111.format(currentDate111.getTime());
        String datepro111 = dateNow111.toString();

//====================== END Create date ======================//

        for (int i = 0; i < order.length(); i++) {
            JSONObject innerObj = order.getJSONObject(i);

            String product_id = (String) innerObj.get("product_id");
            String product_name = (String) innerObj.get("product_name");
            String product_quantity = (String) innerObj.get("product_quantity");
            String product_price = (String) innerObj.get("product_price");

            if (
                    product_id.isEmpty()
                            || order_status.isEmpty()
                            || total_price.isEmpty()
                            || order_id.isEmpty()
                            || product_name.isEmpty()
                            || product_quantity.isEmpty()
                            || product_price.isEmpty()
                            || customer_id.isEmpty()
                            || customer_name.isEmpty()
                            || transaction_id.isEmpty()
            ) {


                response.put("resultCode", "406");
                response.put("resultMsg", "parameter not acceptable");
                response.put("extraPara", "");

                return ResponseEntity.ok(response);

            }
        }
            int product_status = 1;
            PreparedStatement pstmt;
            ResultSet rs;
            DatabaseConnectionPool dbConnectionPool = null;
            Connection connection1 = null;
            String strRetunr = null;
            Statement statementtAuth = null;
            ResultSet resultSettAuth = null;
            Connection conntAuth = null;

            String sql = "insert into order_tb (order_id, customer_id, total_price, order_date, order_status, transaction_id, customer_name) values (?, ?, ?, ?, ?, ?, ?)";

            try {
                dbConnectionPool = new DatabaseConnectionPool(
                        Config.driverServr,
                        Config.dburlServr,
                        Config.dbUserNameServr,
                        Config.dbPasswordServr);
                connection1 = dbConnectionPool.getConnection();
                PreparedStatement statement = connection1.prepareStatement(sql);

                statement.setString(1, order_id);
                statement.setString(2, customer_id);
                statement.setString(3, total_price);
                statement.setString(4, datepro111);
                statement.setString(5, order_status);
                statement.setString(6, transaction_id);
                statement.setString(7, customer_name);
                System.out.println("========================");

                int rowsUpdated1 = statement.executeUpdate();




                // System.out.println("sql====="+ sql);
                if (rowsUpdated1 > 0) {
                    // System.out.println("An existing user was updated successfully!");

                    response.put("resultCode", "200");
                    response.put("resultMsg", "OK, success");

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

                }
            }







            return null;
    }
}
