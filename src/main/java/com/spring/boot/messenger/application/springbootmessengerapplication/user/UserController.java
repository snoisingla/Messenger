package com.spring.boot.messenger.application.springbootmessengerapplication.user;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import com.spring.boot.messenger.application.springbootmessengerapplication.authtoken.AuthTokenServiceImpl;
import com.spring.boot.messenger.application.springbootmessengerapplication.message.UnAuthorisedException;
import com.spring.boot.messenger.application.springbootmessengerapplication.otp.OtpServiceImpl;
import com.spring.boot.messenger.application.springbootmessengerapplication.otp.Otps;
import com.spring.boot.messenger.application.springbootmessengerapplication.otp.VerifyOtpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserServiceImpl userService;
	
	@Autowired
	private OtpServiceImpl otpService;
	
	@Autowired
	private AuthTokenServiceImpl authService;
	
	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping(path = "users/uploadImage") 
	public UploadImageResponse uploadImage(@RequestParam("file") MultipartFile file, 
			@RequestHeader String authToken) {
		boolean isTokenValid = authService.isTokenValid(authToken);
		if(isTokenValid) {
			String contact = authService.findContactForAuthToken(authToken);
			System.out.println("......................."+file); 
			//org.springframework.web.multipart.support.StandardMultipartHttpServletRequest$StandardMultipartFile@2f2200a1
			String fileName = userService.storeImageAndReturnFileName(file);
			String imageDownloadUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
					.path("/downloadImage/").path(fileName).toUriString();
			userService.saveUserImage(contact, imageDownloadUrl);
			return new UploadImageResponse(fileName, imageDownloadUrl, file.getContentType(), file.getSize());
		}
		throw new UnAuthorisedException();	
	}
	
	@GetMapping(path = "/downloadImage/{fileName}")
	public ResponseEntity<Resource> downloadImage(@PathVariable String fileName,
			HttpServletRequest request){
		Resource resource = userService.loadFileAsResource(fileName);
		String contentType = null;
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (IOException e) {
			logger.info("Could not determine file type.");
		}
		if(contentType == null) {
			contentType = "application/octect-stream";
		}
		return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
	}
	
	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping(path = "userExist/{contactNumber}")
	public boolean isUsersExists(@PathVariable String contactNumber) {
		return userService.isUserExist(contactNumber);
	}
	
	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping(path = "users")
	public ResponseEntity uploadProfile(@RequestBody Users user) {
		String userContact = userService.saveUsersProfile(user);
		if(userContact == null) {
			return new ResponseEntity(HttpStatus.CONFLICT);
		}	
		otpService.generateSaveAndSendOTP(user.getContactNumber());
		return new ResponseEntity(HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping(path = "users/verify")
	public VerifyOtpResponse verifyUser(@RequestBody Otps otpImplementation) {
		VerifyOtpResponse verifyResult = otpService.verifyOTP(otpImplementation.getContactNumber(),
				otpImplementation.getOtp());
		if(verifyResult.isVerified() == true) {
			userService.updateUser(otpImplementation.getContactNumber()); //set verified = true
			String token = authService.addAndReturnToken(otpImplementation.getContactNumber());
			verifyResult.setAuthToken(token);
		}
		return verifyResult;
	}
	
	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping(path = "users/{profileContactNumber}")
	public Users fetchUser(@PathVariable String profileContactNumber,@RequestHeader String authToken) {
		boolean isTokenValid = authService.isTokenValid(authToken);
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
	
	@PostMapping(path = "users/updateLastSeen")
	@CrossOrigin(origins = "http://localhost:3000")
	public void updateLastSeen(@RequestHeader String authToken) {
		boolean isTokenValid = authService.isTokenValid(authToken);
		if(isTokenValid) {
			String userContactNumber = authService.findContactForAuthToken(authToken);
			userService.updateLastSeen(userContactNumber);
		}
		else {
			throw new UnAuthorisedException();
		}
	}
}
