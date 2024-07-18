package com.burgas.springbootauto.service.chat;

import com.burgas.springbootauto.entity.chat.Chat;
import com.burgas.springbootauto.entity.person.Person;
import com.burgas.springbootauto.repository.chat.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;

    @SuppressWarnings("unused")
    @Transactional
    public Optional<Chat> findChatByPeople(List<Person> people) {
        List<Long> list = people.stream().map(Person::getId).toList();
        return chatRepository.findChatByPeople(list)
                .or(() -> {
                    Chat chat = Chat.builder().people(people).build();
                    chatRepository.save(chat);
                    return Optional.of(chat);
                });
    }

    @Transactional
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
