package com.burgas.springbootauto.repository.chat;

import com.burgas.springbootauto.entity.communication.chat.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

    Optional<Chat> findChatBySenderAndReceiver(String sender, String receiver);
}
