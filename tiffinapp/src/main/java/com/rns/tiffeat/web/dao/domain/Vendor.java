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
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "vendor")
public class Vendor implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	@Column(name = "vendor_id")
	private long id;
	
	@Column(name = "name",length = 50)
	private String name;
	
	@Column(name = "email",length = 100, unique = true)
	private String email;
	
	@Column(name = "password",length = 50)
	private String password;
	
	@Column(name = "phone",length = 15)
	private String phone;
	
	@Column(name = "pincode",length= 15)
	private String pinCode;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "location")
	private String location;
	
	@Column(name = "location_x")
	private BigDecimal locationX;
	
	@Column(name = "location_y")
	private BigDecimal locationY;
	
	@Column(name = "balance")
	private BigDecimal balance;
	
	@Column(name = "rating",scale = 3)
	private BigDecimal rating;
	
	@Column(name = "feedbacks",scale = 10)
	private Integer feedbacks;
	
	@Column(name = "image",length = 100)
	private String image;
	
	@Column(name = "tiffin_limit")
	private Integer limit;
	
	@Column(name = "phase")
	private String phase;
	//@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = Meal.class)
	//@JoinTable(name = "VENDOR_MEAL", joinColumns = @JoinColumn(name = "vendor_id"), inverseJoinColumns = @JoinColumn(name = "meal_id"))
	//private Set<Meal> meals;
	
	@OneToMany(mappedBy = "vendor",cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = Meal.class, orphanRemoval = true)
	private List<Meal> meals;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPinCode() {
		return pinCode;
	}
	
	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}
	
	public String getLocation() {
		return location;
	}

	public void setLocationX(BigDecimal locationX) {
		this.locationX = locationX;
	}
	
	public BigDecimal getLocationX() {
		return locationX;
	}
	
	public void setLocationY(BigDecimal locationY) {
		this.locationY = locationY;
	}
	
	public BigDecimal getLocationY() {
		return locationY;
	}
	
	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public BigDecimal getRating() {
		return rating;
	}

	public void setRating(BigDecimal rating) {
		this.rating = rating;
	}
	
	public void setFeedbacks(Integer feedbacks) {
		this.feedbacks = feedbacks;
	}
	
	public Integer getFeedbacks() {
		return feedbacks;
	}
	

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	public Integer getLimit() {
		return limit;
	}
	
	public void setLimit(Integer limit) {
		this.limit = limit;
	}
	
	public String getPhase() {
		return phase;
	}
	
	public void setPhase(String phase) {
		this.phase = phase;
	}
	
	public List<Meal> getMeals() {
		return meals;
	}
	
	public void setMeals(List<Meal> meals) {
		this.meals = meals;
	}

	/*public Set<Meal> getMeals() {
		return meals;
	}

	public void setMeals(Set<Meal> meals) {
		this.meals = meals;
	}*/
	
}
