package com.rns.tiffeat.web.util;

import static org.apache.commons.lang3.StringUtils.trimToEmpty;

import com.rns.tiffeat.web.bo.domain.Customer;
import com.rns.tiffeat.web.bo.domain.CustomerOrder;
import com.rns.tiffeat.web.bo.domain.DailyContent;
import com.rns.tiffeat.web.bo.domain.Meal;
import com.rns.tiffeat.web.bo.domain.Vendor;
import com.rns.tiffeat.web.dao.domain.CustomerMeal;
import com.rns.tiffeat.web.dao.domain.DailyMeal;
import com.rns.tiffeat.web.dao.domain.Order;
import com.rns.tiffeat.web.google.Location;

public class DataToBusinessConverters implements Constants {
	
	public static void convertVendor(Vendor currentVendor,com.rns.tiffeat.web.dao.domain.Vendor registeredVendor) {
		currentVendor.setId(registeredVendor.getId());
		currentVendor.setName(trimToEmpty(registeredVendor.getName()));
		currentVendor.setEmail(trimToEmpty(registeredVendor.getEmail()));
		currentVendor.setAddress(trimToEmpty(registeredVendor.getAddress()));
		currentVendor.setPhone(trimToEmpty(registeredVendor.getPhone()));
		currentVendor.setPinCode(AREAS.get(trimToEmpty(registeredVendor.getPinCode())));
		currentVendor.setStatus(CommonUtil.getVendorStatus(registeredVendor.getPhase()));
		Location location = new Location();
		location.setAddress(registeredVendor.getLocation());
		currentVendor.setLocation(location);
	}
	
	public static void convertCustomer(com.rns.tiffeat.web.dao.domain.Customer registeredCustomer,Customer currentCustomer) {
		currentCustomer.setId(registeredCustomer.getId());
		currentCustomer.setEmail(trimToEmpty(registeredCustomer.getEmail()));
		currentCustomer.setName(trimToEmpty(registeredCustomer.getName()));
		currentCustomer.setPhone(trimToEmpty(registeredCustomer.getPhone()));
		currentCustomer.setBalance(registeredCustomer.getBalance());
	}
	
	public static DailyContent convertDailyContent(DailyMeal dailyMeal) {
		if(dailyMeal == null) {
			return null;
		}
		DailyContent content = new DailyContent();
		content.setId(dailyMeal.getId());
		content.setMainItem(trimToEmpty(dailyMeal.getMainCourse()));
		content.setSubItem1(trimToEmpty(dailyMeal.getSubItem1()));
		content.setSubItem2(trimToEmpty(dailyMeal.getSubItem2()));
		content.setSubItem3(trimToEmpty(dailyMeal.getSubItem3()));
		content.setSubItem4(trimToEmpty(dailyMeal.getSubItem4()));
		content.setMeal(convertMeal(dailyMeal.getMeal()));
		content.setMealType(CommonUtil.getMealType(dailyMeal.getType()));
		content.setDate(dailyMeal.getDate());
		return content;
	}

	public static Meal convertMeal(com.rns.tiffeat.web.dao.domain.Meal meal) {
		com.rns.tiffeat.web.bo.domain.Meal mealToBeAdded = new com.rns.tiffeat.web.bo.domain.Meal();
		mealToBeAdded.setId(meal.getId());
		mealToBeAdded.setPrice(meal.getPrice());
		mealToBeAdded.setTitle(trimToEmpty(meal.getTitle()));
		mealToBeAdded.setLunchStatus(CommonUtil.getMealPhase(meal.getLunchStatus()));
		mealToBeAdded.setDinnerStatus(CommonUtil.getMealPhase(meal.getDinnerStatus()));
		mealToBeAdded.setDescription(trimToEmpty(meal.getDescription()));
		mealToBeAdded.setMealTime(CommonUtil.getMealType(meal.getType()));
		Vendor currentVendor = new Vendor();
		DataToBusinessConverters.convertVendor(currentVendor, meal.getVendor());
		mealToBeAdded.setVendor(currentVendor);
		return mealToBeAdded;
	}
	
	public static void convertCustomerOrder(CustomerMeal latestMeal, CustomerOrder customerOrder, Customer currentCustomer) {
		if(latestMeal == null) {
			return;
		}
		customerOrder.setId(latestMeal.getId());
		customerOrder.setCustomer(currentCustomer);
		customerOrder.setArea(AREAS.get(trimToEmpty(latestMeal.getPinCode())));
		customerOrder.setAddress(trimToEmpty(latestMeal.getAddress()));
		customerOrder.setMeal(DataToBusinessConverters.convertMeal(latestMeal.getMeal()));
		customerOrder.setDate(latestMeal.getDate());
		customerOrder.setMealFormat(CommonUtil.getMealFormat(trimToEmpty(latestMeal.getFormat())));
		customerOrder.setMealType(CommonUtil.getMealType(trimToEmpty(latestMeal.getMealType())));
		customerOrder.setMealStatus(CommonUtil.getMealStatus(customerOrder.getMealType(), customerOrder.getMeal()));
		Location location = new Location();
		location.setAddress(latestMeal.getLocation());
		customerOrder.setLocation(location);
	}
	

	public static CustomerOrder convertOrder(Order order) {
		CustomerOrder customerOrder = new CustomerOrder();
		if(order == null ||order.getCustomerMeal() == null) {
			return customerOrder;
		}
		com.rns.tiffeat.web.bo.domain.Customer customer = new com.rns.tiffeat.web.bo.domain.Customer();
		convertCustomer(order.getCustomerMeal().getCustomer(), customer);
		convertCustomerOrder(order.getCustomerMeal(), customerOrder, customer);
		customerOrder.setId(order.getId());
		customerOrder.setDate(order.getDate());
		customerOrder.setPaymentType(CommonUtil.getPaymentType(order.getPaymentType()));
		customerOrder.setStatus(CommonUtil.getOrderStatus(order.getStatus()));
		customerOrder.setTransactionId(order.getTransactionId());
		return customerOrder;
	}

}
