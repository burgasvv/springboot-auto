package com.burgas.springbootauto.entity.chat;

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
public class Message {

    @Id
    @GeneratedValue
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String content;

    @OneToOne(mappedBy = "message", cascade = CascadeType.ALL)
    @JsonIgnore
    private MessageNotification notification;

    public void setNotification(MessageNotification notification) {
        this.notification = notification;
        notification.setMessage(this);
    }

    @ManyToOne
    @JoinColumn(name = "chat_id")
    private Chat chat;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private Person sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private Person receiver;

    @Column(nullable = false)
    private boolean read;

    @Column(nullable = false)
    private String date;
}
