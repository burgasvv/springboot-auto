package com.burgas.springbootauto.entity.communication.chat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageNotification {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @SuppressWarnings("JpaDataSourceORMInspection")
    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "message_id")
    private Message message;

    @Column(nullable = false)
    private String sender;

    @Column(nullable = false)
    private String receiver;
}
