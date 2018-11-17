package com.spring.boot.messenger.application.springbootmessengerapplication.message;
import java.util.*;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.spring.boot.messenger.application.springbootmessengerapplication.user.ResourceNotFoundException;
 
@Repository
@Transactional
public class MessageDAOService extends JdbcDaoSupport{
	
	@Autowired
	public MessageDAOService(DataSource dataSource) {
		this.setDataSource(dataSource);
	}
	
	public List<MessageRequest> getAllMessages(int page_num, int page_size){
		int startIndex = (page_num-1) * page_size;
		int rowsToFetch = page_size;
		String sql = "select * from message ORDER BY id DESC OFFSET ? Rows FETCH next ? rows only";
		Object[] params = new Object[] {startIndex,rowsToFetch};
		MessageMapper mapper = new MessageMapper();		
		return this.getJdbcTemplate().query(sql, params, mapper);		
	}
	
	public List<MessageRequest> getMessagesForReceiver(String receiver){	
		String sql = "select * from message where receiver = ? ORDER BY id DESC";
		Object[] params = new Object[] {receiver};
		MessageMapper mapper = new MessageMapper();
		List<MessageRequest> messageTable = this.getJdbcTemplate().query(sql, params, mapper);
		return messageTable;	
	}
	
	public void addMessage(MessageRequest message) {
		String sql = "insert into message(sender,receiver,text) values(?,?,?)";
		this.getJdbcTemplate().update(sql, message.getSender(),message.getReceiver(),message.getText());
	}
	
	public MessageRequest getMessageFromId(int id) {
		String sql = "select * from message where id = ?";
		Object[] params = new Object[] {id};
		MessageMapper mapper = new MessageMapper();
		try {
			return this.getJdbcTemplate().queryForObject(sql, params, mapper);
		}
		catch(EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	public void deleteMessage(int id) {
		String sql = "delete from message where id = ?";
		int delCount = this.getJdbcTemplate().update(sql,id);
		if(delCount == 0) {
			throw new ResourceNotFoundException("Message with this id not found");
		}
	}
	
	public void editMessage(String text, int id) {
		String sql = "update message set text = ? where id = ?";
		int updateCount = this.getJdbcTemplate().update(sql, text, id);
		if(updateCount == 0) {
			throw new ResourceNotFoundException("Message with this id not found");
		}
	}
}
