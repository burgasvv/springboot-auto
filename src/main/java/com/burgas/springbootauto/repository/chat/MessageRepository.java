package com.burgas.springbootauto.repository.chat;

import com.burgas.springbootauto.entity.communication.chat.Chat;
import com.burgas.springbootauto.entity.communication.chat.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findMessagesByChat(Chat chat);
}
