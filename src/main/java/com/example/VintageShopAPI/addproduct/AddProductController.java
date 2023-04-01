package com.example.VintageShopAPI.addproduct;

import com.example.VintageShopAPI.db.Config;
import com.example.VintageShopAPI.db.DatabaseConnectionPool;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@CrossOrigin
public class AddProductController extends Thread {

    //private static final String PATTERN_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String PATTERN_FORMAT = "HHmmss";
    static Calendar cal = new GregorianCalendar(Locale.ROOT);
    static int current_year = cal.get(Calendar.YEAR);
    static int current_week = cal.get(Calendar.WEEK_OF_YEAR);
    static String current_time = current_year + "" + current_week;
    public static String linux_DIRECTORY = "/home/kyc_Organize/" + current_time + "/";
    // public static String window_DIRECTORY = System.getProperty("C:\\kycimage\\image"+current_week+"\\");
    public static String window_DIRECTORY = "H:\\Data_VINTAGESHOP\\Product\\" + current_time + "\\";

    PreparedStatement pstmt;
    ResultSet rs;
    DatabaseConnectionPool dbConnectionPool;
    //public static String windowpath=directory=C:\Users\lacasoub\myUser\Local\Temp
    Connection connection1;
    public static HttpServletRequest request;


    private void setRequest(HttpServletRequest request) {
        AddProductController.request = request;

    }


    @PostMapping("/v1/addProduct")
    public ResponseEntity<Map<String, Object>> AddProduct(
            @RequestParam(defaultValue = "") String detail_id,
            @RequestParam(defaultValue = "") String transaction_id,
            @RequestParam(defaultValue = "") String category_id,
            @RequestParam(defaultValue = "") String product_id,
            @RequestParam(defaultValue = "") String description_id,
            @RequestParam(defaultValue = "") String image_id,
            @RequestParam(defaultValue = "") String productName,
            @RequestParam(defaultValue = "") String description,
            @RequestParam(defaultValue = "") String detailproduct,
            @RequestParam(defaultValue = "") String amoutProduct,
            @RequestParam(defaultValue = "") Double priceProduct,
            @RequestParam(defaultValue = "") Double purchaseprice,
            @RequestParam(defaultValue = "") MultipartFile image1,
            @RequestParam(defaultValue = "") MultipartFile image2,
            @RequestParam(defaultValue = "") MultipartFile image3,
            @RequestParam(defaultValue = "") MultipartFile image4,
            @RequestParam(defaultValue = "") MultipartFile image5
    ) throws JSONException {

        Map<String, Object> response = new HashMap<>();

        AddProductModel AddProductmodel = new AddProductModel();

        if (

                product_id.equals("")
                        || category_id.equals("")
                        || description.equals("")
                        || image_id.equals("")
                        || description_id.equals("")
                        || detail_id.equals("")
                        || detailproduct.equals("")
        ) {


            response.put("resultCode", "406");
            response.put("resultMsg", "parameter not acceptable");
            response.put("extraPara", "");

            return ResponseEntity.ok(response);

        }


        boolean save_image1 = false;
        boolean save_image2 = false;
        boolean save_image3 = false;
        boolean save_image4 = false;
        boolean save_image5 = false;

        String URL_image1 = "";
        String URL_image2 = "";
        String URL_image3 = "";
        String URL_image4 = "";
        String URL_image5 = "";

        try {

            Uploadimage imgup = new Uploadimage();
            boolean isLinux = System.getProperty("os.name").startsWith("Linux");


            String PATH;
            if (isLinux) {
                PATH = window_DIRECTORY;
            } else {
                PATH = window_DIRECTORY;
            }


            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN_FORMAT).withZone(ZoneId.systemDefault());

            Instant instant = Instant.now();
            String timename = formatter.format(instant);


            String img_image1_name = "image1" + "img_image1_name" + timename + ".png";
            String img_image2_name = "image2" + "img_image2_name" + timename + ".png";
            String img_image3_name = "image3" + "img_image3_name" + timename + ".png";
            String img_image4_name = "image4" + "img_image4_name" + timename + ".png";
            String img_image5_name = "image5" + "img_image5_name" + timename + ".png";

            save_image1 = imgup.image_image1(PATH, img_image1_name, image1);
            save_image2 = imgup.image_image2(PATH, img_image2_name, image2);
            save_image3 = imgup.image_image3(PATH, img_image3_name, image3);
            save_image4 = imgup.image_image4(PATH, img_image4_name, image4);
            save_image5 = imgup.image_image5(PATH, img_image5_name, image5);


            //=====================URL save to db===========================

            //String URL_person_window = ServletUriComponentsBuilder.fromCurrentContextPath().path(PATH+"\\"+ img_person_name).build().toUriString();
            URL_image1 = "/Data_VINTAGESHOP/Product/" + current_time + "/" + img_image1_name;
            URL_image2 = "/Data_VINTAGESHOP/Product/" + current_time + "/" + img_image2_name;
            URL_image3 = "/Data_VINTAGESHOP/Product/" + current_time + "/" + img_image3_name;
            URL_image4 = "/Data_VINTAGESHOP/Product/" + current_time + "/" + img_image4_name;
            URL_image5 = "/Data_VINTAGESHOP/Product/" + current_time + "/" + img_image5_name;


        } catch (Exception ex) {
            response.put("resultCode", "210");
            response.put("resultMsg", "Can not Upload image");
            response.put("extraPara", "");

            return ResponseEntity.ok(response);
        }


        int product_status = 1;
        int transaction_status = 2;
        PreparedStatement pstmt;
        ResultSet rs;
        DatabaseConnectionPool dbConnectionPool = null;
        Connection connection1 = null;
        String strRetunr = null;
        Statement statementtAuth = null;
        ResultSet resultSettAuth = null;
        Connection conntAuth = null;

        String sql1 = "insert into category_product (category_id, product_id) values (?, ?)";
        String sql2 = "insert into pos_image (image_id, image_url_1, image_url_2, image_url_3, image_url_4, image_url_5) values (?, ?, ?, ?, ?, ?)";
        String sql3 = "insert into products_tb (product_id, detail_id, description_id, product_name, image_id,product_status) values (?, ?, ?, ?, ?, ?)";
        String sql4 = "insert into descriptions (description_id, description) values (?, ?)";
        String sql5 = "insert into products_attribute (product_id, image_id, price, detail_id, product_amount, purchase_price) values (?, ?, ?, ?, ?, ?)";
        String sql6 = "insert into details (detail_id, detail) values (?, ?)";
        String sql7 = "insert into transaction_tb (cost, transaction_id, transaction_status, amount, title, transaction_date, delete_transaction_status, update_transaction_date, type_transaction)\n" +
                " values ('" + purchaseprice + "', '" + transaction_id + "', '" + transaction_status + "', '" + amoutProduct + "', '" + productName + "',NOW(), ' 1 ', NOW(), 'Product') ";

        try {
            dbConnectionPool = new DatabaseConnectionPool(
                    Config.driverServr,
                    Config.dburlServr,
                    Config.dbUserNameServr,
                    Config.dbPasswordServr);
            connection1 = dbConnectionPool.getConnection();
            PreparedStatement statement1 = connection1.prepareStatement(sql1);
            PreparedStatement statement2 = connection1.prepareStatement(sql2);
            PreparedStatement statement3 = connection1.prepareStatement(sql3);
            PreparedStatement statement4 = connection1.prepareStatement(sql4);
            PreparedStatement statement5 = connection1.prepareStatement(sql5);
            PreparedStatement statement6 = connection1.prepareStatement(sql6);
            PreparedStatement statement7 = connection1.prepareStatement(sql7);

            statement1.setString(1, category_id);
            statement1.setString(2, product_id);
            int rowsUpdated1 = statement1.executeUpdate();

            statement2.setString(1, image_id);
            statement2.setString(2, URL_image1);
            statement2.setString(3, URL_image2);
            statement2.setString(4, URL_image3);
            statement2.setString(5, URL_image4);
            statement2.setString(6, URL_image5);
            int rowsUpdated2 = statement2.executeUpdate();

            statement3.setString(1, product_id);
            statement3.setString(2, detail_id);
            statement3.setString(3, description_id);
            statement3.setString(4, productName);
            statement3.setString(5, image_id);
            statement3.setInt(6, product_status);
            int rowsUpdated3 = statement3.executeUpdate();

            statement4.setString(1, description_id);
            statement4.setString(2, description);
            int rowsUpdated4 = statement4.executeUpdate();

            statement5.setString(1, product_id);
            statement5.setString(2, image_id);
            statement5.setDouble(3, priceProduct);
            statement5.setString(4, detail_id);
            statement5.setString(5, amoutProduct);
            statement5.setDouble(6, purchaseprice);
            int rowsUpdated5 = statement5.executeUpdate();

            statement6.setString(1, detail_id);
            statement6.setString(2, detailproduct);
            int rowsUpdated6 = statement6.executeUpdate();


            int rowsUpdated7 = statement7.executeUpdate();


            // System.out.println("sql====="+ sql);
            if (rowsUpdated1 > 0 && rowsUpdated2 > 0 && rowsUpdated3 > 0 && rowsUpdated4 > 0 && rowsUpdated5 > 0 && rowsUpdated6 > 0 && rowsUpdated7 > 0) {
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


        response.put("resultCode", "000");
        response.put("resultMsg", "NULL");

        return ResponseEntity.ok(response);
    }
}
