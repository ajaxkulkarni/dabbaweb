package com.rns.tiffeat.web.google;

import java.util.List;

public class GoogleDistanceResult {

	private List<String> destination_addresses;
	private List<String> origin_addresses;
	private List<GoogleDistanceRow> rows;


	public List<GoogleDistanceRow> getRows() {
		return rows;
	}

	public void setRows(List<GoogleDistanceRow> rows) {
		this.rows = rows;
	}

	public List<String> getDestination_addresses() {
		return destination_addresses;
	}

	public void setDestination_addresses(List<String> destination_addresses) {
		this.destination_addresses = destination_addresses;
	}

	public List<String> getOrigin_addresses() {
		return origin_addresses;
	}

	public void setOrigin_addresses(List<String> origin_addresses) {
		this.origin_addresses = origin_addresses;
	}

}
