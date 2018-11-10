package com.spring.boot.messenger.application.springbootmessengerapplication.message;
import java.util.*;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.spring.boot.messenger.application.springbootmessengerapplication.user.UserImplementation;
import com.spring.boot.messenger.application.springbootmessengerapplication.user.UserMapper;

 
@Repository
@Transactional
public class MessageDAOService extends JdbcDaoSupport{
	
	@Autowired
	public MessageDAOService(DataSource dataSource) {
		this.setDataSource(dataSource);
	}
	
	public List<MessageRequest> getAllMessages(){
		String sql = "select * from message";
		Object[] params = new Object[] {};
		MessageMapper mapper = new MessageMapper();		
		List<MessageRequest> messageTable = this.getJdbcTemplate().query(sql, params, mapper);
		return messageTable;
		
	}
	
	public List<MessageRequest> getMessagesForReceiver(String receiver){	
		String sql = "select * from message where receiver = ?";
		Object[] params = new Object[] {receiver};
		MessageMapper mapper = new MessageMapper();
		List<MessageRequest> messageTable = this.getJdbcTemplate().query(sql, params, mapper);
//		if(messageTable.size() >= 1) {	
			return messageTable;
//		}
//		throw new MessagesNotFoundException(receiver+", you have no messages.");	
	}
	
	public void addMessage(MessageRequest message) {
		String sql = "insert into message values(?,?,?)";
		String sender = message.getSender();
		String receiver = message.getReceiver();
		String text = message.getText();
		this.getJdbcTemplate().update(sql, sender,receiver,text);
	}
}
