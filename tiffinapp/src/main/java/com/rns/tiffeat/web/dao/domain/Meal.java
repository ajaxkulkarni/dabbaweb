package com.rns.tiffeat.web.dao.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "meal")
public class Meal implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	@Id
	@GeneratedValue
	@Column(name = "meal_id")
	private long id;
	
	@Column(name = "title",length = 50)
	private String title;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "type",length = 10)
	private String type;
	
	@Column(name = "image",length = 100)
	private String image;
	
	@Column(name = "rating")
	private BigDecimal rating;
	
	@Column(name = "feedbacks",scale = 10)
	private Integer feedbacks;
	
	@Column(name = "price")
	private BigDecimal price;
	
	@Column(name = "lunch_status",length = 10)
	private String lunchStatus;
	
	@Column(name = "dinner_status",length = 10)
	private String dinnerStatus;
	
	@ManyToOne(fetch = FetchType.EAGER, targetEntity = Vendor.class, cascade = CascadeType.MERGE)
	//@JoinTable(name = "VENDOR_MEAL", joinColumns = @JoinColumn(name = "meal_id"), inverseJoinColumns = @JoinColumn(name = "vendor_id"))
	private Vendor vendor;
	
	@OneToMany(mappedBy = "meal",cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = CustomerMeal.class, orphanRemoval = true)
	private List<CustomerMeal> customerMeals;
	
	@OneToMany(mappedBy = "meal",cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = DailyMeal.class, orphanRemoval = true)
	private List<DailyMeal> dailyContents;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public void setRating(BigDecimal rating) {
		this.rating = rating;
	}
	
	public BigDecimal getRating() {
		return rating;
	}
	
	public void setFeedbacks(Integer feedbacks) {
		this.feedbacks = feedbacks;
	}
	
	public Integer getFeedbacks() {
		return feedbacks;
	}
	
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	public BigDecimal getPrice() {
		return price;
	}
	
	public String getLunchStatus() {
		return lunchStatus;
	}
	
	public void setLunchStatus(String lunchStatus) {
		this.lunchStatus = lunchStatus;
	}
	
	public String getDinnerStatus() {
		return dinnerStatus;
	}
	
	public void setDinnerStatus(String dinnerStatus) {
		this.dinnerStatus = dinnerStatus;
	}
	
	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}
	
	public Vendor getVendor() {
		return vendor;
	}
	
	public void setCustomerMeals(List<CustomerMeal> customerMeals) {
		this.customerMeals = customerMeals;
	}
	
	public List<CustomerMeal> getCustomerMeals() {
		return customerMeals;
	}
	
	public void setDailyContents(List<DailyMeal> dailyContents) {
		this.dailyContents = dailyContents;
	}
	
	public List<DailyMeal> getDailyContents() {
		return dailyContents;
	}
	
}
