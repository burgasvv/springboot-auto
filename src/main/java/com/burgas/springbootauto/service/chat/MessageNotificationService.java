package com.burgas.springbootauto.service.chat;

import com.burgas.springbootauto.repository.chat.MessageNotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageNotificationService {

    private final MessageNotificationRepository messageNotificationRepository;


}
