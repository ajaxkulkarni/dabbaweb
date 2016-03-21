package com.rns.tiffeat.web.dao.api;

import java.util.List;

import com.rns.tiffeat.web.dao.domain.Customer;
import com.rns.tiffeat.web.dao.domain.EmailActivation;

public interface CustomerDao {
	
	void addCustomer(Customer customer);
	void editCustomer(Customer customer);
	void deleteCustomer(Customer customer);
	Customer getCustomer(long id);
	Customer getCustomerByEmail(String email);
	Customer getCustomerByPhone(String phone);
	List<Customer> getAllCustomers();
	void addActivationCode(EmailActivation emailActivation);
	List<EmailActivation> getActivationCodes(String email);
	Customer getCustomerByDevice(String deviceId);
}
