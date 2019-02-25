package com.spring.boot.messenger.application.springbootmessengerapplication;

import java.util.Calendar;
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

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthTokenServiceImplTests {
	
	@MockBean
	AuthTokenRepository authServiceMock;
	
	@Autowired
	AuthTokenServiceImpl authTokenServiceImpl;

	
	@Test
	public void test_findContactForAuthToken1() {
		String contact = "testContact";
		Tokens t = new Tokens(contact,"testAuth",getCustomTimestamp(5));
		when(authServiceMock.findByAuthToken("testAuth")).thenReturn(t);
		
		String actualContact = authTokenServiceImpl.findContactForAuthToken("testAuth");
		Assert.assertEquals(contact,actualContact);
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
		Tokens t = new Tokens("testContact","testAuth",getCustomTimestamp(-5));
		when(authServiceMock.findByAuthToken("testAuth")).thenReturn(t);
		
		boolean actual = authTokenServiceImpl.isTokenValid("testAuth");
		Assert.assertFalse(actual);
	}
	
	@Test
	public void test_isTokenValid_TokenNotPresent() {
		Tokens t = null;
		when(authServiceMock.findByAuthToken("testAuth")).thenReturn(t);
		
		boolean actual = authTokenServiceImpl.isTokenValid("testAuth");
		Assert.assertFalse(actual);
	}
	
	private Timestamp getCustomTimestamp(int durationInDays) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_WEEK, durationInDays); 
		Timestamp timeStamp = new Timestamp(cal.getTimeInMillis());
		return timeStamp;
	}
	
}
