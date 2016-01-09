package com.rns.tiffeat.web.bo.domain;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.rns.tiffeat.web.util.Constants;

public class Admin implements Constants{
	
	private String username;
	private String password;
	List<CustomerOrder> todaysOrders;
	int quickOrderCount;
	int scheduledOrderCount;
	List<CustomerOrder> lunchOrders;
	List<CustomerOrder> dinnerOrders;
	Vendor currentVendor;
	CustomerOrder currentOrder;
	
	public void setLunchOrders(List<CustomerOrder> lunchOrders) {
		this.lunchOrders = lunchOrders;
	}
	
	public List<CustomerOrder> getLunchOrders() {
		return lunchOrders;
	}
	
	public void setDinnerOrders(List<CustomerOrder> dinnerOrders) {
		this.dinnerOrders = dinnerOrders;
	}
	
	public List<CustomerOrder> getDinnerOrders() {
		return dinnerOrders;
	}
	
	public void setCurrentOrder(CustomerOrder currentOrder) {
		this.currentOrder = currentOrder;
	}
	
	public CustomerOrder getCurrentOrder() {
		return currentOrder;
	}
	
	public void setCurrentVendor(Vendor currentVendor) {
		this.currentVendor = currentVendor;
	}
	public Vendor getCurrentVendor() {
		return currentVendor;
	}
	public List<CustomerOrder> getTodaysOrders() {
		return todaysOrders;
	}
	public void setTodaysOrders(List<CustomerOrder> todaysOrders) {
		this.todaysOrders = todaysOrders;
	}
	public int getQuickOrderCount() {
		return quickOrderCount;
	}
	public void setQuickOrderCount(int quickOrderCount) {
		this.quickOrderCount = quickOrderCount;
	}
	public int getScheduledOrderCount() {
		return scheduledOrderCount;
	}
	public void setScheduledOrderCount(int scheduledOrderCount) {
		this.scheduledOrderCount = scheduledOrderCount;
	}
	
	public void setCounts() {
		if(CollectionUtils.isEmpty(todaysOrders)) {
			return;
		}
		lunchOrders = new ArrayList<CustomerOrder>();
		dinnerOrders = new ArrayList<CustomerOrder>();
		quickOrderCount = 0;
		scheduledOrderCount = 0;
		for(CustomerOrder order:todaysOrders) {
			if(MealFormat.QUICK.equals(order)) {
				quickOrderCount++;
			}
			else {
				scheduledOrderCount++;
			}
			if(MealType.LUNCH.equals(order.getMealType())) {
				lunchOrders.add(order);
			}
			else {
				dinnerOrders.add(order);
			}
		}
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean validateAdminDetails() {
		if(StringUtils.equals(getUsername(), ADMIN_USERNAME) && StringUtils.equals(getPassword(), ADMIN_PASSWORD)) {
			return true;
		}
		return false;
	}

}
