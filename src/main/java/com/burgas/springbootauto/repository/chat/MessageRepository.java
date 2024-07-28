package com.burgas.springbootauto.repository.chat;

import com.burgas.springbootauto.entity.communication.chat.Chat;
import com.burgas.springbootauto.entity.communication.chat.Message;
import com.burgas.springbootauto.entity.person.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findMessagesBySenderAndReceiver(Person sender, Person receiver);

    List<Message> findMessagesBySender(Person sender);

    List<Message> findMessagesByReceiver(Person receiver);

    List<Message> findMessagesByChat(Chat chat);
}
