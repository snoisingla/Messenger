package com.spring.boot.messenger.application.springbootmessengerapplication.user;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring.boot.messenger.application.springbootmessengerapplication.message.Messages;

@Entity
public class Users {
	
	@Id
	@Column(name = "contact_number", nullable = false)
	private String contactNumber;
	
	@NotNull 
	private String name;
	
	@JsonIgnore
	private boolean verified = false;
	
	@OneToMany(mappedBy = "sender", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Messages> sentMessages;
	
	@OneToMany(mappedBy = "receiver", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Messages> receivedMessages;
	
	private String imageDownloadUrl;
	
	private String lastSeenAt;
	
	protected Users() {
	}
	
	public Users(String contactNumber, String name, String imageDownloadUrl, String lastSeenAt) {
		this.contactNumber = contactNumber;
		this.name = name;
		this.imageDownloadUrl = imageDownloadUrl;
		this.lastSeenAt = lastSeenAt;
	}
	
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

	public String getImageDownloadUrl() {
		return imageDownloadUrl;
	}

	public void setImageDownloadUrl(String imageDownloadUrl) {
		this.imageDownloadUrl = imageDownloadUrl;
	}

	public String getLastSeenAt() {
		return lastSeenAt;
	}

	public void setLastSeenAt(String lastSeenAt) {
		this.lastSeenAt = lastSeenAt;
	}
	
	
	
//	@Override
//	public String toString() {
//		return String.format("User [contactNumber=%s, name=%s, verified=%s]", contactNumber, name, verified);
//	}
}
