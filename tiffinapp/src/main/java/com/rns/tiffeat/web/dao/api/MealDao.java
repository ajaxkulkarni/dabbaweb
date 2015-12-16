package com.rns.tiffeat.web.dao.api;

import java.util.List;

import com.rns.tiffeat.web.dao.domain.CustomerMeal;
import com.rns.tiffeat.web.dao.domain.Meal;
import com.rns.tiffeat.web.dao.domain.Order;

public interface MealDao {

	void addMeal(Meal meal);
	void editMeal(Meal meal);
	Meal getMeal(long id);
	void deleteMeal(Meal meal);
	
	void editMeal(com.rns.tiffeat.web.dao.domain.Meal mealToCook, List<Order> orders);
	void addCustomerMeals(List<CustomerMeal> meals, Meal meal);
	
}
