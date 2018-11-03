package com.spring.boot.messenger.application.springbootmessengerapplication.user;

public class UserImplementation {

	private String name;
	private String photo;
	private String contactNumber;
	private boolean verified;
	
	
	public UserImplementation() {
		super();
	}

	public UserImplementation(String name, String photo, String contactNumber) {
		this.name = name;
		this.photo = photo;
		this.contactNumber = contactNumber;
	}
	
	public UserImplementation(String name, String photo, String contactNumber, boolean verified) {
		this.name = name;
		this.photo = photo;
		this.contactNumber = contactNumber;
		this.verified = verified;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public boolean isVerified() {
		return verified;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}
	
	
}
