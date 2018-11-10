package com.spring.boot.messenger.application.springbootmessengerapplication.message;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.spring.boot.messenger.application.springbootmessengerapplication.authtoken.AuthTokenDAOService;
import com.spring.boot.messenger.application.springbootmessengerapplication.user.UserDAOService;
import com.spring.boot.messenger.application.springbootmessengerapplication.user.UserImplementation;

@RestController
public class MessageController {
	
	@Autowired
	private MessageDAOService service;
	
	@Autowired
	private AuthTokenDAOService authservice;
	
	@Autowired
	private UserDAOService userservice;
	
	@PostMapping(path = "messages")
	public void sendMessage(@RequestBody MessageImplementation message, @RequestHeader String authToken) {
		boolean isTokenValid = authservice.isTokenValid(authToken);
		if(isTokenValid) {
			service.addMessage(message);
		}
		else {
			throw new UnAuthorisedException();
		}
	}
		
	private List<HashMap<String,Object>> convertMessages(List<MessageImplementation> messages){
		List<HashMap<String,Object>> receivedMessageList = new ArrayList<>();
		for(MessageImplementation currentMessage : messages) {
			HashMap<String,Object> map = new HashMap<>();
			UserImplementation userDetails = userservice.fetchUserProfile(currentMessage.getSender());
			map.put("sender", userDetails);
			map.put("text",currentMessage.getText());
			receivedMessageList.add(map);
		}
		return receivedMessageList;
	}
	
	@GetMapping(path = "messages")
	public List<HashMap<String, Object>> receiveMessage(@RequestHeader String authToken) {
		boolean isTokenValid = authservice.isTokenValid(authToken);
		if(isTokenValid) {
			String receiver = authservice.findContactForAuthToken(authToken);
			if(receiver != null){
				List<MessageImplementation> messageListForReceiver = service.getMessagesForReceiver(receiver);
				return convertMessages(messageListForReceiver);
			}
		}
		throw new UnAuthorisedException();
	}
		
	
	@GetMapping(path = "allmessages")
	public List<MessageImplementation> retriveAllMessages() {
		return service.getAllMessages();
	}
}
