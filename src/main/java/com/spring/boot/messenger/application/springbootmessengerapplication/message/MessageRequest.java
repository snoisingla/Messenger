package com.spring.boot.messenger.application.springbootmessengerapplication.message;


public class MessageRequest {
	
	private int id;
	private String sender;
	private String receiver;
	private String text;
	
	
	
public MessageRequest(String sender, String receiver, String text) {
		super();
		this.sender = sender;
		this.receiver = receiver;
		this.text = text;
	}
//	public MessageRequest(int id, String sender, String receiver, String text) {
//		this.id = id;
//		this.sender = sender;
//		this.receiver = receiver;
//		this.text = text;
//	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	

}
