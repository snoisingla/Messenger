package com.spring.boot.messenger.application.springbootmessengerapplication.message;

public class SendMessageRequest {
	
	private MessageImplementation message;
	private String authToken;
	
	public SendMessageRequest(MessageImplementation message, String authToken) {
		super();
		this.message = message;
		this.authToken = authToken;
	}

	public MessageImplementation getMessage() {
		return message;
	}

	public void setMessage(MessageImplementation message) {
		this.message = message;
	}

	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}
	
	
}
