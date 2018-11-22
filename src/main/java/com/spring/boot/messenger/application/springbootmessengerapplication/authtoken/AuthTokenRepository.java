package com.spring.boot.messenger.application.springbootmessengerapplication.authtoken;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthTokenRepository extends JpaRepository<Tokens,String>{
	
	Tokens findByAuthToken(String authToken);

}
