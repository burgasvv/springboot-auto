package com.burgas.springbootauto.controller;

import com.burgas.springbootauto.entity.car.Car;
import com.burgas.springbootauto.entity.chat.Chat;
import com.burgas.springbootauto.entity.chat.Message;
import com.burgas.springbootauto.entity.chat.MessageNotification;
import com.burgas.springbootauto.entity.comment.Comment;
import com.burgas.springbootauto.entity.comment.CommentNotification;
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
    public void getPrivateMessage(@Payload MessageNotification messageNotification) {
        Person sender = personService.findPersonByUsername(messageNotification.getSender());
        Person receiver = personService.findPersonByUsername(messageNotification.getReceiver());
        personService.plusMessage(receiver);
        Chat chat = chatService.findChatBySenderAndReceiver(
                List.of(sender, receiver), sender.getUsername(), receiver.getUsername()).orElse(null);
        Message message = Message.builder().read(false).content(messageNotification.getContent())
                .sender(sender).receiver(receiver).chat(chat).build();
        message.setNotification(messageNotification);
        messageService.save(message);
        messagingTemplate.convertAndSendToUser(messageNotification.getReceiver(),
                "/topic/private-messages", messageNotification);
    }

    @MessageMapping("/read-message")
    public void getReadMessage(@Payload MessageNotification messageNotification) {
        Person receiver = personService.findPersonByUsername(messageNotification.getReceiver());
        personService.minusMessage(receiver);
        messageService.read(messageService.findById(Long.valueOf(messageNotification.getContent())));
        messagingTemplate.convertAndSendToUser(messageNotification.getReceiver(),
                "/topic/read-messages", messageNotification);
    }

    @MessageMapping("/car-comment")
    public void getCarComment(@Payload CommentNotification commentNotification) {
        Person author = personService.findPersonByUsername(commentNotification.getAuthor());
        Car car = carService.findById(Long.valueOf(commentNotification.getObjectId()));
        Comment comment = Comment.builder().author(author).car(car)
                .content(commentNotification.getContent()).build();
        comment.setNotification(commentNotification);
        commentService.save(comment);
        messagingTemplate.convertAndSend("/queue/car-comments", commentNotification);
    }
}
