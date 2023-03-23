package com.example.VintageShopAPI.CheckOut;

import com.example.VintageShopAPI.customermessage.CustomerMessageController;
import com.example.VintageShopAPI.db.Config;
import com.example.VintageShopAPI.db.DatabaseConnectionPool;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONPointerException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@CrossOrigin
public class CheckoutController {
    public static HttpServletRequest request;


    private void setRequest(HttpServletRequest request) {
        CheckoutController.request = request;

    }
    @PostMapping("/v1/CheckoutOrder")
    public ResponseEntity<Map<String, Object>> AddProduct(
            @RequestBody String orders

    )  throws NoSuchAlgorithmException, IOException, JSONException, JSONPointerException {
//        System.out.println("order =====" + orders);


        Map<String, Object> response = new HashMap<>();
        List<Map<String, String>> resultMsg = new ArrayList<>();
        List<Map<String, String>> resultPrice = new ArrayList<>();
        ArrayList<Double> valueprice = new ArrayList<Double>();

        JSONObject jObj = new JSONObject(orders);
        JSONArray order = jObj.getJSONArray("order");
        String customer_id = jObj.getString("customer_id");
        String order_status = jObj.getString("order_status");
        int total_prices = jObj.getInt("total_price");
        String order_id = jObj.getString("order_id");
        String customer_name = jObj.getString("customer_name");
        String transaction_id = jObj.getString("transaction_id");

        double total_price = Double.parseDouble(String.valueOf(total_prices));
        //====================== Create date ======================//

        Calendar currentDate111 = Calendar.getInstance();
        SimpleDateFormat formatter111 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formatter111.setTimeZone(TimeZone.getTimeZone("Asia/Bangkok"));
        String dateNow111 = formatter111.format(currentDate111.getTime());
        String datepro111 = dateNow111.toString();

//====================== END Create date ======================//
        int product_status = 1;
        PreparedStatement pstmt;
        ResultSet rs;
        DatabaseConnectionPool dbConnectionPool = null;
        Connection connection1 = null;
        String strRetunr = null;
        Statement statementtAuth = null;
        ResultSet resultSettAuth = null;
        Connection conntAuth = null;
        try {
            dbConnectionPool = new DatabaseConnectionPool(
                    Config.driverServr,
                    Config.dburlServr,
                    Config.dbUserNameServr,
                    Config.dbPasswordServr);
            connection1 = dbConnectionPool.getConnection();

        String sql = "insert into order_tb (order_id, customer_id, total_price, order_date, order_status, transaction_id, customer_name, order_amount, deleted_status) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String sql1 = "SELECT pa.product_amount,pt.product_status " +
                "FROM products_attribute pa " +
                "JOIN products_tb pt ON pa.product_id = pt.product_id " +
                "WHERE pa.product_id = ? AND pa.price = ? ";
        String sql2 = "INSERT INTO order_detail (order_id, product_id, price, quantity, product_name)\n" +
                "VALUES (?, ?, ?, ?, ?);";
        String updateProductAmount = "UPDATE products_attribute SET product_amount = ? WHERE product_id = ?";
        String updateProductStatus = "UPDATE products_tb SET product_status = ? WHERE product_id = ?";

            String amount_order = String.valueOf(order.length());
            System.out.println("amount_order ==== " + amount_order);

            for (int i = 0; i < order.length(); i++) {
            JSONObject innerObj = order.getJSONObject(i);
                Map<String, String> person1 = new HashMap<>();
                ArrayList<Double> total_pricess = new ArrayList<Double>();

            String product_id = (String) innerObj.get("product_id");
            String product_name = (String) innerObj.get("product_name");
            int product_quantity = (int) innerObj.get("product_quantity");
            String product_prices = (String) innerObj.get("product_price");



            double product_price = Double.parseDouble(product_prices);

                person1.put("product_name", String.valueOf(product_name));//LOOP product_name
                person1.put("product_id", String.valueOf(product_id));//LOOP product_id



//            System.out.println("product_id ==== " + product_id);



            if (
                    product_id.isEmpty()
                            || order_status.isEmpty()
                            || order_id.isEmpty()
                            || product_name.isEmpty()

                            || customer_id.isEmpty()
                            || customer_name.isEmpty()
                            || transaction_id.isEmpty()
            ) {


                response.put("resultCode", "406");
                response.put("resultMsg", "parameter not acceptable");
                response.put("extraPara", "");

                return ResponseEntity.ok(response);

            }

                PreparedStatement statement2 = connection1.prepareStatement(sql2);
                statement2.setString(1, order_id);
                statement2.setString(2, product_id);
                statement2.setDouble(3, product_price);
                statement2.setInt(4, product_quantity);
                statement2.setString(5, product_name);

                int rowsUpdated4 = statement2.executeUpdate();



            PreparedStatement statement = connection1.prepareStatement(sql1);
            statement.setString(1, product_id);
            statement.setDouble(2, product_price);

            ResultSet resultSet = statement.executeQuery();


            while (resultSet.next()) {
                // System.out.println("An existing user was updated successfully!");
                String product_amount = resultSet.getString("product_amount");
                String product_statusDB = resultSet.getString("product_status");


                int amount = Integer.parseInt(product_amount);
//                System.out.println( "product_statusDB ==== " + product_statusDB);
                person1.put("product_status", String.valueOf(product_statusDB));//LOOP product_status

                if(product_statusDB.equals("1")){
                    person1.put("product_price", (product_prices));//LOOP product_prices
                    total_pricess.add(product_price);//LOOP product_prices

                } else if (product_statusDB.equals("0")) {
                    double product_price_active = .00;
//                    System.out.println( "product_prices ==== " + product_prices);
                    total_pricess.add(product_price_active);//LOOP product_prices

                }

//                System.out.println( "amount ==== " + amount);
                if(amount >= product_quantity){
                    int new_amount = (amount - product_quantity);
//                    System.out.println( "new_amount ==== " + new_amount);

                    if( new_amount > 0){
                        PreparedStatement statement1 = connection1.prepareStatement(updateProductAmount);
                        statement1.setInt(1, new_amount);
                        statement1.setString(2, product_id);
                        int rowsUpdated2 = statement1.executeUpdate();

                        // System.out.println("An existing user was updated successfully!");

                    }else {


                        PreparedStatement statement10 = connection1.prepareStatement(updateProductAmount);
                        statement10.setInt(1, new_amount);
                        statement10.setString(2, product_id);
//                        System.out.println( "new_amount ==== " + new_amount);

                        int rowsUpdated2 = statement10.executeUpdate();

                        if (rowsUpdated2 > 0) {
                            int status_now = 0;
                            PreparedStatement statement3 = connection1.prepareStatement(updateProductStatus);
                            statement3.setInt(1, status_now);
                            statement3.setString(2, product_id);
                            int rowsUpdated3 = statement3.executeUpdate();
                            // System.out.println("An existing user was updated successfully!");

                        }
                    }


                }
            }

                double sum = 0;
                for (double num : total_pricess) {
                    sum += num;
                }
                valueprice.add(sum);
                resultMsg.add(person1);
        }



                PreparedStatement statement = connection1.prepareStatement(sql);

                statement.setString(1, order_id);
                statement.setString(2, customer_id);
                statement.setDouble(3, total_price);
                statement.setString(4, datepro111);
                statement.setString(5, order_status);
                statement.setString(6, transaction_id);
                statement.setString(7, customer_name);
                statement.setString(8, amount_order);
                statement.setString(9, "1");

                int rowsUpdated1 = statement.executeUpdate();




                // System.out.println("sql====="+ sql);
                if (rowsUpdated1 > 0) {
                    // System.out.println("An existing user was updated successfully!");
                    double sumtotal_price = 0;
                    for (double num : valueprice) {
                        sumtotal_price += num;
                    }

                    response.put("resultCode", "200");
                    response.put("resultMsg", "OK, success");
                    response.put("order_detail", resultMsg);
                    response.put("total_price", sumtotal_price);
                    response.put("date_price", datepro111);
                    response.put("transaction_id", transaction_id);

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
