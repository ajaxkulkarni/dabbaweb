package com.rns.tiffeat.web.bo.domain;

import java.io.Serializable;

public enum MealType implements Serializable {
	LUNCH("Lunch"),DINNER("Dinner"),BOTH("Lunch and Dinner");
	
	private String description;
	private MealType(String description) {
		this.description = description;
	}
	public String getDescription() {
		return description;
	}
}
