package com.spring.boot.messenger.application.springbootmessengerapplication.user;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class UserMapper implements RowMapper<UserImplementation>{

	@Override
	public UserImplementation mapRow(ResultSet rs, int rowNum) throws SQLException {
		String name = rs.getString("name");
		String photo = rs.getString("photo");
		String contactnumber = rs.getString("contactnumber"); //values in "" are coloumn names from database
		boolean verified = rs.getBoolean("isverified");
		return new UserImplementation(name,photo,contactnumber,verified);
	}
	
}
