package com.spring.boot.messenger.application.springbootmessengerapplication.message;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.spring.boot.messenger.application.springbootmessengerapplication.authtoken.AuthTokenDAOService;

@RestController
public class MessageController {
	
	@Autowired
	private MessageDAOService service;
	
	@Autowired
	private AuthTokenDAOService authservice;
	
	@PostMapping(path = "messages")
	public void sendMessage(@RequestBody SendMessageRequest message) {
		String authTokenFromServer = authservice.findAuthToken(message.getMessage().getSender());
		if(message.getAuthToken().equals(authTokenFromServer)) {
			service.addMessage(message.getMessage());
		}
		else {
			throw new UnAuthorisedException();
		}
	}
		
	private List<HashMap<String,Object>> convertMessages(List<MessageImplementation> messages){
		List<HashMap<String,Object>> receivedMessageList = new ArrayList<>();
		for(MessageImplementation currentMessage : messages) {
			HashMap<String,Object> map = new HashMap<>();			
				map.put("sender",currentMessage.getSender());
				map.put("text",currentMessage.getText());
				receivedMessageList.add(map);
		}
		return receivedMessageList;
	}
	
	@GetMapping(path = "messages/{receiver}/{authToken}")
	public List<HashMap<String,Object>> receiveMessage(@PathVariable String receiver, @PathVariable String authToken) {
		String authTokenFromServer = authservice.findAuthToken(receiver);
		if(authToken.equals(authTokenFromServer)) {
			List<MessageImplementation> messageListForReceiver = service.getMessagesForReceiver(receiver);
			return convertMessages(messageListForReceiver);
		}
		throw new UnAuthorisedException();
	}
	
	@GetMapping(path = "messages")
	public List<MessageImplementation> retriveAllMessages() {
		return service.getAllMessages();
	}
}
