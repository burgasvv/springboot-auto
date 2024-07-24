package com.burgas.springbootauto.entity.comment;

import com.burgas.springbootauto.entity.car.Car;
import com.burgas.springbootauto.entity.person.Person;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String content;

    @JsonIgnore
    @OneToOne(mappedBy = "comment", cascade = CascadeType.ALL)
    private CommentNotification notification;

    public void setNotification(CommentNotification notification) {
        this.notification = notification;
        notification.setComment(this);
    }

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Person author;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;

    @Column(nullable = false)
    private String date;
}
