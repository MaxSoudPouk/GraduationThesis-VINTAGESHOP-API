package com.example.VintageShopAPI.security;

import com.example.VintageShopAPI.global.GlobalParameter;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

//Sample method to construct a JWT

public class JWT_Security_Encode_Decode_Java extends Thread {

    
//    
//    try {
//		// Create session ID to prevent change password on multy using
//		JWT_Security_Encode_Decode_Java encode_Decode_Java = new JWT_Security_Encode_Decode_Java();
//		long ttlMillis = 300000; // 300000 = 5 min
//		jwtTokenStrng = encode_Decode_Java.createJWTSec(md5_segSerailNo, msisdn, ttlMillis, msisdn,
//				md5_segSerailNo);
//	} catch (Exception e) {
//		// TODO: handle exception
//	}
//    

	//======================================================================================================
	public String createJWTSec1(long ttlMillis, String customer_email, String Customer_ID) {



		// The JWT signature algorithm we will be using to sign the token
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);

		// We will sign our JWT with our ApiKey secret
		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(GlobalParameter.sign_key);

		Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

		// Let's set the JWT Claims
//	JwtBuilder builder = Jwts.builder().setId(id).setIssuedAt(now).setSubject(msisdn).setIssuer(issuer)
//		.setId(sessionID).signWith(signatureAlgorithm, signingKey);

		//Let's set the JWT Claims
		JwtBuilder   builder = Jwts.builder().setId(customer_email)
				.setIssuedAt(now)
				.setSubject(customer_email) // User Namw
				.setSubject(Customer_ID) // User Namw
				.signWith(signingKey, signatureAlgorithm);

		// if it has been specified, let's add the expiration
		if (ttlMillis >= 0) {
			long expMillis = nowMillis + ttlMillis;
			Date exp = new Date(expMillis);
			builder.setExpiration(exp);
		}

		// Builds the JWT and serializes it to a compact, URL-safe string
		return builder.compact();
	}

	public boolean deCodeJWT_validate1(String jwt, String customer_email) {
		try {
//			 System.out.println(" jwtgetuserName  ========:: " + userName);
//			 System.out.println(" jwtgetmsisdn    ========:: " + msisdn);


			// This line will throw an exception if it is not a signed JWS (as expected)
			Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(GlobalParameter.sign_key)).parseClaimsJws(jwt)
					.getBody();

//			System.out.println("claims  ========:: " + claims);
//
//			  System.out.println("jwtuserID========:: " + msisdn);


// System.out.println(" GlobalParameter.sign_key========:: " + GlobalParameter.sign_key);
//		Claims claims = (Claims) Jwts.parserBuilder()
//			         .requireAudience(GlobalParameter.sign_key)
//			         .build()
//			         .parse(jwt);


			String getCustomer_email = claims.getSubject();
			String getuserName = claims.getIssuer();

//			System.out.println("getPhoneNumber  ========:: " + getPhoneNumber);
//			System.out.println("getuserName  ========:: " + getuserName);

			// System.out.println(" jwtgetUserName11111========:: " + getuserName);
			// System.out.println(" jwtgetUserID1111111========:: " + getPhoneNumber);

//	    String isSessionIDJWT = "";
//
//	    try {
//		isSessionIDJWT = claims.getId();
//	    } catch (Exception e) {
//		return false;
//	    }

			// Get sessionProfile from hash table;.
//	    String localSessionID = "";
//	    try {
//		String sessionStrLocal = GlobalParameter.hMapProfile.get(msisdn.trim());
//
//		String[] parts = sessionStrLocal.split("\\|");
//
//		localSessionID = parts[3].trim();
//
//	    } catch (Exception e) {
//		return false;
//	    }

			// System.out.println("getMsisdn: " + claims.getSubject());
			// System.out.println("msisdn: " + msisdn);

			if (getCustomer_email.equals(customer_email) && getuserName.equals(getuserName)) {
				// if(getMsisdn.equals(msisdn) && localSessionID.equals(isSessionIDJWT) ) {
				return true;
			} else {

				return false;
			}

			// System.out.println("ID: " + claims.getId());
			// System.out.println("Subject: " + claims.getSubject());
			// System.out.println("Issuer: " + claims.getIssuer());
			// System.out.println("Expiration: " + claims.getExpiration());

		} catch (Exception e) {
			// System.out.println(" Exception========:: " + e.getMessage());

			return false;
		}
	}
}
