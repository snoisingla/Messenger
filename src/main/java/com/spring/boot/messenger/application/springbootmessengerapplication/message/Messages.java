package com.spring.boot.messenger.application.springbootmessengerapplication.message;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring.boot.messenger.application.springbootmessengerapplication.user.Users;

@Entity
public class Messages {
	
	@Id
	@GeneratedValue
	@JsonIgnore
	private long id;
	
	private String text;
	
	@ManyToOne
	private Users sender;
	
	@ManyToOne
	private Users receiver;	
	
	protected Messages() {
		
	}
	
	public Messages(Users sender, Users receiver, String text) {
		super();
		this.sender = sender;
		this.receiver = receiver;
		this.text = text;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Users getSender() {
		return sender;
	}

	public void setSender(Users sender) {
		this.sender = sender;
	}

	public Users getReceiver() {
		return receiver;
	}

	public void setReceiver(Users receiver) {
		this.receiver = receiver;
	}

}
