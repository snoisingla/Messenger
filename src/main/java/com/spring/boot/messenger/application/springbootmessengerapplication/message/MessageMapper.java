package com.spring.boot.messenger.application.springbootmessengerapplication.message;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class MessageMapper implements RowMapper<MessageRequest>{
	
	@Override
	public MessageRequest mapRow(ResultSet rs, int rowNum) throws SQLException {
		String sender = rs.getString("sender");
		String receiver = rs.getString("receiver");
		String text = rs.getString("text");
		
		return new MessageRequest(sender,receiver,text);
	}

}
