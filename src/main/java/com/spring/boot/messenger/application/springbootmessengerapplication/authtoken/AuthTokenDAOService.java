package com.spring.boot.messenger.application.springbootmessengerapplication.authtoken;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.util.Calendar;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@Repository
public class AuthTokenDAOService extends JdbcDaoSupport{
	
	private static int ExpiryDurationInDays = 7;
	
	@Autowired
	public AuthTokenDAOService(DataSource dataSource) {
		this.setDataSource(dataSource);
	}
	
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
		return new Timestamp(cal.getTime().getTime());
	}
	
	public boolean isTokenExpired(Timestamp expiredTime){
		Timestamp currentTime = new Timestamp(System.currentTimeMillis());
		if(expiredTime.after(currentTime)){ //expired > current
			return false;
		}
		return true; //expired
	}
	
	public String addAndReturnToken(String contactNumber) {
		String sql = "insert into token values(?,?,?)";
		String contactnumber = contactNumber;
		String authtoken = generateToken();
		Timestamp expirytime = findTokenExpiryTime();
		this.getJdbcTemplate().update(sql,contactnumber,authtoken,expirytime);
		return authtoken;
	}
		
//	public String findAuthToken(String contactnumber) { //may have to delete this method
//		System.out.println(contactnumber);
//		String sql = "select * from token where contactnumber = ?";
//		Object[] params = new Object[] {contactnumber};
//		AuthTokenMapper mapper = new AuthTokenMapper();
//		try {
//			AuthTokenImplementation authToken = this.getJdbcTemplate().queryForObject(sql, params, mapper);
//			return authToken.getAuthToken();
//		}
//		catch (EmptyResultDataAccessException e){
//			return null;
//		}
//	}
	
	public String findContactForAuthToken(String authToken) {
		String sql = "select * from token where authtoken = ?";
		Object[] params = new Object[] {authToken};
		AuthTokenMapper mapper = new AuthTokenMapper();
		try {
			AuthTokenImplementation token = this.getJdbcTemplate().queryForObject(sql, params, mapper);
			return token.getContactNumber();
		}
		catch (EmptyResultDataAccessException e) {
			return null;
		}	
	}
	
	public boolean isTokenValid(String authToken) {
		//return true;
		String sql = "select * from token where authtoken = ?";
		Object[] params = new Object[] {authToken};
		AuthTokenMapper mapper = new AuthTokenMapper();
		try {
			AuthTokenImplementation token = this.getJdbcTemplate().queryForObject(sql, params, mapper);
			Timestamp expiryTime = token.getExpiryTime();
			Boolean isExpired = isTokenExpired(expiryTime);
			if(isExpired) {
				return false;
			}
			return true;
		}
		catch (EmptyResultDataAccessException e) {
			return false;
		}
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
	
//	public String generateToken() {
//	return "abcd";
	
//	SecureRandom random = new SecureRandom();
//	byte bytes[] = new byte[20];
//	random.nextBytes(bytes);
//	String token = "";
//	try {
//		token = new String(bytes, "UTF-8");
//	} catch (UnsupportedEncodingException e) {
//		e.printStackTrace();
//	}
//	return token;		
//}
}
