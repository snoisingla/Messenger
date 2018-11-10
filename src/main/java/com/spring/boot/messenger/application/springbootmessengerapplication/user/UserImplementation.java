package com.spring.boot.messenger.application.springbootmessengerapplication.user;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserImplementation {
	
	private String contactNumber;
	private String name;
	
	@JsonIgnore
	private boolean verified;
	
	
	public UserImplementation() {
		super();
	}

	public UserImplementation(String contactNumber, String name, boolean verified) {
		super();
		this.contactNumber = contactNumber;
		this.name = name;
		this.verified = verified;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isVerified() {
		return verified;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}
}
