package com.spring.boot.messenger.application.springbootmessengerapplication.authtoken;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AuthTokenMapper implements RowMapper<AuthTokenImplementation>{

	@Override
	public AuthTokenImplementation mapRow(ResultSet rs, int rowNum) throws SQLException {
		String contactnumber = rs.getString("contactnumber");
		String authToken = rs.getString("authtoken");
		String expiryTime = rs.getString("expirytime");
		return new AuthTokenImplementation(contactnumber,authToken,expiryTime);
	}

}
