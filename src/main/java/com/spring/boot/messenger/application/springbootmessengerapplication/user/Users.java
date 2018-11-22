package com.spring.boot.messenger.application.springbootmessengerapplication.user;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring.boot.messenger.application.springbootmessengerapplication.message.Messages;

@Entity
public class Users {
	
	@Id
	@Column(name = "contact_number", nullable = false)
	private String contactNumber;
	
	private String name;
	
	@JsonIgnore
	private boolean verified = false;
	
	@OneToMany(mappedBy = "sender", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Messages> sentMessages;
	
	@OneToMany(mappedBy = "receiver", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Messages> receivedMessages;
	
	
	public List<Messages> getSentMessages() {
		return sentMessages;
	}

	public void setSentMessages(List<Messages> sentMessages) {
		this.sentMessages = sentMessages;
	}

	public List<Messages> getReceivedMessages() {
		return receivedMessages;
	}

	public void setReceivedMessages(List<Messages> receivedMessages) {
		this.receivedMessages = receivedMessages;
	}

	protected Users() {
	}
	
	public Users(String contactNumber, String name) {
		super();
		this.contactNumber = contactNumber;
		this.name = name;
	}

	public Users(String contactNumber, String name, boolean verified) {
		super();
		this.contactNumber = contactNumber;
		this.name = name;
		this.verified = verified;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isVerified() {
		return verified;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}
	
//	@Override
//	public String toString() {
//		return String.format("User [contactNumber=%s, name=%s, verified=%s]", contactNumber, name, verified);
//	}
}
