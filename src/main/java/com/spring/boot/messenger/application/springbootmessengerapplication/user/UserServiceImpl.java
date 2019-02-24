package com.spring.boot.messenger.application.springbootmessengerapplication.user;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
@Repository
@Transactional
public class UserServiceImpl {
	
	@Autowired
	private UserRepository userService;
	
	private Path fileStorageLocation = Paths.get("./uploads");
	
	@Autowired
	private EntityManager entityManager;
	
	public String storeImageAndReturnFileName(MultipartFile file) {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		try {
			Path targetLocation = this.fileStorageLocation.resolve(fileName);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			return fileName;
		} catch (IOException e) {
			return "Could not store file " + fileName + ". Please try again!" ;
		}		
	}
	
	public Resource loadFileAsResource(String fileName) {
		Path fileLocation = this.fileStorageLocation.resolve(fileName);
		try {
			Resource resource = new UrlResource(fileLocation.toUri());
			if(resource.exists()) {
				return resource;
			}
			else {
				return null;
			}
		} catch (MalformedURLException e) {
			return null;
		}
		
	}
	
	public void saveUsersProfile(Users user) {
		userService.save(user);
	}
	
	
	public boolean isUserPresent(String contactNumber) {
		return userService.existsById(contactNumber);
	}
	
	
	public void saveUsersProfile1(Users user) {
		try {
			entityManager.persist(user);
			entityManager.flush();
		}
		catch(PersistenceException e) {
			System.out.println("User already exists");
		}
	}
	
	public void saveUserImage(String contactNumber, String imageDownloadUrl) {
		Users user = userService.findById(contactNumber).get();
		user.setImageDownloadUrl(imageDownloadUrl);
		userService.save(user);
	}
	
	public Users fetchUserProfile(String contactnumber) {
		Optional<Users> user = userService.findById(contactnumber);
		if(!user.isPresent()) {
			throw new ResourceNotFoundException("User with "+contactnumber+ " not found ");
		}
		return user.get();
	}
	
	public void updateUser(String contactNumber) {
		Users user = userService.findById(contactNumber).get();
		user.setVerified(true);
		userService.save(user);
	}
	
	public List<Users> getAllProfiles(){
		return userService.findAll();
	}
	
	public void updateLastSeen(String contactNumber) {
		Users user = userService.findById(contactNumber).get();
		Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		user.setLastSeenAt(sdf.format(cal.getTime()));
	}
	

}
