package com.rns.tiffeat.web.google;

import java.math.BigDecimal;

public class Location {

	private String address;
	private BigDecimal lat;
	private BigDecimal lng;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public BigDecimal getLat() {
		return lat;
	}

	public void setLat(BigDecimal lat) {
		this.lat = lat;
	}

	public BigDecimal getLng() {
		return lng;
	}

	public void setLng(BigDecimal lng) {
		this.lng = lng;
	}


}
