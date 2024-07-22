package com.burgas.springbootauto.entity.comment;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentForm {

    private String content;
    private String author;
    private String objectId;
}
