package com.spring.boot.messenger.application.springbootmessengerapplication.otp;

public class VerifyOtpResponse {
	private boolean expired;
	private boolean verified;
	private String authToken;
	
	
	public VerifyOtpResponse() {
		super();
	}

	public VerifyOtpResponse(boolean expired, boolean verified) {
		this.expired = expired;
		this.verified = verified;
	}
	
	public VerifyOtpResponse(boolean expired, boolean verified, String authToken) {
		super();
		this.expired = expired;
		this.verified = verified;
		this.authToken = authToken;
	}
	
	public boolean isExpired() {	
		return expired;
	}
	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	public void setExpired(boolean expired) {
		this.expired = expired;
	}
	public boolean isVerified() {
		return verified;
	}
	public void setVerified(boolean verified) {
		this.verified = verified;
	}	
}
