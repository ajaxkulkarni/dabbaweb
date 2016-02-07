package com.rns.tiffeat.web.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.rns.tiffeat.web.bo.domain.MealFormat;
import com.rns.tiffeat.web.bo.domain.MealType;
import com.rns.tiffeat.web.dao.api.OrderDao;
import com.rns.tiffeat.web.dao.domain.Order;

public class OrderDaoImpl implements OrderDao {

	private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void addOrder(Order order) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		order = (Order) session.merge(order);
		tx.commit();
		session.close();
	}

	public void editOrder(Order order) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.merge(order);
		tx.commit();
		session.close();

	}

	public void deleteOrder(Order order) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.delete(order);
		tx.commit();
		session.close();

	}

	public List<Order> getVendorOrders(long vendorId, String mealType) {
		List<Order> orders = new ArrayList<Order>();
		Session session = this.sessionFactory.openSession();
		Query createQuery = session.createQuery("from Order where customerMeal.meal.vendor.id=:vendor_id AND customerMeal.mealType=:meal_type");
		createQuery.setLong("vendor_id", vendorId);
		createQuery.setString("meal_type", mealType);
		orders = createQuery.list();
		session.close();
		return orders;
	}

	public Order getCustomerScheduledOrder(long customerId, Date date, String mealType) {
		List<Order> orders = new ArrayList<Order>();
		Session session = this.sessionFactory.openSession();
		Query createQuery = session.createQuery("from Order where customerMeal.customer.id=:customer_id AND date=:input_date AND customerMeal.format=:scheduled"
				+ " AND customerMeal.mealType=:mealType order by id DESC");
		createQuery.setLong("customer_id", customerId);
		createQuery.setDate("input_date", date);
		createQuery.setString("scheduled", MealFormat.SCHEDULED.name());
		createQuery.setString("mealType", mealType);
		orders = createQuery.list();
		session.close();
		if(CollectionUtils.isEmpty(orders)) {
			return null;
		}
		return orders.get(0);
	}

	public Order getLastVendorOrder(long vendorId) {
		List<Order> orders = new ArrayList<Order>();
		Session session = this.sessionFactory.openSession();
		Query createQuery = session.createQuery("from Order o where o.customer.meal.vendor.id=:vendor_id ORDER BY o.date DESC");
		orders = createQuery.list();
		session.close();
		if(CollectionUtils.isEmpty(orders)) {
			return null;
		}
		return orders.get(0);
	}

	public void addOrders(List<Order> orders) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		for(Order order:orders) {
			session.merge(order);
		}
		tx.commit();
		session.close();		
	}

	public Order getOrder(long id) {
		List<Order> orders = new ArrayList<Order>();
		Session session = this.sessionFactory.openSession();
		Query createQuery = session.createQuery("from Order where id=:id");
		createQuery.setLong("id", id);
		orders = createQuery.list();
		session.close();
		if(CollectionUtils.isEmpty(orders)) {
			return null;
		}
		return orders.get(0);
	}

	public void updateOrders(List<Order> orders) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.flush();
		for(Order order:orders) {
			session.update(order);
			//session.merge(order.getCustomerMeal());
		}
		tx.commit();
		session.clear();
		session.close();			
	}

	public List<Order> getMealOrders(long id, String type) {
		List<Order> orders = new ArrayList<Order>();
		Session session = this.sessionFactory.openSession();
		Query createQuery = session.createQuery("from Order where customerMeal.meal.id=:id AND date=:date AND customerMeal.mealType=:type");
		createQuery.setLong("id", id);
		createQuery.setDate("date", new Date());
		createQuery.setString("type", type);
		orders = createQuery.list();
		session.close();
		return orders;
	}

	public List<Order> getOrdersForDate(MealType mealType, Date date) {
		List<Order> orders = new ArrayList<Order>();
		Session session = this.sessionFactory.openSession();
		Query createQuery = session.createQuery("from Order where order.date=:date AND customerMeal.mealType=:type");
		createQuery.setString("type", mealType.name());
		createQuery.setDate("date", new Date());
		orders = createQuery.list();
		session.close();
		return orders;
	}
	
	public List<Order> getOrdersForDate(Date date) {
		List<Order> orders = new ArrayList<Order>();
		Session session = this.sessionFactory.openSession();
		Query createQuery = session.createQuery("from Order where date=:date");
		createQuery.setDate("date", new Date());
		orders = createQuery.list();
		session.close();
		return orders;
	}

	public List<Order> getCustomerQuickOrder(long id) {
		List<Order> orders = new ArrayList<Order>();
		Session session = this.sessionFactory.openSession();
		Query createQuery = session.createQuery("from Order where customerMeal.id=:orderId AND customerMeal.format=:quick order by id DESC");
		createQuery.setString("quick", MealFormat.QUICK.name());
		createQuery.setLong("orderId", id);
		orders = createQuery.list();
		session.close();
		return orders;
	}

	public List<Order> getOrdersBetween(Date fromDate, Date toDate) {
		List<Order> orders = new ArrayList<Order>();
		Session session = this.sessionFactory.openSession();
		Query createQuery = session.createQuery("from Order where date>=:from_date AND date<=:to_date");
		createQuery.setDate("from_date", fromDate);
		createQuery.setDate("to_date", toDate);
		orders = createQuery.list();
		session.close();
		return orders;
	}

	public List<Order> getMealOrdersInBetween(long id, String type, Date fromDate, Date toDate) {
		List<Order> orders = new ArrayList<Order>();
		Session session = this.sessionFactory.openSession();
		Query createQuery = session.createQuery("from Order where customerMeal.meal.id=:id AND date>=:from_date AND date<=:to_date AND customerMeal.mealType=:type");
		createQuery.setLong("id", id);
		createQuery.setString("type", type);
		createQuery.setDate("from_date", fromDate);
		createQuery.setDate("to_date", toDate);
		orders = createQuery.list();
		session.close();
		return orders;
	}

	public List<Order> getVendorOrdersInBetween(long id, Date fromDate, Date toDate) {
		List<Order> orders = new ArrayList<Order>();
		Session session = this.sessionFactory.openSession();
		Query createQuery = session.createQuery("from Order where customerMeal.meal.vendor.id=:vendor_id AND date>=:from_date AND date<=:to_date");
		createQuery.setLong("vendor_id", id);
		createQuery.setDate("from_date", fromDate);
		createQuery.setDate("to_date", toDate);
		orders = createQuery.list();
		session.close();
		return orders;
	}


}
