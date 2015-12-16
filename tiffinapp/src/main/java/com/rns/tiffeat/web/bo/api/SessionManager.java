package com.rns.tiffeat.web.bo.api;

import java.util.List;

import com.rns.tiffeat.web.bo.domain.Admin;
import com.rns.tiffeat.web.bo.domain.Customer;
import com.rns.tiffeat.web.bo.domain.Vendor;

public interface SessionManager {
	
	Customer getCustomer();
	//void setAvailableVendors(List<Vendor> vendors);
	//List<Vendor> getAvailableVendors();
	String getResult();
	void setResult(String result);
	void logoutCustomer();
	Admin getAdmin();
}
