package com.rns.tiffeat.web.bo.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.rns.tiffeat.web.google.Location;


public class Vendor {
	
	private long id;
	private String name;
	private String password;
	private String email;
	private String phone;
	private String address;
	private String pinCode;
	private BigDecimal rating;
	private List<Meal> meals;
	private VendorStatus status;
	private Location location;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPinCode() {
		return pinCode;
	}
	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}
	public BigDecimal getRating() {
		return rating;
	}
	public void setRating(BigDecimal rating) {
		this.rating = rating;
	}
	public List<Meal> getMeals() {
		if(meals == null) {
			meals = new ArrayList<Meal>();
		}
		return meals;
	}
	public void setMeals(List<Meal> meals) {
		this.meals = meals;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public VendorStatus getStatus() {
		return status;
	}
	public void setStatus(VendorStatus status) {
		this.status = status;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	
}
