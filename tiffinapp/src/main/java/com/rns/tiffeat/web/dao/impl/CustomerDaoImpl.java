
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
import com.rns.tiffeat.web.dao.domain.EmailActivation;

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

	public List<Customer> getAllCustomers() {
		Session session = this.sessionFactory.openSession();
		List<Customer> customers = new ArrayList<Customer>();
		Query createQuery = session.createQuery("from Customer");
		customers = createQuery.list();
		session.close();
		return customers;
	}

	public void addActivationCode(EmailActivation activation) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.persist(activation);
		tx.commit();
		session.close();
	}
	
	public List<EmailActivation> getActivationCodes(String email) {
		Session session = this.sessionFactory.openSession();
		List<EmailActivation> activations = new ArrayList<EmailActivation>();
		Query createQuery = session.createQuery("from EmailActivation where email=:email ORDER BY id DESC");
		createQuery.setString("email", email);
		activations = createQuery.list();
		session.close();
		return activations;
	}

	public Customer getCustomerByDevice(String deviceId) {
		Session session = this.sessionFactory.openSession();
		List<Customer> customers = new ArrayList<Customer>();
		Query createQuery = session.createQuery("from Customer where deviceId=:device_id");
		createQuery.setString("device_id", deviceId);
		customers = createQuery.list();
		session.close();
		if(CollectionUtils.isEmpty(customers)) {
			return null;
		}
		return customers.get(0);
	}

}
