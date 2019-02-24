package com.spring.boot.messenger.application.springbootmessengerapplication;

import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.spring.boot.messenger.application.springbootmessengerapplication.otp.OtpRepository;
import com.spring.boot.messenger.application.springbootmessengerapplication.otp.OtpServiceImpl;
import com.spring.boot.messenger.application.springbootmessengerapplication.otp.Otps;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OtpServiceImplTests {
	
	@Autowired
	OtpServiceImpl otpServiceImpl;
	
	@MockBean
	OtpRepository otpMock;
	
	
	@Test
	public void test_getUserOtpDetails() {
		String contact = "testContact";
		Date parsedDate = new Date();
		Timestamp timeStamp = new java.sql.Timestamp(parsedDate.getTime());
		Otps otpDetails = new Otps(contact,1234,timeStamp);
		Optional<Otps> expected = Optional.of(otpDetails);
		
		when(otpMock.findById(contact)).thenReturn(expected);
		Otps actual = otpServiceImpl.getUserOtpDetails(contact);
		Assert.assertTrue(actual.equals(otpDetails));
		
	}
}
