package com.spring.boot.messenger.application.springbootmessengerapplication.message;
import java.util.*;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
 
@Repository
@Transactional
public class MessageDAOService extends JdbcDaoSupport{
	
	@Autowired
	public MessageDAOService(DataSource dataSource) {
		this.setDataSource(dataSource);
	}
	
	public List<MessageRequest> getAllMessages(Integer page_num, Integer page_size){
		int startIndex = (page_num-1) * page_size;
		int endIndex = page_size;
		String sql = "select * from message ORDER BY id DESC OFFSET ? Rows FETCH next ? rows only";
		Object[] params = new Object[] {startIndex,endIndex};
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
		String sender = message.getSender();
		String receiver = message.getReceiver();
		String text = message.getText();
		this.getJdbcTemplate().update(sql, sender,receiver,text);
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
		this.getJdbcTemplate().update(sql,id);
	}
	
	public void editMessage(String text, int id) {
		String sql = "update message set text = ? where id = ?";
		this.getJdbcTemplate().update(sql, text, id);
		
	}
}
