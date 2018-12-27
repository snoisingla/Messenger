package com.spring.boot.messenger.application.springbootmessengerapplication.message;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.boot.messenger.application.springbootmessengerapplication.user.Users;


public interface MessageRepository extends JpaRepository<Messages,Long>{
	
	List<Messages> findByReceiverOrderByIdDesc(String receiver);
	  
	Page<Messages> findBySenderOrReceiverOrderByIdDesc(Users sender, Users receiver, Pageable pageable);
	
	List<Messages> findBySenderOrReceiverOrderByIdDesc(Users sender, Users receiver);

}
