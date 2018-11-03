package com.spring.boot.messenger.application.springbootmessengerapplication.message;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MessagesNotFoundException extends RuntimeException {

	public MessagesNotFoundException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

}
