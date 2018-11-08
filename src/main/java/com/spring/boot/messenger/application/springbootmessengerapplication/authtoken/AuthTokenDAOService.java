package com.spring.boot.messenger.application.springbootmessengerapplication.authtoken;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@Repository
public class AuthTokenDAOService extends JdbcDaoSupport{
	
	@Autowired
	public AuthTokenDAOService(DataSource dataSource) {
		this.setDataSource(dataSource);
	}
	
	public String generateToken() {
		return "abcd";
//		SecureRandom random = new SecureRandom();
//		byte bytes[] = new byte[20];
//		random.nextBytes(bytes);
//		String token = "";
//		try {
//			token = new String(bytes, "UTF-8");
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
//		return token;		
	}
	
	public String findTokenExpiryTime() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm");
		Calendar calendar = Calendar.getInstance();
		Date currentTime = calendar.getTime();
		calendar.setTime(currentTime);
		calendar.add(Calendar.MINUTE, 10);
	 	String expiryTime = dateFormat.format(calendar.getTime());
	 	return expiryTime;
	}
	
	public String addAndReturnToken(String contactNumber) {
		String sql = "insert into tokentable values(?,?,?)";
		String contactnumber = contactNumber;
		String authtoken = generateToken();
		String expirytime = findTokenExpiryTime();
		this.getJdbcTemplate().update(sql,contactnumber,authtoken,expirytime);
		return authtoken;
	}
	
	public String findAuthToken(String contactnumber) {
		System.out.println(contactnumber);
		String sql = "select * from tokentable where contactnumber = ?";
		Object[] params = new Object[] {contactnumber};
		AuthTokenMapper mapper = new AuthTokenMapper();
		AuthTokenImplementation authToken = this.getJdbcTemplate().queryForObject(sql, params, mapper);
		return authToken.getAuthToken();
	}
}
