package com.spring.boot.messenger.application.springbootmessengerapplication.otp;

import java.sql.Timestamp;

public class OtpImplementation {
	private String contactNumber;
	private Integer otp;
	private Timestamp expiryTime;
	
	public OtpImplementation() {
		super();
	}

	public OtpImplementation(String contactNumber, Integer otp, Timestamp expiryTime) {
		this.otp = otp;
		this.contactNumber = contactNumber;
		this.expiryTime = expiryTime;
	}
	
	public Integer getOtp() {
		return otp;
	}
	
	public void setOtp(Integer otp) {
		this.otp = otp;
	}
	

	public String getContactNumber() {
		return contactNumber;
	}
	
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public Timestamp getExpiryTime() {
		return expiryTime;
	}

	public void setExpiryTime(Timestamp expiryTime) {
		this.expiryTime = expiryTime;
	}
	

}
