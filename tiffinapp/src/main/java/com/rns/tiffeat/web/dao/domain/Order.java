package com.rns.tiffeat.web.dao.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "orders")
public class Order implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "order_id")
	private long id;
	
	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE},optional = true)
	private CustomerMeal customerMeal;
	
	@Column(name = "status", length = 10)
	private String status;
	
	@Column(name = "meal_code", length = 10)
	private String mealCode;
	
	@Column(name = "order_date")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date date;
	
	@Column(name = "payment_type",length = 10)
	private String paymentType;
	
	@Column(name = "tran_id",length = 10)
	private String transactionId;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public void setCustomerMeal(CustomerMeal customerMeal) {
		this.customerMeal = customerMeal;
	}
	
	public CustomerMeal getCustomerMeal() {
		return customerMeal;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setMealCode(String mealCode) {
		this.mealCode = mealCode;
	}
	
	public String getMealCode() {
		return mealCode;
	}
	
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	
	public String getPaymentType() {
		return paymentType;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	
	public String getTransactionId() {
		return transactionId;
	}
	
}
