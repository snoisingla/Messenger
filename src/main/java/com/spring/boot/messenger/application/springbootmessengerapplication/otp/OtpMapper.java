package com.spring.boot.messenger.application.springbootmessengerapplication.otp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.springframework.jdbc.core.RowMapper;

public class OtpMapper implements RowMapper<OtpImplementation>{

	@Override
	public OtpImplementation mapRow(ResultSet rs, int rowNum) throws SQLException {
		String contactnumber = rs.getString("contactnumber");
		Integer otp = rs.getInt("otp");
		Timestamp expirytime = rs.getTimestamp("expirytime");
		return new OtpImplementation(contactnumber,otp,expirytime);
	}

}
