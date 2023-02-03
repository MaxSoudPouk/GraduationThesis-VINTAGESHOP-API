package com.example.VintageShopAPI.register;

import com.example.VintageShopAPI.addproduct.AddProductController;
import com.example.VintageShopAPI.addproduct.Uploadimage;
import com.example.VintageShopAPI.db.Config;
import com.example.VintageShopAPI.db.DatabaseConnectionPool;
import org.json.JSONException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.sql.*;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@CrossOrigin
public class RegisterController {


    //private static final String PATTERN_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String PATTERN_FORMAT = "HHmmss";
    static Calendar cal = new GregorianCalendar(Locale.ROOT);
    static int current_year = cal.get(Calendar.YEAR);
    static int current_week = cal.get(Calendar.WEEK_OF_YEAR);
    static String current_time = current_year + "" + current_week;
    public static String linux_DIRECTORY = "/home/kyc_Organize/" + current_time + "/";
    // public static String window_DIRECTORY = System.getProperty("C:\\kycimage\\image"+current_week+"\\");
    public static String window_DIRECTORY = "H:\\Data_VINTAGESHOP\\Profile\\" + current_time + "\\";

    PreparedStatement pstmt;
    ResultSet rs;
    DatabaseConnectionPool dbConnectionPool;
    //public static String windowpath=directory=C:\Users\lacasoub\myUser\Local\Temp
    Connection connection1;
    public static HttpServletRequest request;


    private void setRequest(HttpServletRequest request) {
        AddProductController.request = request;

    }


    @PostMapping("/v1/register")
    public ResponseEntity<Map<String, Object>> AddProduct(
            @RequestParam(defaultValue = "") String customer_id,
            @RequestParam(defaultValue = "") String customer_first_name,
            @RequestParam(defaultValue = "") String customer_last_name,
            @RequestParam(defaultValue = "") String customer_email,
            @RequestParam(defaultValue = "") String customer_password,
            @RequestParam(defaultValue = "") String customer_phonenumber,
            @RequestParam(defaultValue = "") String customer_address,
            @RequestParam(defaultValue = "") MultipartFile customer_url_image
            ) throws JSONException {

        Map<String, Object> response = new HashMap<>();

        System.out.println("customer_url_image ===== " + customer_url_image);


        if (
                customer_id.equals("")
                        || customer_first_name.equals("")
                        || customer_last_name.equals("")
                        || customer_email.equals("")
                        || customer_password.equals("")
                        || customer_phonenumber.equals("")
                        || customer_address.equals("")
        ) {

            response.put("resultCode", "406");
            response.put("resultMsg", "parameter not acceptable");
            response.put("extraPara", "");

            return ResponseEntity.ok(response);

        }


        if (customer_url_image.isEmpty()) {

            String URL_image1 = "";

            PreparedStatement pstmt;
            ResultSet rs;
            DatabaseConnectionPool dbConnectionPool = null;
            Connection connection1 = null;
            String strRetunr = null;
            Statement statementtAuth = null;
            ResultSet resultSettAuth = null;
            Connection conntAuth = null;

            String sql1 = "insert into customer_tb (customer_id, customer_first_name, customer_last_name, customer_password, customer_email, customer_phonenumber, customer_address, customer_url_image) " +
                    "values (?, ?, ?, ?, ?, ?, ?, ?); ";


            try {
                dbConnectionPool = new DatabaseConnectionPool(
                        Config.driverServr,
                        Config.dburlServr,
                        Config.dbUserNameServr,
                        Config.dbPasswordServr);
                connection1 = dbConnectionPool.getConnection();
                PreparedStatement statement = connection1.prepareStatement(sql1);

                statement.setString(1, customer_id);
                statement.setString(2, customer_first_name);
                statement.setString(3, customer_last_name);
                statement.setString(4, customer_password);
                statement.setString(5, customer_email);
                statement.setString(6, customer_phonenumber);
                statement.setString(7, customer_address);
                statement.setString(8, URL_image1);
                int rowsUpdated = statement.executeUpdate();



                // System.out.println("sql====="+ sql);
                if (rowsUpdated > 0) {
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



    }else {


            boolean save_image1 = false;


            String URL_image1 = "";


            try {

                UploadimageCustomer imgup = new UploadimageCustomer();
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


                save_image1 = imgup.image_csutomer(PATH, img_image1_name, customer_url_image);


                //=====================URL save to db===========================

                //String URL_person_window = ServletUriComponentsBuilder.fromCurrentContextPath().path(PATH+"\\"+ img_person_name).build().toUriString();
                URL_image1 = "/Data_VINTAGESHOP/Profile/" + current_time + "/" + img_image1_name;


            } catch (Exception ex) {
                response.put("resultCode", "210");
                response.put("resultMsg", "Can not Upload image");
                response.put("extraPara", "");

                return ResponseEntity.ok(response);
            }




        PreparedStatement pstmt;
        ResultSet rs;
        DatabaseConnectionPool dbConnectionPool = null;
        Connection connection1 = null;
        String strRetunr = null;
        Statement statementtAuth = null;
        ResultSet resultSettAuth = null;
        Connection conntAuth = null;

        String sql = "INSERT INTO customer_tb (customer_id, customer_first_name, customer_last_name, customer_password, customer_email," +
                    " customer_phonenumber, customer_address, customer_url_image) values (?, ?, ?, ?, ?, ?, ?, ?)";


        try {
            dbConnectionPool = new DatabaseConnectionPool(
                    Config.driverServr,
                    Config.dburlServr,
                    Config.dbUserNameServr,
                    Config.dbPasswordServr);
            connection1 = dbConnectionPool.getConnection();
            PreparedStatement statement = connection1.prepareStatement(sql);



            statement.setString(1, customer_id);
            statement.setString(2, customer_first_name);
            statement.setString(3, customer_last_name);
            statement.setString(4, customer_password);
            statement.setString(5, customer_email);
            statement.setString(6, customer_phonenumber);
            statement.setString(7, customer_address);
            statement.setString(8, URL_image1);
            int rowsUpdated = statement.executeUpdate();





            // System.out.println("sql====="+ sql);
            if (rowsUpdated > 0) {
                // System.out.println("An existing user was updated successfully!");

                response.put("resultCode", "200");
                response.put("resultMsg", "OK, success");
                response.put("customer_first_name", customer_first_name);
                response.put("customer_last_name", customer_last_name);

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
        }


        response.put("resultCode", "000");
        response.put("resultMsg", "NULL");

        return ResponseEntity.ok(response);
    }
}
