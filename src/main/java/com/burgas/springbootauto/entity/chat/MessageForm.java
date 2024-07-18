package com.burgas.springbootauto.entity.chat;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageForm {

    private String content;
    private String sender;
    private String receiver;
}
