package com.spring.boot.messenger.application.springbootmessengerapplication.message;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.boot.messenger.application.springbootmessengerapplication.authtoken.AuthTokenServiceImpl;
import com.spring.boot.messenger.application.springbootmessengerapplication.user.UserServiceImpl;
import com.spring.boot.messenger.application.springbootmessengerapplication.user.Users;


@RestController
public class MessageController {
	
	@Autowired
	private MessageServiceImpl messageService;
	
	@Autowired
	private AuthTokenServiceImpl authService;
	
	@PostMapping(path = "messages")
	@CrossOrigin(origins = "http://localhost:3000")
	public void sendMessage(@RequestBody MessageRequest message, @RequestHeader String authToken) {
		boolean isTokenValid = authService.isTokenValid(authToken);
		if(isTokenValid) {
			String sender = authService.findContactForAuthToken(authToken);
			message.setSender(sender);
			messageService.addMessage(message);
		}
		else {
			throw new UnAuthorisedException();
		}
	}
	
	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping(path = "received-messages") //pagination required
	public List<Messages> receiveMessage(@RequestHeader String authToken) {
		boolean isTokenValid = authService.isTokenValid(authToken);
		if(isTokenValid) {
			String receiver = authService.findContactForAuthToken(authToken);
			return messageService.getMessagesForReceiver(receiver);
		}
		throw new UnAuthorisedException();	
	}
	
	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping(path = "sent-messages") //pagination required
	public List<Messages> sentMessages(@RequestHeader String authToken) {
		boolean isTokenValid = authService.isTokenValid(authToken);
		if(isTokenValid) {
			String sender = authService.findContactForAuthToken(authToken);
			return messageService.getSentMessages(sender);
		}
		throw new UnAuthorisedException();	
	}

	@GetMapping(path = "receive-message/{id}")
	public Messages receiveMessageById(@PathVariable int id, @RequestHeader String authToken){
		boolean isTokenValid = authService.isTokenValid(authToken);
		if(isTokenValid) {
			Messages message = messageService.getMessageFromId(id);
			String messageSender = authService.findContactForAuthToken(authToken);
			if(message.getSender().getContactNumber().equals(messageSender)){
				return message;
			}
		}
		throw new UnAuthorisedException("No message found with this id"); //add message error
	}
		
	@DeleteMapping(path = "delete-message/{id}")
	public void deleteMessageById(@PathVariable int id, @RequestHeader String authToken) {
		boolean isTokenValid = authService.isTokenValid(authToken);
		if(isTokenValid) {
			String messageSender = authService.findContactForAuthToken(authToken);
			Messages message = messageService.getMessageFromId(id);
			if(message.getSender().getContactNumber().equals(messageSender)){ //only sender can delete message
				messageService.deleteMessage(id);
				return;
			}
		}
		throw new UnAuthorisedException("No message found with this id");
	}
	
	@PutMapping(path = "edit-message/{id}")
	public void editMessage(@RequestBody MessageRequest message, @PathVariable int id, @RequestHeader String authToken) {
		boolean isTokenValid = authService.isTokenValid(authToken);
		if(isTokenValid) {
			String contactForToken = authService.findContactForAuthToken(authToken);
			String messageSender =	messageService.getMessageFromId(id).getSender().getContactNumber();
			if(contactForToken.equals(messageSender)){
				messageService.editMessage(message.getText(), id);
				return;
			}
		}
		throw new UnAuthorisedException();
	}
	
	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping(path = "db-messages")	//pagination required
	public Page<Messages> retriveAllMessages(@RequestParam(value = "page_num", required = false, defaultValue = "1") int page_num,
												@RequestParam(value = "page_size", required = false, defaultValue = "3") int page_size) {
		Page<Messages> messages = messageService.getPagination(page_num,page_size);
		return messages;
	}
	
	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping(path = "messages")
	public LinkedHashSet<Users> findMessages(@RequestHeader String authToken){
		boolean isTokenValid = authService.isTokenValid(authToken);
		if(isTokenValid) {
			String userContact = authService.findContactForAuthToken(authToken);
			return messageService.getAllMessagesForAUser(userContact);
		}
		throw new UnAuthorisedException();	
	}
	
	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping(path = "messages1") //sent + inbox
	public Page<Messages> findMessagesForUser(@RequestParam(value = "page_num", required = false, defaultValue = "1") int page_num,
			@RequestParam(value = "page_size", required = false, defaultValue = "3") int page_size,@RequestHeader String authToken){
		boolean isTokenValid = authService.isTokenValid(authToken);
		if(isTokenValid) {
			String userContact = authService.findContactForAuthToken(authToken);
			return messageService.getAllMessagesForAUser(userContact, page_num, page_size);
		}
		throw new UnAuthorisedException();	
	}
	
	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping(path = "messages/{userContact}")
	public List<Messages> findMessagesBetweenTwoUsers(@PathVariable String userContact, @RequestHeader String authToken){
		boolean isTokenValid = authService.isTokenValid(authToken);
		if(isTokenValid) {
			String signedInUserContact = authService.findContactForAuthToken(authToken);
			return messageService.findConversationBetweenTwoUsers(userContact, signedInUserContact);
		}
		throw new UnAuthorisedException();
	}
	
}

//@GetMapping(path = "allmessages")
//public ResponseEntity<List<MessageRequest>> retriveAllMessages() {
//	List<MessageRequest> message =  service.getAllMessages();
//	if (message.isEmpty()) {
//        return new ResponseEntity(HttpStatus.NO_CONTENT);
//        // You many decide to return HttpStatus.NOT_FOUND
//    }
//    return new ResponseEntity<List<MessageRequest>>(message,   .OK);
//	
//}
