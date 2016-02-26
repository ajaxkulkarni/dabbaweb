package com.rns.tiffeat.web.bo.domain;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.rns.tiffeat.web.util.Constants;

public class DailyContent implements Constants {
	
	private long id;
	private String mainItem;
	private String subItem1;
	private String subItem2;
	private String subItem3;
	private String subItem4;
	private Date date;
	private Meal meal;
	private MealType mealType;
	
	public String getMainItem() {
		return mainItem;
	}
	public void setMainItem(String mainItem) {
		this.mainItem = mainItem;
	}
	public String getSubItem1() {
		return subItem1;
	}
	public void setSubItem1(String subItem1) {
		this.subItem1 = subItem1;
	}
	public String getSubItem2() {
		return subItem2;
	}
	public void setSubItem2(String subItem2) {
		this.subItem2 = subItem2;
	}
	public String getSubItem3() {
		return subItem3;
	}
	public void setSubItem3(String subItem3) {
		this.subItem3 = subItem3;
	}
	public String getSubItem4() {
		return subItem4;
	}
	public void setSubItem4(String subItem4) {
		this.subItem4 = subItem4;
	}
	public Meal getMeal() {
		return meal;
	}
	public void setMeal(Meal meal) {
		this.meal = meal;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public MealType getMealType() {
		return mealType;
	}
	public void setMealType(MealType mealType) {
		this.mealType = mealType;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		if(StringUtils.isEmpty(mainItem)) {
			return ERROR_MENU_NOT_AVAILABLE_YET;
		}
		StringBuilder builder = new StringBuilder();
		appendField(builder, mainItem, "");
		appendField(builder, " along with " , subItem1);
		appendField(builder, " accompanied by " , subItem2);
		appendField(builder, " and " , subItem3);
		appendField(builder, ", Also " , subItem4);
		return builder.toString();
	}
	
	private void appendField(StringBuilder builder, String phrase, String item) {
		if(StringUtils.isEmpty(item) || StringUtils.equalsIgnoreCase("NA", item)) {
			return;
		}
		builder.append(phrase).append(item);
	}

}
