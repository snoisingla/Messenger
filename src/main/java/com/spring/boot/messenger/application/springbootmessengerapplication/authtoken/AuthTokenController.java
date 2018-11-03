package com.spring.boot.messenger.application.springbootmessengerapplication.authtoken;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthTokenController {
	
	@Autowired
	private AuthTokenDAOService authTokenService;
	
	@GetMapping(path = "/auth")
	public String generateAuth() {
		return authTokenService.generateToken();
	}
	
	@PostMapping(path = "/addtoken")
	public void generateToken(@RequestBody AuthTokenImplementation authToken) {
		authTokenService.addAndReturnToken(authToken.getContactNumber());
	}
}
