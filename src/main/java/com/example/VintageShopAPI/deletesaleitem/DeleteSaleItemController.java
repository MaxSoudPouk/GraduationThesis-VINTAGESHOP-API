//package com.example.VintageShopAPI.deletesaleitem;
//
//import com.example.VintageShopAPI.db.Config;
//import com.example.VintageShopAPI.db.DatabaseConnectionPool;
//import org.json.JSONException;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import java.sql.*;
//import java.text.SimpleDateFormat;
//import java.util.*;
//
//public class DeleteSaleItemController {
//
//    @PostMapping("/v1/DeleteSaleItem")
//    public ResponseEntity<Map<String, Object>> AddProduct(
//            @RequestParam(defaultValue = "") String OrderID,
//            @RequestParam(defaultValue = "") String CustomerID
//
//    ) throws JSONException {
//
//        Map<String, Object> response = new HashMap<>();
//
//        List<Map<String, String>> resultMsg = new ArrayList<>();
//
//
//        if (OrderID.equals("") || CustomerID.equals("")) {
//
//
//            response.put("resultCode", "406");
//            response.put("resultMsg", "parameter not acceptable");
//            response.put("extraPara", "");
//
//            return ResponseEntity.ok(response);
//
//        }
////====================== Create date ======================//
//
//        Calendar currentDate111 = Calendar.getInstance();
//        SimpleDateFormat formatter111 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        formatter111.setTimeZone(TimeZone.getTimeZone("Asia/Bangkok"));
//        String dateNow111 = formatter111.format(currentDate111.getTime());
//        String datepro111 = dateNow111.toString();
//
////====================== END Create date ======================//
////=========================== DB ===========================//
//        PreparedStatement pstmt;
//        ResultSet rs;
//        DatabaseConnectionPool dbConnectionPool = null;
//        Connection connection1 = null;
//        String strRetunr = null;
//        Statement statementtAuth = null;
//        ResultSet resultSettAuth = null;
//        Connection conntAuth = null;
//        String sql = "DELETE CP, P, PA, D, DSC, PI " +
//                "FROM category_product CP " +
//                "JOIN products_tb P ON CP.product_id = P.product_id " +
//                "JOIN products_attribute PA ON P.product_id = PA.product_id " +
//                "JOIN details D ON P.detail_id = D.detail_id " +
//                "JOIN descriptions DSC ON P.description_id = DSC.description_id " +
//                "JOIN pos_image PI ON P.image_id = PI.image_id " +
//                "WHERE CP.product_id = ? ";
//
//
////=========================== DB ===========================//
//
//        try {
//            dbConnectionPool = new
//                    DatabaseConnectionPool(Config.driverServr,
//                    Config.dburlServr,
//                    Config.dbUserNameServr,
//                    Config.dbPasswordServr);
//            connection1 = dbConnectionPool.getConnection();
//            PreparedStatement statement = connection1.prepareStatement(sql);
//
//            statement.setString(1, ProductID);
//            int deletedRows = statement.executeUpdate();
//
//
//            response.put("resultCode", "200");
//            response.put("resultMsg", "OK, success");
//            response.put("productID", ProductID);
//            response.put("Delete_Status", "OK");
//            response.put("date", datepro111);
//
//            return ResponseEntity.ok(response);
//
//
//        } catch (SQLException ex) {
//
//            response.put("resultCode", "216");
//            response.put("resultMsg", "Error query Data Product");
//            response.put("extraPara", "");
//
//            return ResponseEntity.ok(response);
//        }
//    }
//}
