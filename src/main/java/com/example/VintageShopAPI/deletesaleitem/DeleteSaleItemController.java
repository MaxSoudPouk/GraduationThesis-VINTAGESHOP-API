package com.example.VintageShopAPI.deletesaleitem;

import com.example.VintageShopAPI.db.Config;
import com.example.VintageShopAPI.db.DatabaseConnectionPool;
import com.example.VintageShopAPI.security.JWT_Security_Encode_Decode_Java;
import org.json.JSONException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@CrossOrigin
public class DeleteSaleItemController {

    @PostMapping("/v1/DeleteSaleItem")
    public ResponseEntity<Map<String, Object>> AddProduct(
            @RequestParam("OrderID") String OrderID,
            @RequestParam("CustomerID") String CustomerID,
            @RequestParam("token") String token,
            @RequestParam("user_Email") String user_Email,
            @RequestParam("user_ID") String user_ID

    ) throws JSONException {

        Map<String, Object> response = new HashMap<>();


        if (OrderID.equals("") || CustomerID.equals("") || token.equals("")|| user_Email.equals("")|| user_ID.equals("")) {


            response.put("resultCode", "406");
            response.put("resultMsg", "parameter not acceptable");
            response.put("extraPara", "");

            return ResponseEntity.ok(response);

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
        String sql = "UPDATE order_tb\n" +
                "SET deleted_status = '0'\n" +
                "WHERE order_id = '" + OrderID + "' AND customer_id = '" + CustomerID + "';\n";


//=========================== DB ===========================//
        //validate JWT
        boolean jwtencoderesult = false;

        JWT_Security_Encode_Decode_Java encode_Decode_Java = new JWT_Security_Encode_Decode_Java();
        jwtencoderesult = encode_Decode_Java.deCodeJWT_validate(token, user_Email, user_ID);

        if (jwtencoderesult) {

        try {
            dbConnectionPool = new
                    DatabaseConnectionPool(Config.driverServr,
                    Config.dburlServr,
                    Config.dbUserNameServr,
                    Config.dbPasswordServr);
            connection1 = dbConnectionPool.getConnection();
            PreparedStatement statement = connection1.prepareStatement(sql);

            int deletedRows = statement.executeUpdate();

            if (deletedRows > 0) {
                response.put("resultCode", "200");
                response.put("resultMsg", "OK, success");
                response.put("Delete_Status", "OK");
                response.put("date", datepro111);

                return ResponseEntity.ok(response);
            } else {
                response.put("resultCode", "216");
                response.put("resultMsg", "Error query Data Product");
                response.put("extraPara", "");

                return ResponseEntity.ok(response);
            }

        } catch (SQLException ex) {

            response.put("resultCode", "216");
            response.put("resultMsg", "Error query Data Product");
            response.put("extraPara", "");

            return ResponseEntity.ok(response);
        }
    }
            else {

            response.put("resultCode", "498");
            response.put("ResultMsg", "Invalid_Token");
            return ResponseEntity.ok(response);

        }
    }
}
