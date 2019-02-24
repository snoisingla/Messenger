package com.spring.boot.messenger.application.springbootmessengerapplication.otp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import java.sql.Timestamp;

@Service
@Transactional
@Repository
public class OtpServiceImpl{
	
	private static int ExpiryDurationInMinutes = 15;
	
	@Autowired
	private OtpRepository otpService;
	
	public Otps getUserOtpDetails(String contact) {
		Optional<Otps> otp = otpService.findById(contact);
		return (otp.isPresent()) ? otp.get() : null;		
	}
	
	public void generateSaveAndSendOTP(String contact) {
		Timestamp newExpiryTimestamp = findOtpExpiryTime();
		Otps otpDetails = getUserOtpDetails(contact);
		if (otpDetails == null) { //no otp found, add new entry : new user
			Integer otp = generateOTP();
			Otps otpValues = new Otps(contact,otp,newExpiryTimestamp);
			otpService.save(otpValues);
		} else if (otpDetails.isValid()) { //otp got expired
			Integer otp = generateOTP();
			otpDetails.setExpiryTime(newExpiryTimestamp);
			otpDetails.setOtp(otp);
		}
		sendOTPViaSMS(contact,otpDetails);
	}
	
	public VerifyOtpResponse verifyOTP(String contact, Integer otp) {
		Optional<Otps> otpValue = otpService.findById(contact);
		if(!otpValue.isPresent()) {
			return null;
		}
		else {
			if(!otpValue.get().getOtp().equals(otp)) {
				return new VerifyOtpResponse(false,false); //wrong otp
			}
			else if(otpValue.get().isValid()) {
				return new VerifyOtpResponse(true,false); //expired otp
			}
			return new VerifyOtpResponse(false,true); //correct otp
		}
	}	
	
	public List<Otps> retreiveAll(){
		return otpService.findAll();
	}
	
	private Integer generateOTP() {
		return 1000 + new Random().nextInt(8999);
	}
	
	private Timestamp findOtpExpiryTime(){
		Timestamp ts = new Timestamp(System.currentTimeMillis());
        Calendar cal = Calendar.getInstance();
		cal.setTime(ts);
		cal.add(Calendar.MINUTE, ExpiryDurationInMinutes);
		return new Timestamp(cal.getTime().getTime());
	}
	

	private void sendOTPViaSMS(String contact, Otps otp) {
		// TODO Auto-generated method stub
	}
}
