package com.spring.boot.messenger.application.springbootmessengerapplication;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.spring.boot.messenger.application.springbootmessengerapplication.user.UserImplementation;
import com.spring.boot.messenger.application.springbootmessengerapplication.user.UserMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTests {


	@Test
	public void testMapRow() throws SQLException {
		ResultSet test = mock(ResultSet.class);
		when(test.getString("contactnumber")).thenReturn("9873477561");
		when(test.getString("name")).thenReturn("snoi");
		when(test.getBoolean("isverified")).thenReturn(true);
		
		UserMapper mapper = new UserMapper();
		UserImplementation userResult = mapper.mapRow(test, 2);
		assertEquals(userResult.getName(), "snoi");
		assertEquals(userResult.getContactNumber(),"9873477561");
		assertEquals(userResult.isVerified(),true);
		
		
	
		//create ResultSet for input
		//result = call mapRow()
		//set expected result
		//assert
//		assertEquals(userResult.getName, "Snoi")
		
		fail("Not yet implemented");
	}

	private ResultSet mock(Class<ResultSet> class1) {
		// TODO Auto-generated method stub
		return null;
	}

}
