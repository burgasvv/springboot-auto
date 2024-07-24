package com.burgas.springbootauto.repository.comment;

import com.burgas.springbootauto.entity.comment.CommentNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentNotificationRepository extends JpaRepository<CommentNotification, Long> {
}
