package com.spring.boot.messenger.application.springbootmessengerapplication.message;
import java.util.LinkedHashSet;
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
	
	public List<Messages> getSentMessages(String senderContactNumber){ //delete it from here and directly implement in controller
		Users receiver = userService.fetchUserProfile(senderContactNumber);
		return receiver.getSentMessages();
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
	
	public Page<Messages> getAllMessagesForAUser(String contact, int page_num, int page_size) {
		Users user = userService.fetchUserProfile(contact);
		PageRequest page = PageRequest.of(page_num-1,page_size,Sort.Direction.DESC, "id" );
		return messageService.findBySenderOrReceiverOrderByIdDesc(user,user, page);
	}
	
	public LinkedHashSet<Users> getAllMessagesForAUser(String contact) { //sent + inbox
		Users user = userService.fetchUserProfile(contact);
		List<Messages> messages =  messageService.findBySenderOrReceiverOrderByIdDesc(user,user);
		LinkedHashSet<Users> senderList = new LinkedHashSet<>();
		for(Messages message : messages) {
			Users friend = (message.getSender().getContactNumber().equals(contact)) ? message.getReceiver() : message.getSender();
			if(!senderList.contains(friend)) {
				senderList.add(friend);
			}	
		}
		return senderList;
	}

	public List<Messages> findConversationBetweenTwoUsers(String user1Contact, String user2Contact){
		Users user1 = userService.fetchUserProfile(user1Contact);
		Users user2 = userService.fetchUserProfile(user2Contact);
		return messageService.findBySenderAndReceiverOrReceiverAndSenderOrderByIdDesc(user1, user2);		
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
