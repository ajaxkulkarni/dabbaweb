package com.rns.tiffeat.web.dao.api;

import java.util.Date;
import java.util.List;

import com.rns.tiffeat.web.bo.domain.MealType;
import com.rns.tiffeat.web.dao.domain.Order;

public interface OrderDao {

	void addOrder(Order order);
	void editOrder(Order order);
	void deleteOrder(Order order);
	Order getCustomerScheduledOrder(long customerId, Date date, String mealType);
	Order getLastVendorOrder(long vendorId);
	void addOrders(List<Order> orders);
	Order getOrder(long id);
	void updateOrders(List<Order> delieveries);
	
	List<Order> getVendorOrders(long vendorId,String mealType);
	List<Order> getMealOrders(long id, String type);
	List<Order> getOrdersForDate(MealType mealType, Date date);
	List<Order> getOrdersForDate(Date date);
	List<Order> getCustomerQuickOrder(long id);
	
	List<Order> getOrdersBetween(Date fromDate, Date toDate);
	List<Order> getMealOrdersInBetween(long id, String type, Date fromDate, Date toDate);
	List<Order> getVendorOrdersInBetween(long id, Date date, Date date2);
	
}
