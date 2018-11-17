package com.spring.boot.messenger.application.springbootmessengerapplication.message;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.*;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.spring.boot.messenger.application.springbootmessengerapplication.authtoken.AuthTokenDAOService;
import com.spring.boot.messenger.application.springbootmessengerapplication.user.UserDAOService;
import com.spring.boot.messenger.application.springbootmessengerapplication.user.UserImplementation;

@RestController
public class MessageController {
	
	@Autowired
	private MessageDAOService messageservice;
	
	@Autowired
	private AuthTokenDAOService authservice;
	
	@Autowired
	private UserDAOService userservice;
	
	@PostMapping(path = "messages")
	public void sendMessage(@RequestBody MessageRequest message, @RequestHeader String authToken) {
		boolean isTokenValid = authservice.isTokenValid(authToken);
		if(isTokenValid) {
			messageservice.addMessage(message);
		}
		else {
			throw new UnAuthorisedException();
		}
	}
	
	private List<TreeMap<String,Object>> convertMessages(List<MessageRequest> messages){
		List<TreeMap<String,Object>> receivedMessageList = new ArrayList<>();
		for(MessageRequest currentMessage : messages) {
			TreeMap<String,Object> map = new TreeMap<>();
			UserImplementation userDetails = userservice.fetchUserProfile(currentMessage.getSender()); //TODO create separate Message class
			map.put("sender", userDetails);
			map.put("text",currentMessage.getText());
			receivedMessageList.add(map);
		}
		return receivedMessageList;
	}
	
	@GetMapping(path = "messages")
	public List<TreeMap<String, Object>> receiveMessage(@RequestHeader String authToken) {
		boolean isTokenValid = authservice.isTokenValid(authToken);
		if(isTokenValid) {
			String receiver = authservice.findContactForAuthToken(authToken);
			if(receiver != null){
				List<MessageRequest> messageListForReceiver = messageservice.getMessagesForReceiver(receiver);
				return convertMessages(messageListForReceiver);
			}
		}
		throw new UnAuthorisedException();
	}
	
	@GetMapping(path = "messages/{id}")
	public HashMap<String, Object> receiveMessageById(@PathVariable int id, @RequestHeader String authToken){
		boolean isTokenValid = authservice.isTokenValid(authToken);
		if(isTokenValid) {
			MessageRequest message = messageservice.getMessageFromId(id);
			String messageSender = authservice.findContactForAuthToken(authToken);
			if(message.getSender().equals(messageSender)){
				UserImplementation userDetails = userservice.fetchUserProfile(message.getReceiver()); 
				HashMap<String, Object> map = new HashMap<>();
				map.put("receiver",userDetails);
				map.put("text", message.getText());
				return map;
				//404 add
			}
		}
		throw new UnAuthorisedException("No message found with this id"); //add message error
	}
		
	@DeleteMapping(path = "messages/{id}")
	public void deleteMessageById(@PathVariable int id, @RequestHeader String authToken) {
		boolean isTokenValid = authservice.isTokenValid(authToken);
		if(isTokenValid) {
			String messageSender = authservice.findContactForAuthToken(authToken);
			MessageRequest message = messageservice.getMessageFromId(id);
			if(message.getSender().equals(messageSender)){
				messageservice.deleteMessage(id);
				return;
			}
		}
		throw new UnAuthorisedException("No message found with this id");
	}
	
	@PutMapping(path = "messages/{id}")
	public void editMessage(@RequestBody MessageRequest message, @PathVariable int id, @RequestHeader String authToken) {
		boolean isTokenValid = authservice.isTokenValid(authToken);
		if(isTokenValid) {
			String contactForToken = authservice.findContactForAuthToken(authToken);
			String messageSender =	messageservice.getMessageFromId(id).getSender();
			if(contactForToken.equals(messageSender)){
				messageservice.editMessage(message.getText(), id);
				return;
			}
		}
		throw new UnAuthorisedException();
	}
	
	@GetMapping(path = "allmessages")
	public List<MessageRequest> retriveAllMessages(@RequestParam(value = "page_num", required = false, defaultValue = "1") int page_num,
												@RequestParam(value = "page_size", required = false, defaultValue = "3") int page_size) {
		List<MessageRequest> messages = messageservice.getAllMessages(page_num,page_size);
		return messages;
	}
	
//	@GetMapping(path = "allmessages")
//	public ResponseEntity<List<MessageRequest>> retriveAllMessages() {
//		List<MessageRequest> message =  service.getAllMessages();
//		if (message.isEmpty()) {
//            return new ResponseEntity(HttpStatus.NO_CONTENT);
//            // You many decide to return HttpStatus.NOT_FOUND
//        }
//        return new ResponseEntity<List<MessageRequest>>(message, HttpStatus.OK);
//		
//	}
}
