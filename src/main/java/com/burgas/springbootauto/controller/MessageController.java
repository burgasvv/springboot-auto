package com.burgas.springbootauto.controller;

import com.burgas.springbootauto.entity.car.Car;
import com.burgas.springbootauto.entity.chat.Chat;
import com.burgas.springbootauto.entity.chat.Message;
import com.burgas.springbootauto.entity.chat.MessageForm;
import com.burgas.springbootauto.entity.comment.Comment;
import com.burgas.springbootauto.entity.comment.CommentForm;
import com.burgas.springbootauto.entity.person.Person;
import com.burgas.springbootauto.service.car.CarService;
import com.burgas.springbootauto.service.chat.ChatService;
import com.burgas.springbootauto.service.chat.MessageService;
import com.burgas.springbootauto.service.comment.CommentService;
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
    private final CarService carService;
    private final CommentService commentService;

    @MessageMapping("/private-message")
    public void getPrivateMessage(@Payload MessageForm messageForm) {
        Person sender = personService.findPersonByUsername(messageForm.getSender());
        Person receiver = personService.findPersonByUsername(messageForm.getReceiver());
        Chat chat = chatService.findChatBySenderAndReceiver(
                List.of(sender, receiver), sender.getUsername(), receiver.getUsername()).orElse(null);
        messageService.save(
                Message.builder().content(messageForm.getContent())
                .sender(sender).receiver(receiver).chat(chat)
                .build()
        );
        messagingTemplate.convertAndSendToUser(messageForm.getReceiver(), "/topic/private-notifications", messageForm);
        messagingTemplate.convertAndSendToUser(messageForm.getReceiver(), "/topic/private-messages", messageForm);
    }

    @MessageMapping("/car-comment")
    public void getCarComment(@Payload CommentForm commentForm) {
        Person author = personService.findPersonByUsername(commentForm.getAuthor());
        Car car = carService.findById(Long.valueOf(commentForm.getObjectId()));
        commentService.saveCarComment(
                Comment.builder().author(author).car(car).content(commentForm.getContent()).build()
        );
        messagingTemplate.convertAndSend("/queue/car-comments", commentForm);
    }
}
