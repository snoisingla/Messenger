package com.spring.boot.messenger.application.springbootmessengerapplication.user;

import java.util.List;

import com.spring.boot.messenger.application.springbootmessengerapplication.authtoken.AuthTokenServiceImpl;
import com.spring.boot.messenger.application.springbootmessengerapplication.message.UnAuthorisedException;
import com.spring.boot.messenger.application.springbootmessengerapplication.otp.OtpServiceImpl;
import com.spring.boot.messenger.application.springbootmessengerapplication.otp.Otps;
import com.spring.boot.messenger.application.springbootmessengerapplication.otp.VerifyOtpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
	@Autowired
	private UserServiceImpl userService;
	
	@Autowired
	private OtpServiceImpl otpService;
	
	@Autowired
	private AuthTokenServiceImpl authservice;
	
	@PostMapping(path = "users")
	public void uploadProfile(@RequestBody Users user) {
		userService.saveUsersProfile(user);
		otpService.generateSaveAndSendOTP(user.getContactNumber());
	}
	
	@PostMapping(path = "users/verify")
	public VerifyOtpResponse verifyUser(@RequestBody Otps otpImplementation) {
		VerifyOtpResponse verifyResult = otpService.verifyOTP(otpImplementation.getContactNumber(), otpImplementation.getOtp());
		if(verifyResult.isVerified() == true) {
			userService.updateUser(otpImplementation.getContactNumber());
			String token = authservice.addAndReturnToken(otpImplementation.getContactNumber());
			System.out.println(token);
			verifyResult.setAuthToken(token);
		}
		return verifyResult;
	}
	
	@GetMapping(path = "users/{profileContactNumber}")
	public Users fetchUser(@PathVariable String profileContactNumber,@RequestHeader String authToken) {
		boolean isTokenValid = authservice.isTokenValid(authToken);
		if(isTokenValid) {
			return userService.fetchUserProfile(profileContactNumber); 
		}
		else {
			throw new UnAuthorisedException();
		}
	}
	
	@GetMapping(path = "allusers")
	public List<Users> fetchAllProfiles(){
		return userService.getAllProfiles();
	}
}
