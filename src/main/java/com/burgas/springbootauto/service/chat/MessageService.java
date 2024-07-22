package com.burgas.springbootauto.service.chat;

import com.burgas.springbootauto.entity.chat.Chat;
import com.burgas.springbootauto.entity.chat.Message;
import com.burgas.springbootauto.repository.chat.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;

    @SuppressWarnings("unused")
    public List<Message> findMessagesByChat(Chat chat) {
        return messageRepository.findMessagesByChat(chat);
    }

    @Transactional
    public Message save(Message message) {
        message.setDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd MMMM yyyy, HH:mm")));
        return messageRepository.save(message);
    }
}
