package com.rns.tiffeat.web.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.rns.tiffeat.web.bo.domain.Customer;
import com.rns.tiffeat.web.bo.domain.CustomerOrder;
import com.rns.tiffeat.web.bo.domain.MealFormat;
import com.rns.tiffeat.web.bo.domain.MealStatus;
import com.rns.tiffeat.web.bo.domain.MealType;
import com.rns.tiffeat.web.bo.domain.OrderStatus;
import com.rns.tiffeat.web.bo.domain.PaymentType;
import com.rns.tiffeat.web.bo.domain.Vendor;
import com.rns.tiffeat.web.bo.domain.VendorStatus;
import com.rns.tiffeat.web.dao.domain.Meal;
import com.rns.tiffeat.web.google.Location;

public class CommonUtil implements Constants {

	public static Date convertDate(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		Date parsedDate = new Date();
		try {
			parsedDate = sdf.parse(StringUtils.trimToEmpty(date));
		} catch (ParseException e) {
		}
		return parsedDate;
	}

	public static Date convertDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		Date parsedDate = new Date();
		try {
			parsedDate = sdf.parse(sdf.format(date));
		} catch (ParseException e) {
		}
		return parsedDate;
	}

	public static String convertDateToString(Date date) {
		if(date == null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		return sdf.format(date);
	}

	public static int getCurrentHours() {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		return c.get(Calendar.HOUR_OF_DAY);
	}

	public static MealType getMealType(String mealType) {
		if (MealType.LUNCH.name().equals(mealType)) {
			return MealType.LUNCH;
		}
		if (MealType.DINNER.name().equals(mealType)) {
			return MealType.DINNER;
		}
		if (MealType.BOTH.name().equals(mealType)) {
			return MealType.BOTH;
		}
		return null;
	}

	public static MealType[] getMealTypes() {
		MealType[] mealtypes = new MealType[MealType.values().length - 1];
		int i = 0;
		for (MealType mealType : MealType.values()) {
			if (!MealType.BOTH.equals(mealType)) {
				mealtypes[i] = mealType;
				i++;
			}
		}
		return mealtypes;
	}

	public static MealStatus getMealPhase(String tiffinPhase) {
		if (MealStatus.COOKING.name().equals(tiffinPhase)) {
			return MealStatus.COOKING;
		}
		if (MealStatus.PREPARE.name().equals(tiffinPhase)) {
			return MealStatus.PREPARE;
		}
		if (MealStatus.DISPATCH.name().equals(tiffinPhase)) {
			return MealStatus.DISPATCH;
		}
		if (MealStatus.DELIVERED.name().equals(tiffinPhase)) {
			return MealStatus.DELIVERED;
		}
		return null;
	}

	public static MealFormat getMealFormat(String format) {
		if (MealFormat.QUICK.name().equals(format)) {
			return MealFormat.QUICK;
		}
		return MealFormat.SCHEDULED;
	}

	public static Date addDay() {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.DATE, 1);
		return convertDate(c.getTime());
	}

	public static boolean checkIfToday(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		if (date == null) {
			return false;
		}
		try {
			Date date1 = sdf.parse(sdf.format(date));
			Date date2 = sdf.parse(sdf.format(new Date()));
			if (date1.compareTo(date2) == 0) {
				return true;
			}
		} catch (ParseException e) {

		}

		return false;
	}

	public static Date formatDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		try {
			date = sdf.parse(sdf.format(date));
		} catch (ParseException e) {

		}
		return date;
	}

	public static Entry<String, String> getSelectedArea(String pinCode) {
		for (Entry<String, String> e : AREAS.entrySet()) {
			if (e.getKey().equals(pinCode)) {
				return e;
			}
		}
		return null;
	}

	public static String getPinCode(String area) {
		for (Entry<String, String> entry : AREAS.entrySet()) {
			if (entry.getValue().equals(area)) {
				return entry.getKey();
			}
		}
		return null;
	}

	public static OrderStatus getOrderStatus(String status) {
		if (OrderStatus.ORDERED.name().equals(status)) {
			return OrderStatus.ORDERED;
		}
		if (OrderStatus.CANCELLED.name().equals(status)) {
			return OrderStatus.CANCELLED;
		}
		if (OrderStatus.CHANGED.name().equals(status)) {
			return OrderStatus.CHANGED;
		}
		if (OrderStatus.DELIVERED.name().equals(status)) {
			return OrderStatus.DELIVERED;
		}
		if (OrderStatus.PAYABLE.name().equals(status)) {
			return OrderStatus.PAYABLE;
		}
		return OrderStatus.NA;
	}

	public static PaymentType getPaymentType(String paymentType) {
		if (PaymentType.CASH.name().equals(paymentType)) {
			return PaymentType.CASH;
		}
		if (PaymentType.CREDIT.name().equals(paymentType)) {
			return PaymentType.CREDIT;
		}
		if (PaymentType.DEBIT.name().equals(paymentType)) {
			return PaymentType.DEBIT;
		}
		if (PaymentType.NETBANKING.name().equals(paymentType)) {
			return PaymentType.NETBANKING;
		}
		return null;
	}

	public static VendorStatus getVendorStatus(String phase) {
		if (StringUtils.equals(VendorStatus.CLOSED.name(), phase)) {
			return VendorStatus.CLOSED;
		}
		if (StringUtils.equals(VendorStatus.NA.name(), phase)) {
			return VendorStatus.NA;
		}
		if (StringUtils.equals(VendorStatus.OPEN.name(), phase)) {
			return VendorStatus.OPEN;
		}
		return null;
	}

	public static void setMealStatus(Meal meal, MealType mealType, MealStatus status) {
		if (mealType == null || meal == null) {
			return;
		}

		if (MealType.LUNCH.equals(mealType)) {
			meal.setLunchStatus(status.name());
		} else {
			meal.setDinnerStatus(status.name());
		}
	}

	public static MealStatus getMealStatus(MealType mealType, Meal meal) {
		if (mealType == null || meal == null) {
			return null;
		}

		if (MealType.LUNCH.equals(mealType)) {
			return getMealPhase(meal.getLunchStatus());
		} else {
			return getMealPhase(meal.getDinnerStatus());
		}

	}

	public static MealStatus getMealStatus(MealType mealType, com.rns.tiffeat.web.bo.domain.Meal meal) {
		if (mealType == null || meal == null) {
			return null;
		}

		if (MealType.LUNCH.equals(mealType)) {
			return meal.getLunchStatus();
		} else {
			return meal.getDinnerStatus();
		}
	}

	public static MealType[] filterScheduledMealTypes(Customer customer, Map<MealType, Date> mealTypesMap) {
		if (customer == null || CollectionUtils.isEmpty(customer.getScheduledOrder())) {
			return availableMealTypes(mealTypesMap);
		}
		if (customer.getScheduledOrder().size() > 1) {
			return null;
		}
		MealType[] mealTypes = new MealType[customer.getScheduledOrder().size()];
		if (MealType.LUNCH.equals(customer.getScheduledOrder().get(0).getMealType()) && mealTypesMap.get(MealType.LUNCH) != null) {
			mealTypes[0] = MealType.DINNER;
		} else if (mealTypesMap.get(MealType.DINNER) != null) {
			mealTypes[0] = MealType.LUNCH;
		}
		return mealTypes;
	}

	private static MealType[] availableMealTypes(Map<MealType, Date> mealTypesMap) {
		MealType[] mealTypes = new MealType[mealTypesMap.size()];
		if (mealTypesMap.size() == 2) {
			mealTypes = new MealType[3];
		}
		int i = 0;
		for (MealType mealType : mealTypesMap.keySet()) {
			mealTypes[i] = mealType;
			i++;
		}
		if (i == 2) {
			mealTypes[i] = MealType.BOTH;
		}
		return mealTypes;
	}

	public static String getJsonResponseFromUrl(String urlString) throws MalformedURLException, IOException {
		URL url;
		URLConnection urlConn;
		String line;
		String outputString;
		url = new URL(urlString);
		urlConn = url.openConnection();
		outputString = "";
		BufferedReader reader2 = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
		while ((line = reader2.readLine()) != null) {
			outputString += line;
		}
		System.out.println(outputString);
		reader2.close();
		return outputString;
	}

	public static BigDecimal calculateVendorDistance(Location location, Vendor vendor) {
		if (isLocationAbsent(location)) {
			return null;
		}
		if (vendor == null || isLocationAbsent(vendor.getLocation())) {
			return null;
		}

		BigDecimal latDiff = location.getLat().subtract(vendor.getLocation().getLat()).abs();
		BigDecimal lonDiff = location.getLng().subtract(vendor.getLocation().getLng()).abs();

		double a = Math.pow(Math.sin(latDiff.doubleValue() / 2), 2)
				+ Math.cos(Math.toRadians(location.getLat().doubleValue()) * Math.cos(Math.toRadians(vendor.getLocation().getLat().doubleValue()))
						* Math.pow(Math.sin(lonDiff.doubleValue() / 2), 2));
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		System.out.println(c);
		return BigDecimal.valueOf(6373 * c);
	}

	private static boolean isLocationAbsent(Location location) {
		return location == null || location.getLat() == null || location.getLng() == null;
	}

	public static BigDecimal distance(Location location, Vendor vendor, String unit) {
		if (isLocationAbsent(location) || isLocationAbsent(vendor.getLocation())) {
			return null;
		}

		double lon1 = location.getLng().doubleValue();
		double lat1 = location.getLat().doubleValue();
		double lon2 = vendor.getLocation().getLng().doubleValue();
		double lat2 = vendor.getLocation().getLat().doubleValue();
		double theta = lon1 - lon2;
		double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
		dist = Math.acos(dist);
		dist = rad2deg(dist);
		dist = dist * 60 * 1.1515;
		if (unit == "K") {
			dist = dist * 1.609344;
		} else if (unit == "N") {
			dist = dist * 0.8684;
		}

		return BigDecimal.valueOf(dist);
	}

	/* ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: */
	/* :: This function converts decimal degrees to radians : */
	/* ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: */
	private static double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}

	/* ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: */
	/* :: This function converts radians to decimal degrees : */
	/* ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: */
	private static double rad2deg(double rad) {
		return (rad * 180 / Math.PI);
	}

	public static CustomerOrder parseCustomerOrder(String order) {
		CustomerOrder customerOrder = new CustomerOrder();
		JsonObject object = new JsonParser().parse(order).getAsJsonObject();
		customerOrder.setMealType(getMealType(object.get(MODEL_MEAL_TYPE).getAsString()));
		customerOrder.setMealFormat(getMealFormat(object.get(MODEL_MEAL_FORMAT).getAsString()));
		JsonObject locationJson = object.get(MODEL_LOCATION).getAsJsonObject();
		Location location = new Location();
		location.setAddress(locationJson.get(MODEL_ADDRESS).getAsString());
		customerOrder.setLocation(location);
		return customerOrder;
	}

	public static float calculatePrice(CustomerOrder order) {
		float amount = 0;
		if (order.getMeal() == null || order.getMeal().getPrice() == null) {
			return amount;
		}
		if (order.getQuantity() == null) {
			amount = order.getMeal().getPrice().floatValue();
			return amount;
		}
		for (int i = 0; i < order.getQuantity(); i++) {
			amount = order.getMeal().getPrice().floatValue() + amount;
		}
		return amount;
	}

	public static void calculateMealPrice(CustomerOrder order, com.rns.tiffeat.web.bo.domain.Meal meal) {
		if (order == null || meal == null || meal.getPrice() == null) {
			return;
		}
		if (MealFormat.QUICK.equals(order.getMealFormat())) {
			return;
		}
		meal.setPrice(meal.getPrice().multiply(SCHEDULED_ORDER_DISCOUNT).setScale(0, RoundingMode.FLOOR));
		return;
	}

	public static String getDay(Date mealTypeDate) {
		return DateUtils.isSameDay(mealTypeDate, new Date()) ? "Today" : "Tomorrow";
	}

	public static Date getDate(String day) {
		if (StringUtils.equalsIgnoreCase(DAY_TOMORROW, day)) {
			return CommonUtil.addDay();
		}
		return new Date();
	}
	
	public static String prepareActivationCode(CustomerOrder order) {
		return StringUtils.removeEnd(StringUtils.capitalize(StringUtils.substring(order.getCustomer().getEmail(), 0, 1)) + Math.floor(Math.random()*1000000), ".0");
	}

	public static BigDecimal calculateValue(String rating) {
		if(StringUtils.isEmpty(rating)) {
			return null;
		}
		BigDecimal value = new BigDecimal(rating);
		return value;
	}
	

}
