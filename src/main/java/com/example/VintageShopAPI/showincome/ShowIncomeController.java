package com.example.VintageShopAPI.showincome;

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
public class ShowIncomeController {

    @GetMapping("/v1/ShowIncome")
    public ResponseEntity<Map<String, Object>> AddProduct(
            @RequestParam("DateStart") String DateStart,
            @RequestParam("DateEnd") String DateEnd

    ) throws JSONException {
        Map<String, Object> response = new HashMap<>();

        List<Map<String, String>> resultMsg = new ArrayList<>();
        List<Map<String, String>> resultMsgExpenses = new ArrayList<>();

        if (
                DateStart.equals("")||
                        DateEnd.equals("")
        ){


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
        String sql = "SELECT title, cost, amount, transaction_date, transaction_status, delete_transaction_status, type_transaction \n" +
                "FROM transaction_tb\n" +
                "WHERE transaction_status = '1' AND DATE(transaction_date) BETWEEN '"+DateStart+"' AND '"+DateEnd+"';";
        String sqlExs = "SELECT title, cost, amount, transaction_date, transaction_status, delete_transaction_status, type_transaction \n" +
                "FROM transaction_tb\n" +
                "WHERE transaction_status = '2' AND DATE(transaction_date) BETWEEN '"+DateStart+"' AND '"+DateEnd+"';";
        String sql1 = "SELECT customer_name, total_price, order_amount, order_date, IncomeDeleteStatus \n" +
                "FROM order_tb\n" +
                "WHERE DATE(order_date) BETWEEN '"+DateStart+"' AND '"+DateEnd+"';";

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

            //Other Income

            while (resultSet.next()) {
                Map<String, String> product_detail = new HashMap<>();

                String title = resultSet.getString("title");
                String cost = resultSet.getString("cost");
                String amount = resultSet.getString("amount");
                String transaction_date = resultSet.getString("transaction_date");
                String transaction_status = resultSet.getString("transaction_status");
                String IncomeDeleteStatus = resultSet.getString("delete_transaction_status");
                String type_transaction = resultSet.getString("type_transaction");


                product_detail.put("title", String.valueOf(title));
                product_detail.put("cost", String.valueOf(cost));
                product_detail.put("amount", String.valueOf(amount));
                product_detail.put("transaction_date", String.valueOf(transaction_date));
                product_detail.put("transaction_status", String.valueOf(transaction_status));
                product_detail.put("IncomeDeleteStatus", String.valueOf(IncomeDeleteStatus));
                product_detail.put("type", String.valueOf(type_transaction));



                resultMsg.add(product_detail);


            }




            //Other Expenses

            PreparedStatement statement1 = connection1.prepareStatement(sql1);
            ResultSet resultSet1 = statement1.executeQuery();

            while (resultSet1.next()) {
                Map<String, String> product_detail1 = new HashMap<>();

                String customer_name = resultSet1.getString("customer_name");
                String total_price = resultSet1.getString("total_price");
                String order_amount = resultSet1.getString("order_amount");
                String order_date = resultSet1.getString("order_date");
                String IncomeDeleteStatus = resultSet1.getString("IncomeDeleteStatus");


                product_detail1.put("title", String.valueOf(customer_name));
                product_detail1.put("cost", String.valueOf(total_price));
                product_detail1.put("amount", String.valueOf(order_amount));
                product_detail1.put("transaction_date", String.valueOf(order_date));
                product_detail1.put("IncomeDeleteStatus", String.valueOf(IncomeDeleteStatus));
                product_detail1.put("type", "Order");


                resultMsg.add(product_detail1);


            }

            PreparedStatement statement3 = connection1.prepareStatement(sqlExs);
            ResultSet resultSet3 = statement3.executeQuery();
            while (resultSet3.next()) {
                Map<String, String> product_detail = new HashMap<>();

                String title = resultSet3.getString("title");
                String cost = resultSet3.getString("cost");
                String amount = resultSet3.getString("amount");
                String transaction_date = resultSet3.getString("transaction_date");
                String transaction_status = resultSet3.getString("transaction_status");
                String IncomeDeleteStatus = resultSet3.getString("delete_transaction_status");
                String type_transaction = resultSet3.getString("type_transaction");


                product_detail.put("title", String.valueOf(title));
                product_detail.put("cost", String.valueOf(cost));
                product_detail.put("amount", String.valueOf(amount));
                product_detail.put("transaction_date", String.valueOf(transaction_date));
                product_detail.put("transaction_status", String.valueOf(transaction_status));
                product_detail.put("IncomeDeleteStatus", String.valueOf(IncomeDeleteStatus));
                product_detail.put("type", String.valueOf(type_transaction));




                resultMsgExpenses.add(product_detail);


            }



        } catch (SQLException ex) {

            response.put("resultCode", "216");
            response.put("resultMsg", "Error query Data Product");
            response.put("extraPara", "");

            return ResponseEntity.ok(response);
        }

        response.put("resultCode", "200");
//        response.put("productIDShow", "vintage0001");
        response.put("IncomeDetail", resultMsg);
        response.put("ExpensesDetail", resultMsgExpenses);
        return ResponseEntity.ok(response);
    }
}