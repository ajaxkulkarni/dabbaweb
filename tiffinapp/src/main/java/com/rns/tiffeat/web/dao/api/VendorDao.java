package com.rns.tiffeat.web.dao.api;

import java.math.BigDecimal;
import java.util.List;

import com.rns.tiffeat.web.dao.domain.Vendor;

public interface VendorDao {
	
	void addVendor(Vendor vendor);
	void updateVendor(Vendor vendor);
	List<Vendor> getAllVendors();
	void addFeedback(Vendor vendor,BigDecimal feedback);
	void addRating(Vendor vendor);
	void deleteVendor(Vendor vendor);
	Vendor getVendorByEmail(String email);
	Vendor getVendorByPhone(String phone);
	List<com.rns.tiffeat.web.bo.domain.Meal> getVendorMeals(Vendor registeredVendor);
	List<Vendor> getVendorsByArea(String pinCode);
	
}
