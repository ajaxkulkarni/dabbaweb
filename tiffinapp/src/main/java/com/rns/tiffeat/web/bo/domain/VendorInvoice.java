package com.rns.tiffeat.web.bo.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class VendorInvoice {

	private Vendor vendor;
	private List<CustomerOrder> dinnerOrders;
	private List<CustomerOrder> lunchOrders;
	private BigDecimal due;
	private BigDecimal profit;
	private String dateRange;

	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

	public List<CustomerOrder> getDinnerOrders() {
		if (dinnerOrders == null) {
			dinnerOrders = new ArrayList<CustomerOrder>();
		}
		return dinnerOrders;
	}

	public void setDinnerOrders(List<CustomerOrder> dinnerOrders) {
		this.dinnerOrders = dinnerOrders;
	}

	public List<CustomerOrder> getLunchOrders() {
		if (lunchOrders == null) {
			lunchOrders = new ArrayList<CustomerOrder>();
		}
		return lunchOrders;
	}

	public void setLunchOrders(List<CustomerOrder> lunchOrders) {
		this.lunchOrders = lunchOrders;
	}

	public BigDecimal getDue() {
		if (due == null) {
			due = BigDecimal.ZERO;
		}
		return due;
	}

	public void setDue(BigDecimal due) {
		this.due = due;
	}

	public String getDateRange() {
		return dateRange;
	}

	public void setDateRange(String dateRange) {
		this.dateRange = dateRange;
	}

	public BigDecimal getProfit() {
		return profit;
	}

	public void setProfit(BigDecimal profit) {
		this.profit = profit;
	}

}
