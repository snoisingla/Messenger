package com.spring.boot.messenger.application.springbootmessengerapplication.user;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class UserMapper implements RowMapper<UserImplementation>{

	@Override
	public UserImplementation mapRow(ResultSet rs, int rowNum) throws SQLException {
		String contactnumber = rs.getString("contactnumber"); //values in "" are coloumn names from database
		String name = rs.getString("name");
		boolean verified = rs.getBoolean("isverified");
		return new UserImplementation(contactnumber,name,verified);
	}
	
}
