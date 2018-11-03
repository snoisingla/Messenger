package com.spring.boot.messenger.application.springbootmessengerapplication.message;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {
	
	@Autowired
	private MessageDAOService service;
	
	@PostMapping(path = "messages")
	public void sendMessage(@RequestBody MessageImplementation message) {
		System.out.println("controller");
		service.addMessage(message);
		//return response as 200
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
	
	@GetMapping(path = "messages/{receiver}")
	public List<HashMap<String,Object>> receiveMessage(@PathVariable String receiver) {
		//get request parameters
		//fetch messages from database
		//respond with json messages
		List<MessageImplementation> messageListForReceiver = service.getMessagesForReceiver(receiver);
		return convertMessages(messageListForReceiver);
	}
	
	@GetMapping(path = "messages")
	public List<MessageImplementation> retriveAllMessages() {
		return service.getAllMessages();
	}
	
	

}
