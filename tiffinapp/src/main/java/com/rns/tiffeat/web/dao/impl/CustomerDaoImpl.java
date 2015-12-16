
package com.rns.tiffeat.web.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.rns.tiffeat.web.dao.api.CustomerDao;
import com.rns.tiffeat.web.dao.domain.Customer;

public class CustomerDaoImpl implements CustomerDao {

	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	

	public void addCustomer(Customer customer) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.persist(customer);
		tx.commit();
		session.close();
	}

	public void editCustomer(Customer customer) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(customer);
		tx.commit();
		session.close();
	}

	public void deleteCustomer(Customer customer) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.delete(customer);
		tx.commit();
		session.close();
	}

	public Customer getCustomer(long id) {
		Session session = this.sessionFactory.openSession();
		List<Customer> customers = new ArrayList<Customer>();
		Query createQuery = session.createQuery("from Customer where id=:cust_id");
		createQuery.setLong("cust_id", id);
		customers = createQuery.list();
		session.close();
		if(CollectionUtils.isEmpty(customers)) {
			return null;
		}
		return customers.get(0);
	}

	public Customer getCustomerByEmail(String email) {
		Session session = this.sessionFactory.openSession();
		List<Customer> customers = new ArrayList<Customer>();
		Query createQuery = session.createQuery("from Customer where email=:email");
		createQuery.setString("email", email);
		customers = createQuery.list();
		session.close();
		if(CollectionUtils.isEmpty(customers)) {
			return null;
		}
		return customers.get(0);
	}

	public Customer getCustomerByPhone(String phone) {
		Session session = this.sessionFactory.openSession();
		List<Customer> customers = new ArrayList<Customer>();
		Query createQuery = session.createQuery("from Customer where phone=:phone");
		createQuery.setString("phone", phone);
		customers = createQuery.list();
		session.close();
		if(CollectionUtils.isEmpty(customers)) {
			return null;
		}
		return customers.get(0);
	}

}
