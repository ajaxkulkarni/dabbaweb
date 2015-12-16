package com.rns.tiffeat.web.dao.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "daily_meal")
public class DailyMeal implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "daily_meal_id")
	private long id;
	
	@Column(name = "main_item", length = 50)
	private String mainCourse;
	
	@Column(name = "sub_item1", length = 50)
	private String subItem1;
	
	@Column(name = "sub_item2", length = 50)
	private String subItem2;
	
	@Column(name = "sub_item3", length = 50)
	private String subItem3;
	
	@Column(name = "sub_item4", length = 50)
	private String subItem4;
	
	//LUNCH/DINNER
	@Column(name = "type", length= 10)
	private String type;
	
	@Column(name = "status", length = 10)
	private String status;
	
	@Column(name = "meal_date")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date date;
	
	@ManyToOne(fetch = FetchType.EAGER, targetEntity = Meal.class,/*cascade = 
        {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}*/cascade = CascadeType.MERGE,optional = true)
	private Meal meal;
	
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getMainCourse() {
		return mainCourse;
	}

	public void setMainCourse(String mainCourse) {
		this.mainCourse = mainCourse;
	}

	public String getSubItem1() {
		return subItem1;
	}

	public void setSubItem1(String subItem1) {
		this.subItem1 = subItem1;
	}

	public String getSubItem2() {
		return subItem2;
	}

	public void setSubItem2(String subItem2) {
		this.subItem2 = subItem2;
	}

	public String getSubItem3() {
		return subItem3;
	}

	public void setSubItem3(String subItem3) {
		this.subItem3 = subItem3;
	}

	public String getSubItem4() {
		return subItem4;
	}

	public void setSubItem4(String subItem4) {
		this.subItem4 = subItem4;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public Meal getMeal() {
		return meal;
	}

	public void setMeal(Meal meal) {
		this.meal = meal;
	}
	
}
