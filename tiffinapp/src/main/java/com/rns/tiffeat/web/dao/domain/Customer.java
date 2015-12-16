package com.rns.tiffeat.web.dao.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "customer")
public class Customer implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "customer_id")
	private long id;
	
	@Column(name = "name",length = 50)
	private String name;
	
	@Column(name = "email",length = 100)
	private String email;
	
	@Column(name = "phone",length = 15)
	private String phone;
	
	@Column(name = "password",length = 50)
	private String password;
	
	@Column(name = "device_id")
	private String deviceId;
	
	@Column(name = "reg_id")
	private String regId;
	
	@Column(name = "balance")
	private BigDecimal balance;
	
	@OneToMany(mappedBy = "customer",cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = CustomerMeal.class, orphanRemoval = true)
	private List<CustomerMeal> meals;
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getRegId() {
		return regId;
	}

	public void setRegId(String regId) {
		this.regId = regId;
	}
	
	public BigDecimal getBalance() {
		return balance;
	}
	
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public List<CustomerMeal> getMeals() {
		if(meals == null) {
			meals = new ArrayList<CustomerMeal>();
		}
		return meals;
	}
	
	public void setMeals(List<CustomerMeal> meals) {
		this.meals = meals;
	}
	
}
