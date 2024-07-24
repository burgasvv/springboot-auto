package com.burgas.springbootauto.entity.comment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentNotification {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String objectId;

    @Column
    private String amount;
}
