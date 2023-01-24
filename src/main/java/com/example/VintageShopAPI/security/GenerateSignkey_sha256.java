package com.example.VintageShopAPI.security;

import com.example.VintageShopAPI.global.GlobalParameter;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;



public class GenerateSignkey_sha256 extends Thread {

	public GenerateSignkey_sha256() {
		
	}

	public String generateSignkey_sha256(String userName, String channel, String transactionID,
			 String msisdn, String remark, String extraParams, String uuid, String des_url) {
		
		try {

			String signStr = "charset=utf-8&userName=" +
					  userName +
			        "&channel=" +
			          channel +
			        "&transactionID=" +
			          transactionID +
			        "&msisdn=" +
		              msisdn +
			        "&remark=" +
			        remark +
			        "&extraParams=" +
			        extraParams +
			        "&uuid=" +
			        uuid +
			        "&sign_type=SAH256&key="+ GlobalParameter.sign_key+"&url="+des_url;
			
			
			System.out.println("Server signStr pain="+signStr);
			
			String sign = "";
			try {
				sign = getSHA256Hash(signStr);
				
				//sign = getSHA256Hash("1");
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//System.out.println("Server sign= " + sign);
			return sign;

		} catch (Exception e) {
			return "";
		}
	}

	private static String getSHA256Hash(String data) {
		String result = null;

		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(data.getBytes("UTF-8"));
			return bytesToHex(hash); // make it printable
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}

	private static String bytesToHex(byte[] hash) {
		return DatatypeConverter.printHexBinary(hash);
	}

}
