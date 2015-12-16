package com.rns.tiffeat.web.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.rns.tiffeat.web.dao.api.TransactionDao;
import com.rns.tiffeat.web.dao.domain.Transaction;

public class TransactionDaoImpl implements TransactionDao {

	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void addTransaction(Transaction transaction) {
		Session session = this.sessionFactory.openSession();
		org.hibernate.Transaction tx = session.beginTransaction();
		session.persist(transaction);
		tx.commit();
		session.close();
	}
	
	public void pingDb() {
		Session session = this.sessionFactory.openSession();
		System.out.println("Ping the server...");
		/*Query createQuery = session.createQuery("from Vendor");
		createQuery.list();*/
		session.close();
	}

}
