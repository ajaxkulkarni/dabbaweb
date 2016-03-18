package com.rns.tiffeat.web.bo.domain;

public enum PaymentType {
	CASH("Cash on Delivery"),
	DEBIT("Debit card"),
	CREDIT("Credit card"),
	NETBANKING("Internet Banking"),
	ONLINE("Online");
	
	private String description;
	
	private PaymentType(String description) {
		setDescription(description);
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
