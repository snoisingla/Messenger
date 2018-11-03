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
	
	@PostMapping(path = "requestOtp")
	public void sendOTP(@RequestBody OtpImplementation userDetail) {
		otpService.generateSaveAndSendOTP(userDetail.getContactNumber());
	}
	
	@GetMapping(path = "requestOtp/{contactNumber}")
	public Integer getOTP(@PathVariable String contactNumber) {
		return otpService.getUserWithOtpPresent(contactNumber).getOtp();
	}
	
	@GetMapping(path = "verifyOtp/{contactNumber}/{otp}")
	public VerifyOtpResponse verifyOtp(@PathVariable String contactNumber, @PathVariable Integer otp) {
		return otpService.verifyOTP(contactNumber, otp);
	}
	
	@GetMapping(path = "/otps")
	public List<OtpImplementation> getAllProfiles(){ 
		return otpService.retreiveAll();
	}
}
