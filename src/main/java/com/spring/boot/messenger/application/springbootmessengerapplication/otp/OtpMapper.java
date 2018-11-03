package com.spring.boot.messenger.application.springbootmessengerapplication.otp;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class OtpMapper implements RowMapper<OtpImplementation>{

	@Override
	public OtpImplementation mapRow(ResultSet rs, int rowNum) throws SQLException {
		String contactnumber = rs.getString("contactnumber");
		Integer otp = rs.getInt("otp");
		String createdat = rs.getString("createdat");
		return new OtpImplementation(contactnumber,otp,createdat);
	}

}
