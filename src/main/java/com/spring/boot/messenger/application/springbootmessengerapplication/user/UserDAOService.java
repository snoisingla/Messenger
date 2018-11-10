package com.spring.boot.messenger.application.springbootmessengerapplication.user;
import java.util.*;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Component;

@Component
public class UserDAOService extends JdbcDaoSupport{
	
	@Autowired
	public UserDAOService(DataSource dataSource) {
		this.setDataSource(dataSource);
	}
		
	public void saveUsersProfile(UserImplementation user) {
		String sql = "insert into users values(?,?)";
		String name = user.getName();
		String contactnumber = user.getContactNumber();
		this.getJdbcTemplate().update(sql,contactnumber,name);
	}
	
	public UserImplementation fetchUserProfile(String contactnumber) {
		String sql = "select * from users us where us.contactnumber = ?";
		Object[] params = new Object[] {contactnumber};
		UserMapper mapper = new UserMapper();
		try {
			UserImplementation userTable = this.getJdbcTemplate().queryForObject(sql, params, mapper);
			return userTable;
		}
		catch (EmptyResultDataAccessException e) {
			throw new UserNotFoundException("User with "+contactnumber+ " not found ");
		}
	}	
	
	public List<UserImplementation> getAllProfiles(){
		String sql = "select * from users";
		Object[] params = new Object[] {};
		UserMapper mapper = new UserMapper();
		List<UserImplementation> userTable = this.getJdbcTemplate().query(sql, params, mapper);	
		return userTable;
	}
	
	public void updateUser(String contactNumber) {
		String sql = "update users set isverified = ? where contactnumber = ?";
		this.getJdbcTemplate().update(sql,true,contactNumber);		
	}
	//otp : verb end point name
		//client : enters phone number post/number
		//click on send otp 
		//server : send otp to phone  post/otp
		//client : get otp get/otp
		//client : enter otp 
		//server : verify if otp is correct
	
}
