package com.rns.tiffeat.web.bo.api;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.rns.tiffeat.web.bo.domain.Customer;
import com.rns.tiffeat.web.bo.domain.CustomerOrder;
import com.rns.tiffeat.web.bo.domain.DailyContent;
import com.rns.tiffeat.web.bo.domain.Meal;
import com.rns.tiffeat.web.bo.domain.MealType;
import com.rns.tiffeat.web.bo.domain.PayUDetails;
import com.rns.tiffeat.web.bo.domain.Vendor;

public interface CustomerBo {
	
	List<Customer> getAllCustomers();

	// void submitFeedback(Meal meal);
	String register(Customer customer);

	boolean login(Customer customer);

	List<Vendor> getAvailableVendors(String area);

	Vendor getVendorWithMeals(Vendor vendor);

	//MealType[] getAvailableMealType(CustomerOrder order);

	Map<MealType, Date> getAvailableMealTypeDates(CustomerOrder order);

	String quickOrder(CustomerOrder customerOrder);
	
	String validateScheduledOrder(CustomerOrder order);

	String scheduledOrder(CustomerOrder customerOrder);

	String changeScheduledOrder(CustomerOrder customerOrder);

	String cancelScheduledOrder(CustomerOrder customerOrder);

	String addMoneyToWallet(Customer currentCustomer);

	// void addMeal(Customer customer);
	long addTransaction(PayUDetails payUDetails);

	void setCurrentCustomer(Customer currentCustomer);

	String validateQuickOrder(CustomerOrder customerOrder);

	void rateMeal(CustomerOrder order, BigDecimal rating);

	DailyContent getDailyContentForMeal(Meal meal, MealType mealType);

	String loginWithGoogle(Customer googleCustomer);
}
