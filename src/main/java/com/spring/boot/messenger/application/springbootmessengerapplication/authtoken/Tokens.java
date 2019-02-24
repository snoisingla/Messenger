package com.spring.boot.messenger.application.springbootmessengerapplication.authtoken;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Tokens {
	
	@Id
	@Column(name = "contact_number", nullable = false)
	private String contactNumber;
	
	private String authToken;
	private Timestamp expiryTime;
	
	protected Tokens() {
		
	}
	
	public Tokens(String contactNumber, String authToken, Timestamp expiryTime) {
		super();
		this.contactNumber = contactNumber;
		this.authToken = authToken;
		this.expiryTime = expiryTime;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public String getAuthToken() {
		return authToken;
	}
	
	private Timestamp getExpiryTime() {
		return expiryTime;
	}
	
	public boolean isValid(){
		Timestamp currentTime = new Timestamp(System.currentTimeMillis());
		return (getExpiryTime().after(currentTime)); //expired > current
	}
}
