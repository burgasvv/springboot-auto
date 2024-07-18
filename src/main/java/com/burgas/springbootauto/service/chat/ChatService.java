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

    @Transactional
    public Optional<Chat> findChatByPeople(List<Person> persons) {
        List<Long> list = persons.stream().map(Person::getId).toList();
        return chatRepository.findChatByPeople(list)
                .or(() -> {
                    Chat chat = Chat.builder().people(persons).build();
                    chatRepository.save(chat);
                    return Optional.of(chat);
                });
    }
}
