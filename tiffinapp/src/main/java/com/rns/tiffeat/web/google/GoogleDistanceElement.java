package com.rns.tiffeat.web.google;

public class GoogleDistanceElement {

	private GoogleDistance distance;
	private GoogleDistance duration;

	public GoogleDistance getDistance() {
		return distance;
	}

	public void setDistance(GoogleDistance distance) {
		this.distance = distance;
	}

	public GoogleDistance getDuration() {
		return duration;
	}

	public void setDuration(GoogleDistance duration) {
		this.duration = duration;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	private String status;

}
