package com.spring.boot.messenger.application.springbootmessengerapplication.message;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnAuthorisedException extends RuntimeException {
	
	public UnAuthorisedException() {
		super("AuthToken match error or Token got expired");
	}
	
	public UnAuthorisedException(String message) {
		super(message);
	}

}
