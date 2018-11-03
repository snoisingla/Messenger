package com.spring.boot.messenger.application.springbootmessengerapplication.otp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OtpController {
	
	@Autowired
	private OtpDAOService otpService;
	
	@PostMapping(path = "otps/request")
	public void sendOTP(@RequestBody OtpImplementation otpDetails) {
		otpService.generateSaveAndSendOTP(otpDetails.getContactNumber());
	}
	
	@GetMapping(path = "otps/{contactNumber}")
	public Integer getOTP(@PathVariable String contactNumber) {
		return otpService.getUserWithOtpPresent(contactNumber).getOtp();
	}
	
	@PostMapping(path = "otps/verify")
	public VerifyOtpResponse verifyOtp(@RequestBody OtpImplementation otpDetails) {
		return otpService.verifyOTP(otpDetails.getContactNumber(), otpDetails.getOtp());
	}
	
	@GetMapping(path = "otps")
	public List<OtpImplementation> getAllProfiles(){ 
		return otpService.retreiveAll();
	}
}
