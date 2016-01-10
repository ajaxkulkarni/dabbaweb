package com.rns.tiffeat.web.bo.api;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.rns.tiffeat.web.bo.domain.CustomerOrder;
import com.rns.tiffeat.web.bo.domain.DailyContent;
import com.rns.tiffeat.web.bo.domain.Meal;
import com.rns.tiffeat.web.bo.domain.MealType;
import com.rns.tiffeat.web.bo.domain.Vendor;
import com.rns.tiffeat.web.dao.domain.DailyMeal;


public interface VendorBo {

	String addDailyContent(DailyContent dailyMeal);
	String updateDailyContent(DailyContent dailyContent);
	List<DailyMeal> getDailyMealsByDate(Vendor vendor,Date date);
	//void generateCurrentOrder(Meal meal);
	List<CustomerOrder> getAllVendorOrders(com.rns.tiffeat.web.bo.domain.Vendor vendor, String dateRange);
	List<CustomerOrder> getAllOrdersForMeal(Meal meal, MealType mealType, String dateRange);
	
	boolean loginWithEmail(Vendor vendor);
	String register(Vendor vendor);
	
	String addMeal(Meal meal, MultipartFile imageFile);
	String updateMeal(Meal meal,MultipartFile imageFile);
	void deleteMeal(Meal meal);
	Meal getMeal(Meal meal);
	
	void startCooking(Meal meal, MealType mealType);
	String startDispatch(Meal meal, MealType mealType);
	void delieverTiffins(List<CustomerOrder> orders);
	
	List<Vendor> getAllVendors();
	String updateVendorProfile(Vendor vendor, MultipartFile image);
	InputStream getVendorProfilePic(Vendor vendor) throws FileNotFoundException;
	InputStream getMealImage(Meal meal) throws FileNotFoundException;
	
	public void setDailyContents(List<Meal> meals);
	DailyContent getDailyContent(DailyContent content);
	
	List<CustomerOrder> getAllOrders(String dateRange);
	
	String cancelOrder(CustomerOrder order);
	List<Meal> getAllMeals();
	
}
