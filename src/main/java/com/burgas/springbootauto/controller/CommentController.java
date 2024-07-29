package com.burgas.springbootauto.controller;

import com.burgas.springbootauto.entity.car.Car;
import com.burgas.springbootauto.entity.communication.comment.Comment;
import com.burgas.springbootauto.entity.communication.comment.CommentNotification;
import com.burgas.springbootauto.entity.person.Person;
import com.burgas.springbootauto.service.car.CarService;
import com.burgas.springbootauto.service.communication.comment.CommentService;
import com.burgas.springbootauto.service.person.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final PersonService personService;
    private final CarService carService;
    private final CommentService commentService;
    private final SimpMessagingTemplate messagingTemplate;

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
