package com.spring.boot.messenger.application.springbootmessengerapplication;

import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.spring.boot.messenger.application.springbootmessengerapplication.user.UserRepository;
import com.spring.boot.messenger.application.springbootmessengerapplication.user.UserServiceImpl;
import com.spring.boot.messenger.application.springbootmessengerapplication.user.Users;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplTests {
	
	@Autowired
	UserServiceImpl userServiceImpl;
	
	@MockBean
	UserRepository userService;
	
	@Test
	public void test_isUserPresent_Present() {
		String contact = "99999";
		when(userService.existsById(contact)).thenReturn(true);
		boolean actual = userServiceImpl.isUserPresent(contact);
		Assert.assertTrue(actual);
	}
	
	@Test
	public void test_isUserPresent_NotPresent() {
		String contact = "99999";
		when(userService.existsById(contact)).thenReturn(false);
		boolean actual = userServiceImpl.isUserPresent(contact);
		Assert.assertFalse(actual);
	}
	
	@Test
	public void test_fetchUserProfile() {
		String contact = "99999";
		Users user = new Users(contact,"test");
		Optional<Users> expected = Optional.of(user);
		when(userService.findById(contact)).thenReturn(expected);
		Users actual = userServiceImpl.fetchUserProfile(contact);
		Assert.assertEquals(actual.getContactNumber(),user.getContactNumber());
		Assert.assertEquals(actual.getName(),user.getName());
	}
	
	
//	@Test
//	public void test_saveUsersProfile() {
//		String contact = "1234";
//		Users user = new Users(contact,"test");
//		userServiceImpl.saveUsersProfile(user);
//		String expected = contact;
//		String actual = userServiceImpl.fetchUserProfile(contact).getContactNumber();
//		Assert.assertEquals(expected,actual);
//	}
	
	
}
