package com.spring.boot.messenger.application.springbootmessengerapplication.authtoken;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.util.Calendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Repository
public class AuthTokenServiceImpl{
	
	private static int ExpiryDurationInDays = 7;
	private static int ExpiryDurationInMinutes = 5;
	
	@Autowired
	private AuthTokenRepository tokenService;
	
	
	public String generateToken() {
	    SecureRandom secureRandom = new SecureRandom();
	    byte[] token = new byte[16];
	    secureRandom.nextBytes(token);
	    return new BigInteger(1, token).toString(16); //hex encoding, for binary 2, for integers 10
	}
	
	public Timestamp findTokenExpiryTime(){
		Timestamp ts = new Timestamp(System.currentTimeMillis());
        Calendar cal = Calendar.getInstance();
		cal.setTime(ts);
		cal.add(Calendar.DAY_OF_WEEK, ExpiryDurationInDays);
		//cal.add(Calendar.MINUTE, ExpiryDurationInMinutes);
		return new Timestamp(cal.getTime().getTime());
	}
	
	public String addAndReturnToken(String contactNumber) {
		String authtoken = generateToken();
		Timestamp expirytime = findTokenExpiryTime();
		Tokens tokenValues = new Tokens(contactNumber,authtoken,expirytime);
		tokenService.save(tokenValues);
		return authtoken;
	}
	
	public String findContactForAuthToken(String authToken) {
		return tokenService.findByAuthToken(authToken).getContactNumber();
		//return (contact != null) ? tokenService.findByAuthToken(authToken).getContactNumber() : null;
	}
	
	public boolean isTokenValid(Timestamp expiredTime){
		Timestamp currentTime = new Timestamp(System.currentTimeMillis());
		if(expiredTime.after(currentTime)){ //expired > current
			return true;
		}
		return false; //expired
	}
	
	public boolean isTokenValid(String authToken) {
		Tokens token = tokenService.findByAuthToken(authToken);
		return (token == null) ? false : isTokenValid(token.getExpiryTime());		
	}
	
//	public String findTokenExpiryTime() {
//	SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm");
//	Calendar calendar = Calendar.getInstance();
//	Date currentTime = calendar.getTime();
//	calendar.setTime(currentTime);
//	calendar.add(Calendar.MINUTE, 10);
// 	String expiryTime = dateFormat.format(calendar.getTime());
// 	return expiryTime;
//}
	
}
