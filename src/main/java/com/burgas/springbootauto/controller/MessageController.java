package com.burgas.springbootauto.controller;

import com.burgas.springbootauto.entity.chat.Chat;
import com.burgas.springbootauto.entity.chat.Message;
import com.burgas.springbootauto.entity.chat.MessageForm;
import com.burgas.springbootauto.entity.person.Person;
import com.burgas.springbootauto.service.chat.ChatService;
import com.burgas.springbootauto.service.chat.MessageService;
import com.burgas.springbootauto.service.person.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MessageController {

    private final SimpMessagingTemplate messagingTemplate;
    private final MessageService messageService;
    private final PersonService personService;
    private final ChatService chatService;

    @MessageMapping("/message")
    public void getMessage(@Payload MessageForm messageForm) {
        messagingTemplate.convertAndSend("/topic/global-notifications", messageForm);
        messagingTemplate.convertAndSend("/topic/messages", messageForm);
    }

    @MessageMapping("/private-message")
    public void getPrivateMessage(@Payload MessageForm messageForm) {
        Person sender = personService.findPersonByUsername(messageForm.getSender());
        Person receiver = personService.findPersonByUsername(messageForm.getReceiver());
        Chat chat = chatService.findChatByPeople(List.of(sender, receiver)).orElse(null);
        messageService.save(
                Message.builder().content(messageForm.getContent())
                .sender(sender).receiver(receiver).chat(chat)
                .build()
        );
        messagingTemplate.convertAndSendToUser(messageForm.getReceiver(), "/topic/private-notifications", messageForm);
        messagingTemplate.convertAndSendToUser(messageForm.getReceiver(), "/topic/private-messages", messageForm);
    }
}
