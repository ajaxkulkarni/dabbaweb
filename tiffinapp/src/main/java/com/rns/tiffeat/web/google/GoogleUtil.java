package com.rns.tiffeat.web.google;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.rns.tiffeat.web.bo.domain.Customer;
import com.rns.tiffeat.web.bo.domain.GooglePojo;
import com.rns.tiffeat.web.util.CommonUtil;
import com.rns.tiffeat.web.util.Constants;

public class GoogleUtil implements Constants {

	//private static final String GOOGLE_LOCATION_ROOT_URL = "https://maps.googleapis.com/maps/api/geocode/json?";
	//private static final String API_KEY = "AIzaSyCeqAG4MWZ4WNXvd-c4guFjtWFW8MTyUlc";

	public static Customer getGoogleCustomer(String googleCode) throws IOException {
		String parameters = "code=" + googleCode + Constants.GOOGLE_ACCESS_TOKEN_URL_PARAMS;
		URL url = new URL(GOOGLE_GET_ACCESS_TOKEN_URL);
		URLConnection urlConn = url.openConnection();
		urlConn.setDoOutput(true);
		OutputStreamWriter writer = new OutputStreamWriter(urlConn.getOutputStream());
		writer.write(parameters);
		writer.flush();
		// get output in outputString
		String line, outputString = "";
		BufferedReader reader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
		while ((line = reader.readLine()) != null) {
			outputString += line;
		}
		System.out.println(outputString);
		JsonObject json = (JsonObject) new JsonParser().parse(outputString);
		String access_token = json.get(GOOGLE_ACCESS_TOKEN).getAsString();
		System.out.println(access_token);
		writer.close();
		// get User Info
		outputString = CommonUtil.getJsonResponseFromUrl(GOOGLE_GET_USER_DATA_URL + access_token);
		// Convert JSON response into Pojo class
		GooglePojo data = new Gson().fromJson(outputString, GooglePojo.class);
		System.out.println(data);

		Customer customer = new Customer();
		customer.setEmail(data.getEmail());
		customer.setName(data.getName());
		return customer;
	}

	
	public static BigDecimal getDistanceBetweenLocations(String source, String destination) throws JsonSyntaxException, MalformedURLException, IOException {
		String url = createDistanceUrl(source,destination);
		String resultJson = CommonUtil.getJsonResponseFromUrl(url);
		GoogleDistanceResult result = new Gson().fromJson(resultJson,GoogleDistanceResult.class);
		if(result == null) {
			return null;
		}
		if(CollectionUtils.isEmpty(result.getRows())) {
			return null;
		}
		for(GoogleDistanceRow row:result.getRows()) {
			BigDecimal distance = extractElement(row);
			if(result != null) {
				return distance;
			}
		}
		return null;
	}

	private static BigDecimal extractElement(GoogleDistanceRow row) {
		if(CollectionUtils.isEmpty(row.getElements())) {
			return null;
		}
		for(GoogleDistanceElement element:row.getElements()) {
			if(element.getDistance() == null) {
				continue;
			}
			return element.getDistance().getValue();
		}
		return null;
	}

	private static String createDistanceUrl(String location1, String location2) {
		StringBuilder builder = new StringBuilder();
		builder.append(GOOGLE_DISTANCE_URL);
		builder.append("origins=");
		builder.append(StringUtils.replace(location1," ",SPACE_CHAR));
		builder.append(PARAMETER_APPENDER);
		builder.append("destinations=");
		builder.append(StringUtils.replace(location2," ",SPACE_CHAR));
		//builder.append("&");
		//builder.append("key=");
		//builder.append(API_KEY);
		return builder.toString();
	}
	

}
