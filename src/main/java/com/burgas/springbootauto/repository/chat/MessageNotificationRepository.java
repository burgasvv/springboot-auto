package com.burgas.springbootauto.repository.chat;

import com.burgas.springbootauto.entity.chat.MessageNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageNotificationRepository extends JpaRepository<MessageNotification, Long> {


}
