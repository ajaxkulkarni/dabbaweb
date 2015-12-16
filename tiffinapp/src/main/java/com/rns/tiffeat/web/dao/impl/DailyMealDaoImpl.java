package com.rns.tiffeat.web.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.rns.tiffeat.web.bo.domain.MealType;
import com.rns.tiffeat.web.dao.api.DailyMealDao;
import com.rns.tiffeat.web.dao.domain.DailyMeal;
import com.rns.tiffeat.web.util.CommonUtil;

public class DailyMealDaoImpl implements DailyMealDao {

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void addDailyMeal(DailyMeal dailyMeal) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.merge(dailyMeal);
		tx.commit();
		session.close();
	}

	public void updateDailyMeal(DailyMeal dailyMeal) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(dailyMeal);
		tx.commit();
		session.close();
	}

	public void deleteDailyMeal(DailyMeal dailyMeal) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.delete(dailyMeal);
		tx.commit();
		session.close();
	}

	public List<DailyMeal> getDailyMealsForVendor(long vendorId, Date date) {
		List<DailyMeal> meals = new ArrayList<DailyMeal>();
		Session session = this.sessionFactory.openSession();
		Query createQuery = session
				.createQuery("from DailyMeal where meal.vendor.id=:vendor_id AND date=:input_date ORDER BY id DESC");
		createQuery.setLong("vendor_id", vendorId);
		createQuery.setDate("input_date", date);
		meals = createQuery.list();
		session.close();
		return meals;
	}

	public DailyMeal getDailyMealsForMeal(long mealId, Date date) {
		List<DailyMeal> meals = new ArrayList<DailyMeal>();
		Session session = this.sessionFactory.openSession();
		Query createQuery = session
				.createQuery("from DailyMeal where meal.id=:meal_id AND date=:input_date ORDER By id DESC");
		createQuery.setLong("meal_id", mealId);
		createQuery.setDate("input_date", date);
		meals = createQuery.list();
		session.close();
		if (CollectionUtils.isEmpty(meals)) {
			return null;
		}
		return meals.get(0);
	}

	public DailyMeal getDailyMealsForMealType(long mealId, Date date, MealType mealType) {
		List<DailyMeal> meals = new ArrayList<DailyMeal>();
		if(mealType == null) {
			return null;
		}
		Session session = this.sessionFactory.openSession();
		Query createQuery = session.createQuery("from DailyMeal where meal.id=:meal_id AND date=:input_date AND type=:meal_type ORDER By id DESC");
		createQuery.setLong("meal_id", mealId);
		createQuery.setDate("input_date", date);
		createQuery.setString("meal_type", mealType.name());
		meals = createQuery.list();
		session.close();
		if (CollectionUtils.isEmpty(meals)) {
			return null;
		}
		return meals.get(0);
	}

	public DailyMeal getDailyMeal(long id) {
		List<DailyMeal> meals = new ArrayList<DailyMeal>();
		Session session = this.sessionFactory.openSession();
		Query createQuery = session.createQuery("from DailyMeal where id=:id");
		createQuery.setLong("id", id);
		meals = createQuery.list();
		session.close();
		if (CollectionUtils.isEmpty(meals)) {
			return null;
		}
		return meals.get(0);
	}

	public DailyMeal getDailyMealsForMealType(long mealId, MealType mealType) {
		List<DailyMeal> meals = new ArrayList<DailyMeal>();
		if(mealType == null) {
			return null;
		}
		Session session = this.sessionFactory.openSession();
		Query createQuery = session.createQuery("from DailyMeal where meal.id=:meal_id AND type=:meal_type AND date>=:todays_date ORDER By id DESC");
		createQuery.setLong("meal_id", mealId);
		createQuery.setString("meal_type", mealType.name());
		createQuery.setDate("todays_date", new Date());
		//createQuery.setTimestamp("todays_date", new Date());
		//session.createCriteria(DailyMeal.class).add(Restrictions.ge("date", CommonUtil.convertDate(new Date())));
		meals = createQuery.list();
		session.close();
		if (CollectionUtils.isEmpty(meals)) {
			return null;
		}
		return meals.get(0);
	}

}
