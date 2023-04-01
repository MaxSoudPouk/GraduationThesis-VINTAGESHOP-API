package com.example.VintageShopAPI.addorthertransaction;

import com.example.VintageShopAPI.customermessage.CustomerMessageController;
import com.example.VintageShopAPI.db.Config;
import com.example.VintageShopAPI.db.DatabaseConnectionPool;
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
public class AddOtherTransactionController {
    public static HttpServletRequest request;
    DatabaseConnectionPool dbConnectionPool;


    private void setRequest(HttpServletRequest request) {
        CustomerMessageController.request = request;

    }
    @PostMapping("/v1/AddOtherTransaction")
    public ResponseEntity<Map<String, Object>> AddProduct(
            @RequestParam("title") String title,
            @RequestParam("amount") String amount,
            @RequestParam("transaction_id") String transaction_id,
            @RequestParam("cost") String cost,
            @RequestParam("transaction_status") String transaction_status

    ) throws JSONException {
        Map<String, Object> response = new HashMap<>();
        /*
        * transaction_status = 1 ====> Income
        * transaction_status = 2 ====> Expenses
        */

        if (
                title.equals("")||

                        transaction_status.equals("")||
                        transaction_id.equals("")||
                        amount.equals("")
                        ){


            response.put("resultCode", "406");
            response.put("resultMsg", "parameter not acceptable");
            response.put("extraPara", "");

            return ResponseEntity.ok(response);

        }

        int stat = 0;

        if (transaction_status.equals("Income")){
            stat = 1;
        }
        if (transaction_status.equals("Expenses")){
            stat = 2;
        }
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
        String sql = "insert into transaction_tb (cost,transaction_id, transaction_status, amount, title, transaction_date, delete_transaction_status, update_transaction_date, type_transaction)\n" +
                "                   values ('"+cost +"','"+transaction_id+"', '"+stat+"', '"+amount+"', '"+title+"',NOW(),' 1 ', NOW(), 'Other')\n";
        try {
            dbConnectionPool = new
                    DatabaseConnectionPool(Config.driverServr,
                    Config.dburlServr,
                    Config.dbUserNameServr,
                    Config.dbPasswordServr);
            connection1 = dbConnectionPool.getConnection();
            PreparedStatement statement = connection1.prepareStatement(sql);


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

        response.put("resultCode", "000");
        response.put("resultMsg", "NULL");

        return ResponseEntity.ok(response);
    }
}