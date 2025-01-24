package com.example.VintageShopAPI.showproductadmin;

import com.example.VintageShopAPI.addproduct.AddProductController;
import com.example.VintageShopAPI.db.Config;
import com.example.VintageShopAPI.db.DatabaseConnectionPool;
import com.example.VintageShopAPI.security.JWT_Security_Encode_Decode_Java;
import org.json.JSONException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class ShowProductAdminController {
    public static HttpServletRequest request;
    DatabaseConnectionPool dbConnectionPool;


    private void setRequest(HttpServletRequest request) {
        ShowProductAdminController.request = request;

    }

    @PostMapping("/v1/showProduct")
    public ResponseEntity<Map<String, Object>> AddProduct(
            @RequestParam(defaultValue = "") String showProductname,
            @RequestParam(defaultValue = "") String token,
            @RequestParam(defaultValue = "") String customer_ID,
            @RequestParam(defaultValue = "") String customer_email

    ) throws JSONException {
        Map<String, Object> response = new HashMap<>();

        List<Map<String, String>> resultMsg = new ArrayList<>();

        if (showProductname.equals("") || token.equals("") || customer_ID.equals("") || customer_email.equals("")) {


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
        String sql = "SELECT CP.product_id, P.product_name, P.detail_id, P.description_id, PA.product_amount, D.detail, DSC.description, PA.price, PI.image_url_1, PI.image_url_2, PI.image_url_3, PI.image_url_4, PI.image_url_5 " + "FROM category_product  CP " + "JOIN products_tb  P ON CP.product_id = P.product_id " + "JOIN products_attribute PA ON P.product_id = PA.product_id " + "JOIN details D ON P.detail_id = D.detail_id " + "JOIN descriptions DSC ON P.description_id = DSC.description_id " + "JOIN pos_image PI ON P.image_id = PI.image_id " + "WHERE CP.category_id = ?";
//=========================== DB ===========================//

        //validate JWT
        boolean jwtencoderesult = false;

        JWT_Security_Encode_Decode_Java encode_Decode_Java = new JWT_Security_Encode_Decode_Java();
        jwtencoderesult = encode_Decode_Java.deCodeJWT_validate(token, customer_email, customer_ID);

        if (jwtencoderesult) {

//=========================== vintage0001 ===========================//

            if (showProductname.equals("vintage0001")) {
                try {
                    dbConnectionPool = new DatabaseConnectionPool(Config.driverServr, Config.dburlServr, Config.dbUserNameServr, Config.dbPasswordServr);
                    connection1 = dbConnectionPool.getConnection();
                    PreparedStatement statement = connection1.prepareStatement(sql);

                    statement.setString(1, showProductname);
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
                        String imageUrl2 = resultSet.getString("image_url_2");
                        String imageUrl3 = resultSet.getString("image_url_3");
                        String imageUrl4 = resultSet.getString("image_url_4");
                        String imageUrl5 = resultSet.getString("image_url_5");


                        product_detail.put("productId", String.valueOf(productId));
                        product_detail.put("productName", String.valueOf(productName));
                        product_detail.put("DetailID", String.valueOf(detailId));
                        product_detail.put("DescriptionID", String.valueOf(descriptionId));
                        product_detail.put("ProductAmount", String.valueOf(productAmount));
                        product_detail.put("Detail", String.valueOf(detail));
                        product_detail.put("Description", String.valueOf(description));
                        product_detail.put("Price", String.valueOf(price));
                        product_detail.put("ImageURL1", String.valueOf(imageUrl1));
                        product_detail.put("ImageURL2", String.valueOf(imageUrl2));
                        product_detail.put("ImageURL3", String.valueOf(imageUrl3));
                        product_detail.put("ImageURL4", String.valueOf(imageUrl4));
                        product_detail.put("ImageURL5", String.valueOf(imageUrl5));

                        resultMsg.add(product_detail);


                    }
                } catch (SQLException ex) {

                    response.put("resultCode", "216");
                    response.put("resultMsg", "Error query Data Product");
                    response.put("extraPara", "");

                    return ResponseEntity.ok(response);
                }

                response.put("resultCode", "200");
                response.put("showProductname", "vintage0001");
                response.put("ProductDetail", resultMsg);
                return ResponseEntity.ok(response);
            }
//=========================== vintage0001 END ===========================//
//=========================== vintage0002 ===========================//

            else if (showProductname.equals("vintage0002")) {
                try {
                    dbConnectionPool = new DatabaseConnectionPool(Config.driverServr, Config.dburlServr, Config.dbUserNameServr, Config.dbPasswordServr);
                    connection1 = dbConnectionPool.getConnection();
                    PreparedStatement statement = connection1.prepareStatement(sql);

                    statement.setString(1, showProductname);
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
                        String imageUrl2 = resultSet.getString("image_url_2");
                        String imageUrl3 = resultSet.getString("image_url_3");
                        String imageUrl4 = resultSet.getString("image_url_4");
                        String imageUrl5 = resultSet.getString("image_url_5");


                        product_detail.put("productId", String.valueOf(productId));
                        product_detail.put("productName", String.valueOf(productName));
                        product_detail.put("DetailID", String.valueOf(detailId));
                        product_detail.put("DescriptionID", String.valueOf(descriptionId));
                        product_detail.put("ProductAmount", String.valueOf(productAmount));
                        product_detail.put("Detail", String.valueOf(detail));
                        product_detail.put("Description", String.valueOf(description));
                        product_detail.put("Price", String.valueOf(price));
                        product_detail.put("ImageURL1", String.valueOf(imageUrl1));
                        product_detail.put("ImageURL2", String.valueOf(imageUrl2));
                        product_detail.put("ImageURL3", String.valueOf(imageUrl3));
                        product_detail.put("ImageURL4", String.valueOf(imageUrl4));
                        product_detail.put("ImageURL5", String.valueOf(imageUrl5));

                        resultMsg.add(product_detail);


                    }
                } catch (SQLException ex) {

                    response.put("resultCode", "216");
                    response.put("resultMsg", "Error query Data Product");
                    response.put("extraPara", "");

                    return ResponseEntity.ok(response);
                }

                response.put("resultCode", "200");
                response.put("showProductname", "vintage0002");
                response.put("ProductDetail", resultMsg);
                return ResponseEntity.ok(response);
            }
//=========================== vintage0002 END ===========================//
//=========================== vintage0003 ===========================//

            else if (showProductname.equals("vintage0003")) {
                try {
                    dbConnectionPool = new DatabaseConnectionPool(Config.driverServr, Config.dburlServr, Config.dbUserNameServr, Config.dbPasswordServr);
                    connection1 = dbConnectionPool.getConnection();
                    PreparedStatement statement = connection1.prepareStatement(sql);

                    statement.setString(1, showProductname);
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
                        String imageUrl2 = resultSet.getString("image_url_2");
                        String imageUrl3 = resultSet.getString("image_url_3");
                        String imageUrl4 = resultSet.getString("image_url_4");
                        String imageUrl5 = resultSet.getString("image_url_5");


                        product_detail.put("productId", String.valueOf(productId));
                        product_detail.put("productName", String.valueOf(productName));
                        product_detail.put("DetailID", String.valueOf(detailId));
                        product_detail.put("DescriptionID", String.valueOf(descriptionId));
                        product_detail.put("ProductAmount", String.valueOf(productAmount));
                        product_detail.put("Detail", String.valueOf(detail));
                        product_detail.put("Description", String.valueOf(description));
                        product_detail.put("Price", String.valueOf(price));
                        product_detail.put("ImageURL1", String.valueOf(imageUrl1));
                        product_detail.put("ImageURL2", String.valueOf(imageUrl2));
                        product_detail.put("ImageURL3", String.valueOf(imageUrl3));
                        product_detail.put("ImageURL4", String.valueOf(imageUrl4));
                        product_detail.put("ImageURL5", String.valueOf(imageUrl5));

                        resultMsg.add(product_detail);


                    }
                } catch (SQLException ex) {

                    response.put("resultCode", "216");
                    response.put("resultMsg", "Error query Data Product");
                    response.put("extraPara", "");

                    return ResponseEntity.ok(response);
                }

                response.put("resultCode", "200");
                response.put("showProductname", "vintage0003");
                response.put("ProductDetail", resultMsg);
                return ResponseEntity.ok(response);
            }
//=========================== vintage0003 END ===========================//
//=========================== vintage0004 ===========================//

            else if (showProductname.equals("vintage0004")) {
                try {
                    dbConnectionPool = new DatabaseConnectionPool(Config.driverServr, Config.dburlServr, Config.dbUserNameServr, Config.dbPasswordServr);
                    connection1 = dbConnectionPool.getConnection();
                    PreparedStatement statement = connection1.prepareStatement(sql);

                    statement.setString(1, showProductname);
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
                        String imageUrl2 = resultSet.getString("image_url_2");
                        String imageUrl3 = resultSet.getString("image_url_3");
                        String imageUrl4 = resultSet.getString("image_url_4");
                        String imageUrl5 = resultSet.getString("image_url_5");


                        product_detail.put("productId", String.valueOf(productId));
                        product_detail.put("productName", String.valueOf(productName));
                        product_detail.put("DetailID", String.valueOf(detailId));
                        product_detail.put("DescriptionID", String.valueOf(descriptionId));
                        product_detail.put("ProductAmount", String.valueOf(productAmount));
                        product_detail.put("Detail", String.valueOf(detail));
                        product_detail.put("Description", String.valueOf(description));
                        product_detail.put("Price", String.valueOf(price));
                        product_detail.put("ImageURL1", String.valueOf(imageUrl1));
                        product_detail.put("ImageURL2", String.valueOf(imageUrl2));
                        product_detail.put("ImageURL3", String.valueOf(imageUrl3));
                        product_detail.put("ImageURL4", String.valueOf(imageUrl4));
                        product_detail.put("ImageURL5", String.valueOf(imageUrl5));

                        resultMsg.add(product_detail);


                    }
                } catch (SQLException ex) {

                    response.put("resultCode", "216");
                    response.put("resultMsg", "Error query Data Product");
                    response.put("extraPara", "");

                    return ResponseEntity.ok(response);
                }

                response.put("resultCode", "200");
                response.put("showProductname", "vintage0004");
                response.put("ProductDetail", resultMsg);
                return ResponseEntity.ok(response);
            }
//=========================== vintage0004 END ===========================//
//=========================== vintage0005 ===========================//

            else if (showProductname.equals("vintage0005")) {
                try {
                    dbConnectionPool = new DatabaseConnectionPool(Config.driverServr, Config.dburlServr, Config.dbUserNameServr, Config.dbPasswordServr);
                    connection1 = dbConnectionPool.getConnection();
                    PreparedStatement statement = connection1.prepareStatement(sql);

                    statement.setString(1, showProductname);
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
                        String imageUrl2 = resultSet.getString("image_url_2");
                        String imageUrl3 = resultSet.getString("image_url_3");
                        String imageUrl4 = resultSet.getString("image_url_4");
                        String imageUrl5 = resultSet.getString("image_url_5");


                        product_detail.put("productId", String.valueOf(productId));
                        product_detail.put("productName", String.valueOf(productName));
                        product_detail.put("DetailID", String.valueOf(detailId));
                        product_detail.put("DescriptionID", String.valueOf(descriptionId));
                        product_detail.put("ProductAmount", String.valueOf(productAmount));
                        product_detail.put("Detail", String.valueOf(detail));
                        product_detail.put("Description", String.valueOf(description));
                        product_detail.put("Price", String.valueOf(price));
                        product_detail.put("ImageURL1", String.valueOf(imageUrl1));
                        product_detail.put("ImageURL2", String.valueOf(imageUrl2));
                        product_detail.put("ImageURL3", String.valueOf(imageUrl3));
                        product_detail.put("ImageURL4", String.valueOf(imageUrl4));
                        product_detail.put("ImageURL5", String.valueOf(imageUrl5));

                        resultMsg.add(product_detail);


                    }
                } catch (SQLException ex) {

                    response.put("resultCode", "216");
                    response.put("resultMsg", "Error query Data Product");
                    response.put("extraPara", "");

                    return ResponseEntity.ok(response);
                }

                response.put("resultCode", "200");
                response.put("showProductname", "vintage0005");
                response.put("ProductDetail", resultMsg);
                return ResponseEntity.ok(response);
            }
//=========================== vintage0005 END ===========================//
//=========================== vintage0006 ===========================//

            else if (showProductname.equals("vintage0006")) {
                try {
                    dbConnectionPool = new DatabaseConnectionPool(Config.driverServr, Config.dburlServr, Config.dbUserNameServr, Config.dbPasswordServr);
                    connection1 = dbConnectionPool.getConnection();
                    PreparedStatement statement = connection1.prepareStatement(sql);

                    statement.setString(1, showProductname);
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
                        String imageUrl2 = resultSet.getString("image_url_2");
                        String imageUrl3 = resultSet.getString("image_url_3");
                        String imageUrl4 = resultSet.getString("image_url_4");
                        String imageUrl5 = resultSet.getString("image_url_5");


                        product_detail.put("productId", String.valueOf(productId));
                        product_detail.put("productName", String.valueOf(productName));
                        product_detail.put("DetailID", String.valueOf(detailId));
                        product_detail.put("DescriptionID", String.valueOf(descriptionId));
                        product_detail.put("ProductAmount", String.valueOf(productAmount));
                        product_detail.put("Detail", String.valueOf(detail));
                        product_detail.put("Description", String.valueOf(description));
                        product_detail.put("Price", String.valueOf(price));
                        product_detail.put("ImageURL1", String.valueOf(imageUrl1));
                        product_detail.put("ImageURL2", String.valueOf(imageUrl2));
                        product_detail.put("ImageURL3", String.valueOf(imageUrl3));
                        product_detail.put("ImageURL4", String.valueOf(imageUrl4));
                        product_detail.put("ImageURL5", String.valueOf(imageUrl5));

                        resultMsg.add(product_detail);


                    }
                } catch (SQLException ex) {

                    response.put("resultCode", "216");
                    response.put("resultMsg", "Error query Data Product");
                    response.put("extraPara", "");

                    return ResponseEntity.ok(response);
                }

                response.put("resultCode", "200");
                response.put("showProductname", "vintage0006");
                response.put("ProductDetail", resultMsg);
                return ResponseEntity.ok(response);
            }
//=========================== vintage0006 END ===========================//
//=========================== vintage0007 ===========================//

            else if (showProductname.equals("vintage0007")) {
                try {
                    dbConnectionPool = new DatabaseConnectionPool(Config.driverServr, Config.dburlServr, Config.dbUserNameServr, Config.dbPasswordServr);
                    connection1 = dbConnectionPool.getConnection();
                    PreparedStatement statement = connection1.prepareStatement(sql);

                    statement.setString(1, showProductname);
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
                        String imageUrl2 = resultSet.getString("image_url_2");
                        String imageUrl3 = resultSet.getString("image_url_3");
                        String imageUrl4 = resultSet.getString("image_url_4");
                        String imageUrl5 = resultSet.getString("image_url_5");


                        product_detail.put("productId", String.valueOf(productId));
                        product_detail.put("productName", String.valueOf(productName));
                        product_detail.put("DetailID", String.valueOf(detailId));
                        product_detail.put("DescriptionID", String.valueOf(descriptionId));
                        product_detail.put("ProductAmount", String.valueOf(productAmount));
                        product_detail.put("Detail", String.valueOf(detail));
                        product_detail.put("Description", String.valueOf(description));
                        product_detail.put("Price", String.valueOf(price));
                        product_detail.put("ImageURL1", String.valueOf(imageUrl1));
                        product_detail.put("ImageURL2", String.valueOf(imageUrl2));
                        product_detail.put("ImageURL3", String.valueOf(imageUrl3));
                        product_detail.put("ImageURL4", String.valueOf(imageUrl4));
                        product_detail.put("ImageURL5", String.valueOf(imageUrl5));

                        resultMsg.add(product_detail);


                    }
                } catch (SQLException ex) {

                    response.put("resultCode", "216");
                    response.put("resultMsg", "Error query Data Product");
                    response.put("extraPara", "");

                    return ResponseEntity.ok(response);
                }

                response.put("resultCode", "200");
                response.put("showProductname", "vintage0007");
                response.put("ProductDetail", resultMsg);
                return ResponseEntity.ok(response);
            }
//=========================== vintage0007 END ===========================//
        } else {

            response.put("resultCode", "498");
            response.put("ResultMsg", "Invalid_Token");
            return ResponseEntity.ok(response);

        }


        response.put("resultCode", "201");
        response.put("resultMsg", "Don't have " + showProductname + " request");
        return ResponseEntity.ok(response);
    }
}
