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
public class AddProductController extends Thread{

    //private static final String PATTERN_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String PATTERN_FORMAT = "HHmmss";
    static Calendar cal = new GregorianCalendar(Locale.ROOT);
    static int current_year = cal.get(Calendar.YEAR);
    static int current_week = cal.get(Calendar.WEEK_OF_YEAR);
    static String current_time = current_year + "" + current_week;
    public static String linux_DIRECTORY = "/home/kyc_Organize/" + current_time + "/";
    // public static String window_DIRECTORY = System.getProperty("C:\\kycimage\\image"+current_week+"\\");
    public static String window_DIRECTORY = "C:\\kyc_Organize\\" + current_time + "\\";

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
            @RequestParam(defaultValue = "") String idproduct,
            @RequestParam(defaultValue = "") String productName,
            @RequestParam(defaultValue = "") String description,
            @RequestParam(defaultValue = "") String detailproduct,
            @RequestParam(defaultValue = "") String amoutProduct,
            @RequestParam(defaultValue = "") String priceProduct,
            @RequestParam(defaultValue = "") MultipartFile image1,
            @RequestParam(defaultValue = "") MultipartFile image2,
            @RequestParam(defaultValue = "") MultipartFile image3,
            @RequestParam(defaultValue = "") MultipartFile image4,
            @RequestParam(defaultValue = "") MultipartFile image5
    )throws JSONException{

        Map<String, Object> response = new HashMap<>();

        AddProductModel AddProductmodel = new AddProductModel();


        if (
              idproduct.equals("")
                        || description.equals("")
                        || productName.equals("")
                        || detailproduct.equals("")
//                        || image2.isEmpty()
//                        || image3.isEmpty()
//                        || image4.isEmpty()
//                        || image5.isEmpty()
        ) {


            response.put("resultCode", "406");
            response.put("resultMsg", "parameter not acceptable");
            response.put("extraPara", "");

            return ResponseEntity.ok(response);

        }

       System.out.println("image1 ======================" + image1.getClass().getName());




//
//        //                    //=======================End check status================
//
//        boolean save_image1 = false;
//        boolean save_image2 = false;
//        boolean save_image3 = false;
//        boolean save_image4 = false;
//        boolean save_image5 = false;
//
//        String URL_person_linux = "";
//        String URL_doc_linux = "";
//        String URL_sim_linux = "";
//        String URL_org_linux = "";
//
//        try {
//
//            Uploadimage imgup = new Uploadimage();
//            boolean isLinux = System.getProperty("os.name").startsWith("Linux");
//
//
//            String PATH;
//            if (isLinux) {
//                PATH = linux_DIRECTORY;
//            } else {
//                PATH = window_DIRECTORY;
//            }
//
//
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN_FORMAT).withZone(ZoneId.systemDefault());
//
//            Instant instant = Instant.now();
//            String timename = formatter.format(instant);
//
//
//            String img_image1_name = "image1" + "img_image1_name" + timename + ".png";
//            String img_image2_name = "image2" + "img_image1_name" + timename + ".png";
//            String img_image3_name = "image3" + "img_image1_name" + timename + ".png";
//            String img_image4_name = "image4" + "img_image1_name" + timename + ".png";
//            String img_image5_name = "image5" + "img_image1_name" + timename + ".png";
//
//            save_image1 = imgup.image_image1(PATH, img_image1_name, image1);
//            save_image2 = imgup.image_image2(PATH, img_image2_name, image2);
//            save_image3 = imgup.image_image3(PATH, img_image3_name, image3);
//            save_image4 = imgup.image_image4(PATH, img_image4_name, image4);
//            save_image5 = imgup.image_image5(PATH, img_image5_name, image5);
//
//            //=====================URL save to db===========================
//
//            //String URL_person_window = ServletUriComponentsBuilder.fromCurrentContextPath().path(PATH+"\\"+ img_person_name).build().toUriString();
//            URL_person_linux = "/kyc_Organize/" + current_time + "/" + img_image1_name;
//            URL_doc_linux = "/kyc_Organize/" + current_time + "/" + img_image2_name;
//            URL_sim_linux = "/kyc_Organize/" + current_time + "/" + img_image3_name;
//            URL_org_linux = "/kyc_Organize/" + current_time + "/" + img_image4_name;
//            URL_org_linux = "/kyc_Organize/" + current_time + "/" + img_image5_name;
//
//
//        } catch (Exception ex) {
//            response.put("resultCode", "210");
//            response.put("resultMsg", "Can not Upload image");
//            response.put("extraPara", "");
//
//            return ResponseEntity.ok(response);
//        }
//
//
//        if (save_image1 && save_image2 && save_image3 && save_image4 && save_image5) {
//
//
//            PreparedStatement pstmt;
//            ResultSet rs;
//            DatabaseConnectionPool dbConnectionPool = null;
//            Connection connection1 = null;
//            String strRetunr = null;
//            Statement statementtAuth = null;
//            ResultSet resultSettAuth = null;
//            Connection conntAuth = null;
//            String sql = "";
//
//
//            String sql = "";
//
//            try {
//                dbConnectionPool = new DatabaseConnectionPool(Config.driverServr, Config.dburlServr,
//                        Config.dbUserNameServr, Config.dbPasswordServr);
//                connection1 = dbConnectionPool.getConnection();
//                PreparedStatement statement = connection1.prepareStatement(sql);
//                statement.setString(1, msisdn);
//                statement.setString(2, fname);
//                statement.setString(3, lname);
//                statement.setInt(4, gender_id);
//                statement.setString(5, birthday);
//                statement.setInt(6, document_id);
//                statement.setString(7, occupation);
//                statement.setInt(8, simtype_id);
//                statement.setString(9, address_reg);
//                statement.setString(10, latitude);
//                statement.setString(11, longtitude);
//                statement.setString(12, devicename);
//                statement.setString(13, osversion);
//                statement.setString(14, URL_person_linux);
//                statement.setString(15, URL_doc_linux);
//                statement.setString(16, URL_sim_linux);
//                int rowsUpdated = statement.executeUpdate();
//
//                // System.out.println("sql====="+ sql);
//                if (rowsUpdated > 0) {
//                    // System.out.println("An existing user was updated successfully!");
//
//                    response.put("resultCode", "210");
//                    response.put("resultMsg", "Can not Upload image");
//                    response.put("extraPara", "");
//
//                    return ResponseEntity.ok(response);
//                }
//
//            } catch (SQLException ex) {
//
//                response.put("resultCode", "210");
//                response.put("resultMsg", "Can not Upload image");
//                response.put("extraPara", "");
//
//                return ResponseEntity.ok(response);
//            } finally {
//
//                try {
//
//                    dbConnectionPool.freeConnection(connection1);
//                    // release resources
//                    // dbConnectionPool.destroy();
//
//                    if (conntAuth != null) {
//                        conntAuth.close();
//                    }
//
//                    if (statementtAuth != null) {
//                        statementtAuth.close();
//                    }
//
//                    if (resultSettAuth != null) {
//                        resultSettAuth.close();
//                    }
//                } catch (Exception e3) {
//
//                }
//            }
//
//        } else {
//            simregismodel.setResultCode(GlobalParameter.error_upload_image);
//            simregismodel.setResultMsg(GlobalParameter.error_upload_image_msg);
//            simregismodel.setTransactionID(transactionNo);
//            simregismodel.setExtraPara("Error Upload image");
//
//            return simregismodel;
//
//        }

        response.put("resultCode", "200");
        response.put("idproduct",idproduct);
        response.put("productName",productName);
        response.put("description",description);
        response.put("detailproduct",detailproduct);
        response.put("amoutProduct",amoutProduct);
        response.put("priceProduct",priceProduct);

        return ResponseEntity.ok(response);
    }
}
