package com.rns.tiffeat.web.bo.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class Customer {

	private long id;
	private String name;
	private String password;
	private String email;
	private String phone;
	private String deviceId;
	private String regId;
	private BigDecimal balance;
	private List<CustomerOrder> scheduledOrder;
	private List<CustomerOrder> quickOrders;
	private List<CustomerOrder> previousOrders;
	private CustomerOrder orderInProcess;
	private Integer noOfTiffinsRemaining;
	
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	public List<CustomerOrder> getQuickOrders() {
		if(quickOrders == null) {
			quickOrders = new ArrayList<CustomerOrder>();
		}
		return quickOrders;
	}
	public void setQuickOrders(List<CustomerOrder> orders) {
		this.quickOrders = orders;
	}
	public List<CustomerOrder> getPreviousOrders() {
		return previousOrders;
	}
	public void setPreviousOrders(List<CustomerOrder> previousOrders) {
		this.previousOrders = previousOrders;
	}
	
	public List<CustomerOrder> getScheduledOrder() {
		if(scheduledOrder == null) {
			scheduledOrder = new ArrayList<CustomerOrder>();
		}
		return scheduledOrder;
	}
	
	public void setScheduledOrder(List<CustomerOrder> scheduledOrder) {
		this.scheduledOrder = scheduledOrder;
	}
	
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public void setOrderInProcess(CustomerOrder customerOrder) {
		this.orderInProcess = customerOrder;
	}
	public CustomerOrder getOrderInProcess() {
		if(orderInProcess == null) {
			orderInProcess = new CustomerOrder();
		}
		return orderInProcess;
	}
	public Integer getNoOfTiffinsRemaining() {
		return noOfTiffinsRemaining;
	}
	public void setNoOfTiffinsRemaining(Integer noOfTiffinsRemaining) {
		this.noOfTiffinsRemaining = noOfTiffinsRemaining;
	}
	
}
