package com.spring.boot.messenger.application.springbootmessengerapplication.otp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.spring.boot.messenger.application.springbootmessengerapplication.user.UserNotFoundException;

import java.util.*;

import javax.sql.DataSource;

import java.text.SimpleDateFormat;

@Transactional
@Repository
public class OtpDAOService extends JdbcDaoSupport{
	
	@Autowired
	public OtpDAOService(DataSource dataSource){
		this.setDataSource(dataSource);
	}
	
	VerifyOtpResponse verifyOtpResponse;
	
	private Integer generateOTP() {
		return 1000 + new Random().nextInt(8999);
	}
	
	private String getCurrentTime() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm");
		Calendar calendar = Calendar.getInstance();
		return dateFormat.format(calendar.getTime());
	}
	
	private boolean isValidOtp(String otpGeneratedTime) {		
		 String currentTime = getCurrentTime();
		 SimpleDateFormat format = new SimpleDateFormat("hh:mm");  
			Date d1 = null;
			Date d2 = null;
			try {
			    d1 = format.parse(otpGeneratedTime);
			    d2 = format.parse(currentTime);
			} catch (Exception e) {
			    e.printStackTrace();
			}    
			long diff = d2.getTime() - d1.getTime();
			long diffMinutes = diff / (60 * 1000);
			//long diffHours = diff / (60 * 60 * 1000); 
			
			if(diffMinutes >= 5) {
				return false;
			}
			return true;
	}
	
	public OtpImplementation getUserWithOtpPresent(String contactnumber) {
		String sql = "select ot.contactnumber, ot.otp, ot.createdat from otptable ot where ot.contactnumber = ?";
		Object[] params = new Object[] {contactnumber};
		OtpMapper mapper = new OtpMapper();				
		try {
			OtpImplementation otpTable = this.getJdbcTemplate().queryForObject(sql, params, mapper);
			return otpTable;
		}
		catch (EmptyResultDataAccessException e) {
			return null;
		}		
	}
	
	public void generateSaveAndSendOTP(String contactNumber) {
		String currentTime = getCurrentTime();		
		OtpImplementation otpImpl = getUserWithOtpPresent(contactNumber);
		if (otpImpl == null) { //no otp found, add new entry : new user
			Integer otp = generateOTP();
			String sql = "insert into otptable values(?,?,?)";
			this.getJdbcTemplate().update(sql,contactNumber,otp,currentTime);
		} else if (!isValidOtp(otpImpl.getCreatedAt())) { //otp got expired
			Integer otp = generateOTP();
			otpImpl.setCreatedAt(currentTime);
			otpImpl.setOtp(otp);
		}
		sendOTPViaSMS(contactNumber,otpImpl);
	}
	
	private void sendOTPViaSMS(String contactNumber, OtpImplementation otp) {
		// TODO Auto-generated method stub
	}
	
	public VerifyOtpResponse verifyOTP(String contactnumber, Integer otp) {
		String sql = "select ot.contactnumber, ot.otp, ot.createdat from otptable ot where ot.contactnumber = ?";
		Object[] params = new Object[] {contactnumber};
		OtpMapper mapper = new OtpMapper();
		try {
			OtpImplementation otpTable = this.getJdbcTemplate().queryForObject(sql, params, mapper);
			if(!otpTable.getOtp().equals(otp)) {
				return new VerifyOtpResponse(false,false); //wrong otp
			}
			else if(!isValidOtp(otpTable.getCreatedAt())) {
				return new VerifyOtpResponse(true,false); //expired otp
			}
			return new VerifyOtpResponse(false,true); //correct otp
		}
		
		catch(EmptyResultDataAccessException e) {
			return null; //no user found with this contact
		}
	}	
	
	public List<OtpImplementation> retreiveAll(){
		String sql = "select * from otptable";
		Object[] params = new Object[] {};
		OtpMapper mapper = new OtpMapper();
		List<OtpImplementation> otpTable = this.getJdbcTemplate().query(sql, params, mapper);
		if(otpTable.size() >= 1) {	
			return otpTable;
		}
		else {
			throw new UserNotFoundException("No users found");
		}
	}
}

/*	client shows enter phone number and send otp screen
user enter phone number and click on send otp
client request server to send otp to user via sms with request parameter as phone number(Post/sendOTP)
client updates screen to enter otp and verify after server sends ok response
user enter otp
clients send request to server to verify otp with phone number and otp as request parameter and(GET/sendOTP/phoneno/otp)
true and false as response
if true, client goes to next screen
if false, client asks to re-enter correct otp, enable re-send otp button */