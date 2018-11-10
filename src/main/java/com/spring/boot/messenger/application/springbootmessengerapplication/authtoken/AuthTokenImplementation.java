package com.spring.boot.messenger.application.springbootmessengerapplication.authtoken;

import java.sql.Timestamp;

public class AuthTokenImplementation {
	private String contactNumber;
	private String authToken;
	private Timestamp expiryTime;
	
	public AuthTokenImplementation(String contactNumber, String authToken, Timestamp expiryTime) {
		super();
		this.contactNumber = contactNumber;
		this.authToken = authToken;
		this.expiryTime = expiryTime;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	public Timestamp getExpiryTime() {
		return expiryTime;
	}

	public void setExpiryTime(Timestamp expiryTime) {
		this.expiryTime = expiryTime;
	}
}
