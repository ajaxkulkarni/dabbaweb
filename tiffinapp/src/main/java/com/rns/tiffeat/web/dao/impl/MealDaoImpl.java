package com.rns.tiffeat.web.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.rns.tiffeat.web.dao.api.MealDao;
import com.rns.tiffeat.web.dao.domain.CustomerMeal;
import com.rns.tiffeat.web.dao.domain.Meal;
import com.rns.tiffeat.web.dao.domain.Order;

public class MealDaoImpl implements MealDao {

	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public Object getSessionFactory() {
		return sessionFactory;
	}

	public void addMeal(Meal meal) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.merge(meal);
		tx.commit();
		session.close();

	}
	
	public void editMeal(Meal meal) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.merge(meal);
		tx.commit();
		session.close();

	}
	
	public Meal getMeal(long id) {
		List<Meal> meals = new ArrayList<Meal>();
		Session session = this.sessionFactory.openSession();
		Query createQuery = session.createQuery("from Meal where id=:id");
		createQuery.setLong("id", id);
		meals = createQuery.list();
		session.close();
		if(CollectionUtils.isEmpty(meals)) {
			return null;
		}
		return meals.get(0);
	}

	public void deleteMeal(Meal meal) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.delete(meal);
		tx.commit();
		session.close();
	}

	public void editMeal(Meal mealToCook, List<Order> orders) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(mealToCook);
		if(CollectionUtils.isNotEmpty(orders)) {
			for(Order order:orders) {
				session.merge(order);
			}
		}
		tx.commit();
		session.close();
	}
	
	public void addCustomerMeals(List<CustomerMeal> meals, Meal meal) {
		Session session = this.sessionFactory.openSession();
		session.update(meal);
		meal.getCustomerMeals().addAll(meals);
		session.close();
	}
	
}
