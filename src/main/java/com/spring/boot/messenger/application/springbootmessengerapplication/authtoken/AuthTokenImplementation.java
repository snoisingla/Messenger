package com.spring.boot.messenger.application.springbootmessengerapplication.authtoken;

public class AuthTokenImplementation {
	private String contactNumber;
	private String authToken;
	private String expiryTime;
	
	public AuthTokenImplementation(String contactNumber, String authToken, String expiryTime) {
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

	public String getExpiryTime() {
		return expiryTime;
	}

	public void setExpiryTime(String expiryTime) {
		this.expiryTime = expiryTime;
	}
}
