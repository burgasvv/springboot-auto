package com.burgas.springbootauto.entity.communication.cheer;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheerNotification {

    private String username;
    private String objectId;
}
