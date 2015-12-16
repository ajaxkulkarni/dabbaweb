package com.rns.tiffeat.web.dao.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name= "customer_meal")
public class CustomerMeal implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "customer_meal_id")
	private long id;

	@Column(name = "order_date")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date date;

	// DINNER/LUNCH
	@Column(name = "type")
	private String mealType;

	@Column(name = "pin_code")
	private String pinCode;

	@Column(name = "address")
	private String address;
	
	@Column(name = "location_address")
	private String location;

	@Column(name = "x_location")
	private BigDecimal locationX;

	@Column(name = "y_location")
	private BigDecimal locationY;
	
	@Column(name = "format")
	private String format;

	@ManyToOne(fetch = FetchType.EAGER, targetEntity = Customer.class,cascade=CascadeType.MERGE)
	private Customer customer;

	//PAYABLE/DUE
	@Column(name = "status",length = 10)
	private String status;
	
	@ManyToOne(fetch = FetchType.EAGER, targetEntity = Meal.class)
	private Meal meal;
	
	@OneToMany(mappedBy = "customerMeal",cascade=CascadeType.MERGE, fetch = FetchType.LAZY, targetEntity = Order.class)
	private List<Order> orders;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Meal getMeal() {
		return meal;
	}

	public void setMeal(Meal meal) {
		this.meal = meal;
	}

	public String getMealType() {
		return mealType;
	}

	public void setMealType(String mealType) {
		this.mealType = mealType;
	}

	public String getPinCode() {
		return pinCode;
	}

	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public BigDecimal getLocationX() {
		return locationX;
	}

	public void setLocationX(BigDecimal locationX) {
		this.locationX = locationX;
	}

	public BigDecimal getLocationY() {
		return locationY;
	}

	public void setLocationY(BigDecimal locationY) {
		this.locationY = locationY;
	}

	public String getFormat() {
		return format;
	}
	
	public void setFormat(String format) {
		this.format = format;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}
	
	public List<Order> getOrders() {
		if(orders == null) {
			orders = new ArrayList<Order>();
		}
		return orders;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}
	
	public String getLocation() {
		return location;
	}
}
