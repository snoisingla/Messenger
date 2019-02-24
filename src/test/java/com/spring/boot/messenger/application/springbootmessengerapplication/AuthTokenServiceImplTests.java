package com.spring.boot.messenger.application.springbootmessengerapplication;

import java.util.Date;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.spring.boot.messenger.application.springbootmessengerapplication.authtoken.AuthTokenRepository;
import com.spring.boot.messenger.application.springbootmessengerapplication.authtoken.AuthTokenServiceImpl;
import com.spring.boot.messenger.application.springbootmessengerapplication.authtoken.Tokens;

import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthTokenServiceImplTests {
	
	@MockBean
	AuthTokenRepository authServiceMock;
	
	@Autowired
	AuthTokenServiceImpl authTokenServiceImpl;

	
	@Test
	public void test_findContactForAuthToken1() {

		String expectedContact = "testContact";
	    Date parsedDate = new Date();
		Timestamp pastTimeStamp = new java.sql.Timestamp(parsedDate.getTime());
		Tokens t = new Tokens(expectedContact,"testAuth",pastTimeStamp);
		when(authServiceMock.findByAuthToken("testAuth")).thenReturn(t);
		
		String actualContact = authTokenServiceImpl.findContactForAuthToken("testAuth");
		Assert.assertEquals(expectedContact,actualContact);
	}
	
	@Test
	public void test_findContactForAuthToken_TokenNotPresent() {

		Tokens t = null;
		when(authServiceMock.findByAuthToken("testAuth")).thenReturn(t);
		
		String actualContact = authTokenServiceImpl.findContactForAuthToken("testAuth");
		Assert.assertNull(actualContact);

	}
	
	@Test
	public void test_isTokenValid_tokenExpired() {
		String s = "2018-12-30 15:23:01.975"; //expired
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
	    Date parsedDate;
		try {
			parsedDate = dateFormat.parse(s);
			Timestamp pastTimeStamp = new java.sql.Timestamp(parsedDate.getTime());
			
			Tokens t = new Tokens("testContact","testAuth",pastTimeStamp);
			
			when(authServiceMock.findByAuthToken("testAuth")).thenReturn(t);
			boolean actual = authTokenServiceImpl.isTokenValid("testAuth");
			Assert.assertFalse(actual);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test_isTokenValid_TokenNotPresent() {
		Tokens t = null;
		when(authServiceMock.findByAuthToken("testAuth")).thenReturn(t);
		
		boolean actual = authTokenServiceImpl.isTokenValid("testAuth");
		Assert.assertFalse(actual);
	}
	
}
