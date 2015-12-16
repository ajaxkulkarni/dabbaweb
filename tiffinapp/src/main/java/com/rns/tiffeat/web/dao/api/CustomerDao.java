package com.rns.tiffeat.web.dao.api;

import com.rns.tiffeat.web.dao.domain.Customer;

public interface CustomerDao {
	
	void addCustomer(Customer customer);
	void editCustomer(Customer customer);
	void deleteCustomer(Customer customer);
	Customer getCustomer(long id);
	Customer getCustomerByEmail(String email);
	Customer getCustomerByPhone(String phone);
}
