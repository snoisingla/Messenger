package com.spring.boot.messenger.application.springbootmessengerapplication.authtoken;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {
	
	@Autowired
	private AuthTokenServiceImpl tokenService;
	
	@GetMapping(path = "token/{contactNumber}")
	public String isValid(@PathVariable String contactNumber) {
		return tokenService.addAndReturnToken(contactNumber);
	}
	
	

}
