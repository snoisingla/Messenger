package com.spring.boot.messenger.application.springbootmessengerapplication.message;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Messages,Long>{
	
	List<Messages> findByReceiverOrderByIdDesc(String receiver);

}
