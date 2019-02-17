package com.spring.boot.messenger.application.springbootmessengerapplication.message;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.spring.boot.messenger.application.springbootmessengerapplication.user.Users;


public interface MessageRepository extends JpaRepository<Messages,Long>{
	
	List<Messages> findByReceiverOrderByIdDesc(String receiver);
	  
	Page<Messages> findBySenderOrReceiverOrderByIdDesc(Users sender, Users receiver, Pageable pageable);
	
	List<Messages> findBySenderOrReceiverOrderByIdDesc(Users sender, Users receiver);
	
	@Query("select m from Messages m where (m.sender = ?1 AND m.receiver = ?2)"
			  		+ " OR (m.receiver = ?1 AND m.sender = ?2) order by id desc" )
	List<Messages> findBySenderAndReceiverOrReceiverAndSenderOrderByIdDesc(Users sender, Users receiver);
}
