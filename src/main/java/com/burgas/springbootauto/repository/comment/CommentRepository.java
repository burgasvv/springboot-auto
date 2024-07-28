package com.burgas.springbootauto.repository.comment;

import com.burgas.springbootauto.entity.communication.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
