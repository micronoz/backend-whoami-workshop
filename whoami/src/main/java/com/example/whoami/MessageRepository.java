package com.example.whoami;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface MessageRepository extends CrudRepository<Message, Long> {
    @Query("SELECT m FROM Message m WHERE :id = m.senderId OR :id = m.receiverId")
    List<Message> findBySenderIdOrReceiverId(@Param("id") String id);
}