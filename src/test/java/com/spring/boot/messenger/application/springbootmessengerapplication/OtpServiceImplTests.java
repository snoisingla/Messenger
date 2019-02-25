package com.spring.boot.messenger.application.springbootmessengerapplication;

import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.Calendar;
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
import com.spring.boot.messenger.application.springbootmessengerapplication.otp.VerifyOtpResponse;

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
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		Otps otpDetails = new Otps(contact,1234,timestamp);
		Optional<Otps> expected = Optional.of(otpDetails);
		
		when(otpMock.findById(contact)).thenReturn(expected);
		Otps actual = otpServiceImpl.getUserOtpDetails(contact);
		Assert.assertTrue(actual.equals(otpDetails));
	}
	
	@Test
	public void test_isValid() {
		Otps otp = new Otps("",1234,getCustomTimestamp(-5));
		boolean actual = otp.isValid();		
		Assert.assertFalse(actual);
	}
	
	@Test
	public void test_verifyOTP_WrongOtp() {
		String contact = "testContact";
		int correctOtp = 1234;
		int incorrectOtp = 2345;
		
		Otps otpDetails = new Otps(contact,correctOtp,getCustomTimestamp(5));
		Optional<Otps> expected = Optional.of(otpDetails);
		
		when(otpMock.findById(contact)).thenReturn(expected);
		VerifyOtpResponse otpResponse = otpServiceImpl.verifyOTP(contact, incorrectOtp);
		Assert.assertFalse(otpResponse.isVerified());
	}
	
	@Test
	public void test_verifyOTP_ExpiredOtp() {
		String contact = "testContact";
		int otp = 1234;
		Otps otpDetails = new Otps(contact,otp,getCustomTimestamp(-5));
		Optional<Otps> expected = Optional.of(otpDetails);
		
		when(otpMock.findById(contact)).thenReturn(expected);
		
		VerifyOtpResponse otpResponse = otpServiceImpl.verifyOTP(contact, otp);
		Assert.assertTrue(otpResponse.isExpired());		
	}
	
	@Test
	public void test_verifyOTP_CorrectOtp() {
		String contact = "testContact";
		int otp = 1234;
		Otps otpDetails = new Otps(contact,otp,getCustomTimestamp(5));
		Optional<Otps> expected = Optional.of(otpDetails);
		
		when(otpMock.findById(contact)).thenReturn(expected);
		VerifyOtpResponse otpResponse = otpServiceImpl.verifyOTP(contact, otp);
		Assert.assertFalse(otpResponse.isExpired());
		Assert.assertTrue(otpResponse.isVerified());
	}
	
	public void test_verifyOTP_NoOtp() {
		String contact = "testContact";
		int otp = 1234;
		Otps otpDetails = null;
		Optional<Otps> expected = Optional.of(otpDetails);
		
		when(otpMock.findById(contact)).thenReturn(expected);
		
		VerifyOtpResponse otpResponse = otpServiceImpl.verifyOTP(contact, otp);
		Assert.assertNull(otpResponse);		
	}

	private Timestamp getCustomTimestamp(int durationInDays) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_WEEK, durationInDays); 
		Timestamp timeStamp = new Timestamp(cal.getTimeInMillis());
		return timeStamp;
	}
	
}
