package com.burgas.springbootauto.service.chat;

import com.burgas.springbootauto.entity.chat.Chat;
import com.burgas.springbootauto.entity.person.Person;
import com.burgas.springbootauto.repository.chat.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;

    @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
    public Optional<Chat> findChatBySenderAndReceiver(List<Person> people, String sender, String receiver) {
        return chatRepository.findChatBySenderAndReceiver(sender, receiver)
                .or(() -> {
                    Optional<Chat> reversedChat = chatRepository.findChatBySenderAndReceiver(receiver, sender);
                    if (reversedChat.isEmpty()){
                        Chat chat = Chat.builder().people(people).sender(sender).receiver(receiver).build();
                        chatRepository.save(chat);
                        return Optional.of(chat);
                    }
                    return reversedChat;
                });
    }
}
