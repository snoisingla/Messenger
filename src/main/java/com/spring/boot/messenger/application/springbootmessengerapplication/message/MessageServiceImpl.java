package com.spring.boot.messenger.application.springbootmessengerapplication.message;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.spring.boot.messenger.application.springbootmessengerapplication.user.ResourceNotFoundException;
import com.spring.boot.messenger.application.springbootmessengerapplication.user.UserServiceImpl;
import com.spring.boot.messenger.application.springbootmessengerapplication.user.Users;

@Service
@Repository
@Transactional
public class MessageServiceImpl{
	
	@Autowired
	private MessageRepository messageService;
	
	@Autowired
	private UserServiceImpl userService;
	
	public Page<Messages> getPagination(int page_num, int page_size){
		PageRequest page = PageRequest.of(page_num-1,page_size,Sort.Direction.DESC, "id" );
		Page<Messages> message= messageService.findAll(page);
		System.out.println("ddd........"+message.get());
		return message;
	}
	
	public void addMessage(MessageRequest msgRequest) {
		Messages msg = new Messages();
		msg.setText(msgRequest.getText());
		msg.setSender(userService.fetchUserProfile(msgRequest.getSender()));
		msg.setReceiver(userService.fetchUserProfile(msgRequest.getReceiver()));
		messageService.save(msg);
	}
	
	public List<Messages> getMessagesForReceiver(String receiverContactNumber){ //delete it from here and directly implement in controller
		Users receiver = userService.fetchUserProfile(receiverContactNumber);
		return receiver.getReceivedMessages();
	}
	
	public Messages getMessageFromId(long id) {
		Optional<Messages> messages = messageService.findById(id);
		return (!messages.isPresent()) ? null : messages.get();
	}
	
	public void deleteMessage(long id) {
		try {
			messageService.deleteById(id);
		}
		catch(IllegalArgumentException e) {
			throw new ResourceNotFoundException("Message with this id not found");
		}
	}
	
	public void editMessage(String text, long id) {
		Optional<Messages> messages = messageService.findById(id);
		if(!messages.isPresent()) {
			throw new ResourceNotFoundException("Message with this id not found");
		}
		messages.get().setText(text);
		messageService.save(messages.get());
	}
	
	public Page<Messages> getAllMessages(int page_num, int page_size){
		return getPagination(page_num,page_size);
	}
	
	
	
//	public List<MessageRequest> getAllMessages(int page_num, int page_size){
//		int startIndex = (page_num-1) * page_size;
//		int rowsToFetch = page_size;
//		String sql = "select * from message ORDER BY id DESC OFFSET ? Rows FETCH next ? rows only";
//		Object[] params = new Object[] {startIndex,rowsToFetch};
//		MessageMapper mapper = new MessageMapper();		
//		return this.getJdbcTemplate().query(sql, params, mapper);		
//	}
	
}
