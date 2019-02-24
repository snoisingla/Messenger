//package com.spring.boot.messenger.application.springbootmessengerapplication;
//
//import org.junit.After;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//
//import com.spring.boot.messenger.application.springbootmessengerapplication.user.UserServiceImpl;
//import com.spring.boot.messenger.application.springbootmessengerapplication.user.Users;
//
//public class UserServiceImplTests {
//	
//	UserServiceImpl userServiceImpl;
//	
//	@Before
//	public void setUp() {
//		userServiceImpl = new UserServiceImpl();
//	}
//	
//	@Test
//	public void test_saveUsersProfile() {
//		String contact = "1234";
//		Users user = new Users(contact,"test");
//		userServiceImpl.saveUsersProfile(user);
//		String expected = contact;
//		String actual = userServiceImpl.fetchUserProfile(contact).getContactNumber();
//		Assert.assertEquals(expected,actual);
//	}
//	
//	@After
//	public void tearDown() {
//		userServiceImpl = null;
//	}
//}
