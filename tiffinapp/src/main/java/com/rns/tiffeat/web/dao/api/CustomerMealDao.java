package com.rns.tiffeat.web.dao.api;

import java.util.Date;
import java.util.List;

import com.rns.tiffeat.web.bo.domain.MealType;
import com.rns.tiffeat.web.dao.domain.CustomerMeal;

public interface CustomerMealDao {
	
	long addCustomerMeal(CustomerMeal meal);
	CustomerMeal getCustomerMeal(long customerMealId);
	List<CustomerMeal> getScheduledMeals(long customerId);
	CustomerMeal getLastQuickOrder(long customerId);
	List<CustomerMeal> getQuickOrdersByDate(long customerId,Date date);
	List<CustomerMeal> getPreviousQuickOrders(long customerId);
	List<CustomerMeal> getScheduledMealsForVendor(long vendorId);
	void editCustomerMeal(CustomerMeal meal);
	List<CustomerMeal> getScheduledMealsForMeal(long id, MealType mealType);
	long addCustomerMeals(List<CustomerMeal> customerMealsToBeAdded);
	Double getAverageRating(long mealId);
}
