package com.spring.boot.messenger.application.springbootmessengerapplication.user;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Repository
@Transactional
public class UserServiceImpl {
	
	@Autowired
	private UserRepository userService;
	
	public void saveUsersProfile(Users user) {
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
	

}
