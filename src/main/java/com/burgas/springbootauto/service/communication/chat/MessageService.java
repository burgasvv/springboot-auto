package com.burgas.springbootauto.service.communication.chat;

import com.burgas.springbootauto.entity.communication.chat.Chat;
import com.burgas.springbootauto.entity.communication.chat.Message;
import com.burgas.springbootauto.repository.communication.chat.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;

    public Message findById(Long id) {
        return messageRepository.findById(id).orElse(null);
    }

    @SuppressWarnings("unused")
    public List<Message> findMessagesByChat(Chat chat) {
        return messageRepository.findMessagesByChat(chat);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
    public Message save(Message message) {
        message.setDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd MMMM yyyy, HH:mm")));
        return messageRepository.save(message);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
    public void read(Message message) {
        message.setRead(true);
        messageRepository.save(message);
    }
}
