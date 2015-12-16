
package com.rns.tiffeat.web.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.rns.tiffeat.web.dao.api.VendorDao;
import com.rns.tiffeat.web.dao.domain.Meal;
import com.rns.tiffeat.web.dao.domain.Vendor;
import com.rns.tiffeat.web.util.DataToBusinessConverters;

public class VendorDaoImpl implements VendorDao {

	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void addVendor(Vendor vendor) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.persist(vendor);
		tx.commit();
		session.close();
	}

	public void updateVendor(Vendor vendor) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(vendor);
		tx.commit();
		session.close();

	}

	public List<Vendor> getAllVendors() {
		List<Vendor> vendors = new ArrayList<Vendor>();
		Session session = this.sessionFactory.openSession();
		Query createQuery = session.createQuery("from Vendor");
		vendors = createQuery.list();
		session.close();
		return vendors;
	}

	public void addFeedback(Vendor vendor,BigDecimal feedback) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Query queryFeedback = session.createQuery("update Vendor D SET D.feedbacks=:feedbacks+1 where D.id=:id");
		queryFeedback.setLong("id", vendor.getId());
		queryFeedback.setInteger("feedbacks", vendor.getFeedbacks());
		queryFeedback.executeUpdate();
		
		Query queryRating = session.createQuery("update Vendor D SET D.rating=:rating where D.id=:id");
		queryRating.setLong("id", vendor.getId());
		queryRating.setBigDecimal("rating", calculateRating(vendor,feedback));
		queryRating.executeUpdate();
		
		
		tx.commit();
		session.close();
	}

	private BigDecimal calculateRating(Vendor vendor, BigDecimal feedback) {
		return (vendor.getRating().add(feedback)).divide(new BigDecimal(vendor.getFeedbacks()));
		
	}

	public void addRating(Vendor vendor) {
		// TODO Auto-generated method stub
		
	}

	public void deleteVendor(Vendor vendor) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.delete(vendor);
		tx.commit();
		session.close();
	}

	public Vendor getVendorByEmail(String email) {
		List<Vendor> vendors = new ArrayList<Vendor>();
		Session session = this.sessionFactory.openSession();
		Query createQuery = session.createQuery("from Vendor where email=:email");
		createQuery.setString("email", email);
		vendors = createQuery.list();
		session.close();
		if(CollectionUtils.isEmpty(vendors)) {
			return null;
		}
		return vendors.get(0);
	}
	
	public Vendor getVendorByPhone(String phone) {
		List<Vendor> vendors = new ArrayList<Vendor>();
		Session session = this.sessionFactory.openSession();
		Query createQuery = session.createQuery("from Vendor where phone=:phone");
		createQuery.setString("phone", phone);
		vendors = createQuery.list();
		session.close();
		if(CollectionUtils.isEmpty(vendors)) {
			return null;
		}
		return vendors.get(0);
	}

	public List<com.rns.tiffeat.web.bo.domain.Meal> getVendorMeals(Vendor registeredVendor) {
		List<com.rns.tiffeat.web.bo.domain.Meal> meals = new ArrayList<com.rns.tiffeat.web.bo.domain.Meal>();
		Session session = this.sessionFactory.openSession();
		session.update(registeredVendor);
		for(Meal meal: registeredVendor.getMeals()) {
			meals.add(DataToBusinessConverters.convertMeal(meal));
		}
		session.close();
		return meals;
	}
	
	/*private void addCurrentMeal(Meal meal,List<com.rns.tiffeat.web.bo.domain.Meal> meals) {
		com.rns.tiffeat.web.bo.domain.Meal currentMeal = new com.rns.tiffeat.web.bo.domain.Meal();
		currentMeal.setId(meal.getId());
		currentMeal.setStatus(CommonUtil.getMealPhase(meal.getStatus()));
		currentMeal.setTitle(meal.getTitle());
		currentMeal.setDescription(meal.getDescription());
		currentMeal.setPrice(meal.getPrice());
		DataToBusinessConverters.convertMeal(meal);
		// TODO : Other Parameters
		meals.add(currentMeal);
	}*/

	public List<Vendor> getVendorsByArea(String pinCode) {
		List<Vendor> vendors = new ArrayList<Vendor>();
		Session session = this.sessionFactory.openSession();
		Query createQuery = session.createQuery("from Vendor where pinCode=:pinCode");
		createQuery.setString("pinCode", pinCode);
		vendors = createQuery.list();
		session.close();
		return vendors;
	}

}
