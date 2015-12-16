package com.rns.tiffeat.web.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.rns.tiffeat.web.bo.domain.CustomerOrder;
import com.rns.tiffeat.web.bo.domain.MealFormat;
import com.rns.tiffeat.web.bo.domain.PayUDetails;
import com.rns.tiffeat.web.bo.domain.PaymentType;

public class PaymentUtils implements Constants {
	

	public static PayUDetails preparePayUDetails(CustomerOrder order) throws NoSuchAlgorithmException {
		if(order.getMeal() == null || order.getCustomer()== null) {
			return null;
		}
		PayUDetails payUDetails = new PayUDetails();
		payUDetails.setMerchantKey(MERCHANT_KEY);
		if(order.getMeal().getPrice() != null) {
			payUDetails.setAmount(order.getMeal().getPrice().floatValue());
		}
		if(MealFormat.SCHEDULED.equals(order.getMealFormat()) && order.getCustomer().getBalance() != null) {
			payUDetails.setAmount(order.getCustomer().getBalance().floatValue());
		}
		
		payUDetails.setName(order.getCustomer().getName());
		payUDetails.setEmail(order.getCustomer().getEmail());
		payUDetails.setPhone(order.getCustomer().getPhone());
		payUDetails.setProductInfo(PRODUCT_INFO);
		payUDetails.setSuccessUrl(RESULT_URL_QUICK);
		payUDetails.setFailureUrl(RESULT_URL_QUICK);
		return payUDetails;
	}

	public static String prepareHashKey(PayUDetails payUDetails) throws NoSuchAlgorithmException {
		StringBuilder keyBuilder = new StringBuilder();
		keyBuilder.append(payUDetails.getMerchantKey()).append("|");
		keyBuilder.append(payUDetails.getId()).append("|");
		keyBuilder.append(payUDetails.getAmount()).append("|");
		keyBuilder.append(payUDetails.getProductInfo()).append("|");
		keyBuilder.append(payUDetails.getName()).append("|");
		keyBuilder.append(payUDetails.getEmail()).append("|");
		keyBuilder.append("").append("|"); //udf1
		keyBuilder.append("").append("|"); //udf2
		keyBuilder.append("").append("|"); //udf3
		keyBuilder.append("").append("|"); //udf4
		keyBuilder.append("").append("|"); //udf5
		keyBuilder.append("|||||" + MERCHANT_SALT);
		return generateKey(keyBuilder.toString());
	}

	private static String generateKey(String key) throws NoSuchAlgorithmException {
		System.out.println("Hash :" + key);
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.update(key.getBytes());
        
        byte byteData[] = md.digest();
 
        //convert the byte to hex format method 1
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
         sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
     
        System.out.println("Hex format : " + sb.toString());
        
        //convert the byte to hex format method 2
        StringBuffer hexString = new StringBuffer();
    	for (int i=0;i<byteData.length;i++) {
    		String hex=Integer.toHexString(0xff & byteData[i]);
   	     	if(hex.length()==1) hexString.append('0');
   	     	hexString.append(hex);
    	}
    	System.out.println("Hex format : " + hexString.toString());
    	return hexString.toString();
	}

	public static PaymentType getPaymenType(String parameter) {
		if("CC".equals(parameter)) {
			return PaymentType.CREDIT;
		}
		if("DC".equals(parameter)) {
			return PaymentType.DEBIT;
		}
		if("NB".equals(parameter)) {
			return PaymentType.NETBANKING;
		}
		if("COD".equals(parameter)) {
			return PaymentType.CASH;
		}
		return null;
	}
}
