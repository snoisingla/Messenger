package com.spring.boot.messenger.application.springbootmessengerapplication.otp;

public class OtpImplementation {
	private String contactNumber;
	private Integer otp;
	private String createdAt;
	
	public OtpImplementation(String contactNumber, Integer otp, String createdAt) {
		this.otp = otp;
		this.contactNumber = contactNumber;
		this.createdAt = createdAt;
	}
	
	public Integer getOtp() {
		return otp;
	}
	
	public void setOtp(Integer otp) {
		this.otp = otp;
	}
	
	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getContactNumber() {
		return contactNumber;
	}
	
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	

}
