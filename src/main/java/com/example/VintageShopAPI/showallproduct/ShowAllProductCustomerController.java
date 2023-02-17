package com.example.VintageShopAPI.showallproduct;

import com.example.VintageShopAPI.addproduct.AddProductController;
import com.example.VintageShopAPI.db.Config;
import com.example.VintageShopAPI.db.DatabaseConnectionPool;
import org.json.JSONException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class ShowAllProductCustomerController {
    public static HttpServletRequest request;
    DatabaseConnectionPool dbConnectionPool;


    private void setRequest(HttpServletRequest request) {
        AddProductController.request = request;

    }
    @GetMapping("/v1/ShowAllProductCustomer")
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
        String sql = "SELECT CP.product_id, P.product_name, P.detail_id, P.description_id, PA.product_amount, D.detail, DSC.description, PA.price, PI.image_url_1, PI.image_url_2, PI.image_url_3, PI.image_url_4, PI.image_url_5 " + "FROM category_product  CP " + "JOIN products_tb  P ON CP.product_id = P.product_id " + "JOIN products_attribute PA ON P.product_id = PA.product_id " + "JOIN details D ON P.detail_id = D.detail_id " + "JOIN descriptions DSC ON P.description_id = DSC.description_id " + "JOIN pos_image PI ON P.image_id = PI.image_id ";
//=========================== DB ===========================//

//=========================== vintage0001 ===========================//


            try {
                dbConnectionPool = new DatabaseConnectionPool(Config.driverServr, Config.dburlServr, Config.dbUserNameServr, Config.dbPasswordServr);
                connection1 = dbConnectionPool.getConnection();
                PreparedStatement statement = connection1.prepareStatement(sql);

                ResultSet resultSet = statement.executeQuery();


                while (resultSet.next()) {
                    Map<String, String> product_detail = new HashMap<>();

                    String productId = resultSet.getString("product_id");
                    String productName = resultSet.getString("product_name");
                    String detailId = resultSet.getString("detail_id");
                    String descriptionId = resultSet.getString("description_id");
                    String productAmount = resultSet.getString("product_amount");
                    String detail = resultSet.getString("detail");
                    String description = resultSet.getString("description");
                    double price = resultSet.getInt("price");
                    String imageUrl1 = resultSet.getString("image_url_1");


                    product_detail.put("productId", String.valueOf(productId));
                    product_detail.put("productName", String.valueOf(productName));
                    product_detail.put("DetailID", String.valueOf(detailId));
                    product_detail.put("DescriptionID", String.valueOf(descriptionId));
                    product_detail.put("ProductAmount", String.valueOf(productAmount));
                    product_detail.put("Detail", String.valueOf(detail));
                    product_detail.put("Description", String.valueOf(description));
                    product_detail.put("Price", String.valueOf(price));
                    product_detail.put("ImageURL1", String.valueOf(imageUrl1));

                    resultMsg.add(product_detail);

                }
            } catch (SQLException ex) {

                response.put("resultCode", "216");
                response.put("resultMsg", "Error query Data Product");
                response.put("extraPara", "");

                return ResponseEntity.ok(response);
            }

            response.put("resultCode", "200");
            response.put("ProductDetail", resultMsg);
            return ResponseEntity.ok(response);
        }
}
