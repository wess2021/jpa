package com.example.jpa.service;

import com.example.jpa.model.Comment;
import com.example.jpa.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    public Comment saveComment(Comment comment) {
        return commentRepository.save(comment);
    }

    public Page<Comment> getCommentList(Long postId) {return (Page<Comment>) commentRepository.findAll();
    }
}
