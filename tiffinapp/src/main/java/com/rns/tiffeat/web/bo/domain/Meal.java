package com.rns.tiffeat.web.bo.domain;

import java.math.BigDecimal;

public class Meal {

	private long id;
	private String title;
	private String description;
	private String image;
	private BigDecimal rating;
	private BigDecimal price;
	private MealStatus lunchStatus;
	private MealStatus dinnerStatus;
	private Vendor vendor;
	private DailyContent lunchMenu;
	private DailyContent dinnerMenu;
	private MealType mealTime;
	private String menu;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public BigDecimal getRating() {
		return rating;
	}
	public void setRating(BigDecimal rating) {
		this.rating = rating;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Vendor getVendor() {
		return vendor;
	}
	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}
	
	public DailyContent getLunchMenu() {
		return lunchMenu;
	}
	
	public void setLunchMenu(DailyContent lunchMenu) {
		this.lunchMenu = lunchMenu;
	}
	
	public DailyContent getDinnerMenu() {
		return dinnerMenu;
	}
	public void setDinnerMenu(DailyContent dinnerMenu) {
		this.dinnerMenu = dinnerMenu;
	}
	public MealStatus getLunchStatus() {
		return lunchStatus;
	}
	public void setLunchStatus(MealStatus lunchStatus) {
		this.lunchStatus = lunchStatus;
	}
	public MealStatus getDinnerStatus() {
		return dinnerStatus;
	}
	public void setDinnerStatus(MealStatus dinnerStatus) {
		this.dinnerStatus = dinnerStatus;
	}
	public MealType getMealTime() {
		return mealTime;
	}
	public void setMealTime(MealType mealTime) {
		this.mealTime = mealTime;
	}
	public String getMenu() {
		return menu;
	}
	public void setMenu(String menu) {
		this.menu = menu;
	}
	
	
}	
