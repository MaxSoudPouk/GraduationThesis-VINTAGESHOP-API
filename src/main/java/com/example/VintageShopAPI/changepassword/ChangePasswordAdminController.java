package com.example.VintageShopAPI.changepassword;

import com.example.VintageShopAPI.db.Config;
import com.example.VintageShopAPI.db.DatabaseConnectionPool;
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
public class ChangePasswordAdminController {
    public static HttpServletRequest request;
    DatabaseConnectionPool dbConnectionPool;


    private void setRequest(HttpServletRequest request) {
        ChangePasswordAdminController.request = request;

    }

    @PostMapping("/v1/ChangePasswordAdmin")
    public ResponseEntity<Map<String, Object>> AddProduct(
            @RequestParam(defaultValue = "") String Old_Password,
            @RequestParam(defaultValue = "") String New_Password,
            @RequestParam(defaultValue = "") String Confirm_Password,
            @RequestParam(defaultValue = "") String customer_ID,
            @RequestParam(defaultValue = "") String token,
            @RequestParam(defaultValue = "") String customer_email

    ) throws JSONException {

        Map<String, Object> response = new HashMap<>();
        List<Map<String, String>> resultMsg = new ArrayList<>();


        if (
                Old_Password.equals("") ||
                        customer_ID.equals("") ||
                        customer_email.equals("") ||
                        New_Password.equals("") ||
                        Confirm_Password.equals("")) {


            response.put("resultCode", "406");
            response.put("resultMsg", "parameter not acceptable");
            response.put("extraPara", "");

            return ResponseEntity.ok(response);

        }
        if (Confirm_Password.equals(New_Password)) {


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
            String sql = "UPDATE admin_tb SET admin_password = ?, admin_date_time = ? WHERE admin_id = ? and admin_password = ?";
            String sqlpassword = "SELECT admin_password from admin_tb where admin_id = '"
                    + customer_ID + "'  LIMIT 1";
            String customer_qry_password = null;


            //validate JWT
            boolean jwtencoderesult = false;

            JWT_Security_Encode_Decode_Java encode_Decode_Java = new JWT_Security_Encode_Decode_Java();
            jwtencoderesult = encode_Decode_Java.deCodeJWT_validate(token, customer_email, customer_ID);

            if (jwtencoderesult) {

                try {

                    dbConnectionPool = new DatabaseConnectionPool(Config.driverServr, Config.dburlServr, Config.dbUserNameServr,
                            Config.dbPasswordServr);
                    connection1 = dbConnectionPool.getConnection();
                    pstmt = connection1.prepareStatement(sqlpassword);
                    rs = (ResultSet) pstmt.executeQuery();

                    if (rs != null && rs.next()) {
                        customer_qry_password = rs.getString("admin_password");
                        if (customer_qry_password.equals(Old_Password)) {
                            try {
                                dbConnectionPool = new
                                        DatabaseConnectionPool(Config.driverServr,
                                        Config.dburlServr,
                                        Config.dbUserNameServr,
                                        Config.dbPasswordServr);
                                connection1 = dbConnectionPool.getConnection();
                                PreparedStatement statement = connection1.prepareStatement(sql);

                                statement.setString(1, New_Password);
                                statement.setString(2, datepro111);
                                statement.setString(3, customer_ID);
                                statement.setString(4, Old_Password);

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
                        } else {
                            response.put("resultCode", "209");
                            response.put("resultMsg", "password and old password the same");
                            response.put("extraPara", "");

                            return ResponseEntity.ok(response);
                        }

                    }

                } catch (Exception e) {
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
        } else {
            response.put("resultCode", "210");
            response.put("resultMsg", "new password and confirm password not sam ");
            response.put("extraPara", "");

            return ResponseEntity.ok(response);
        }

        response.put("resultCode", "000");
        response.put("resultMsg", "NULL");

        return ResponseEntity.ok(response);
    }
}
