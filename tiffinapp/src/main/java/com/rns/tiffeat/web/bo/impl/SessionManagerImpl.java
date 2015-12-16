package com.rns.tiffeat.web.bo.impl;

import java.util.List;

import com.rns.tiffeat.web.bo.api.SessionManager;
import com.rns.tiffeat.web.bo.domain.Admin;
import com.rns.tiffeat.web.bo.domain.Customer;
import com.rns.tiffeat.web.bo.domain.Vendor;

public class SessionManagerImpl implements SessionManager {
	
	private Customer customer;
	private List<Vendor> availableVendors;
	private String result;
	private Admin admin;

	public Customer getCustomer() {
		if(customer == null) {
			customer = new Customer();
		}
		return customer;
	}

	public void setAvailableVendors(List<Vendor> vendors) {
		this.availableVendors = vendors;
	}

	public List<Vendor> getAvailableVendors() {
		return availableVendors;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public void logoutCustomer() {
		this.customer = null;
		this.availableVendors = null;
	}
	
	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public Admin getAdmin() {
		if(admin == null) {
			admin = new Admin();
		}
		return admin;
	}

}
