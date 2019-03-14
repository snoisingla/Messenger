package com.spring.boot.messenger.application.springbootmessengerapplication.otp;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Otps {
	
	@Id
	@Column(name = "contact_number", nullable = false)
	private String contactNumber;
	private Integer otp;
	private Timestamp expiryTime;
	
	protected Otps() {
		
	}

	public Otps(String contactNumber, Integer otp, Timestamp expiryTime) {
		this.otp = otp;
		this.contactNumber = contactNumber;
		this.expiryTime = expiryTime;
	}
	
	public Integer getOtp() {
		return otp;
	}
	
	public void setOtp(Integer otp) {
		this.otp = otp;
	}
	

	public String getContactNumber() {
		return contactNumber;
	}
	
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public Timestamp getExpiryTime() {
		return expiryTime;
	}

	public void setExpiryTime(Timestamp expiryTime) {
		this.expiryTime = expiryTime;
	}
	
	public boolean isValid(){
		Timestamp currentTime = new Timestamp(System.currentTimeMillis());
		return (getExpiryTime().after(currentTime)); //expired > current
		//doesn't compare milliseconds
	}
	
	@Override
    public boolean equals(Object obj){ 
          
    // checking if both the object references are  
    // referring to the same object. 
    if(this == obj) {
    	return true; 
    }
    // if(!(obj instanceof Geek)) return false; ---> avoid. 
    if(obj == null || obj.getClass()!= this.getClass()) {
        return false; 
    }
    // type casting of the argument.  
    Otps otp = (Otps) obj; 
          
        // comparing the state of argument with  
        // the state of 'this' Object. 
    return (otp.otp == this.otp && 
    		otp.contactNumber == this.contactNumber &&
    		otp.expiryTime == this.expiryTime); 
    } 
	

}
