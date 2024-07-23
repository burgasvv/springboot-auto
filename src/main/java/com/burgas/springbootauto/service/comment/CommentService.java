package com.burgas.springbootauto.service.comment;

import com.burgas.springbootauto.entity.comment.Comment;
import com.burgas.springbootauto.repository.comment.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    @Transactional
    public void save(final Comment comment) {
        comment.setDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd MMMM yyyy, HH:mm")));
        commentRepository.save(comment);
    }
}
