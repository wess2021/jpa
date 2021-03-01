package com.example.jpa.controller;

import com.example.jpa.exception.ResourceNotFoundException;
import com.example.jpa.model.Comment;
import com.example.jpa.model.Post;
import com.example.jpa.repository.PostRepository;
import com.example.jpa.service.CommentService;
import com.example.jpa.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@SessionAttributes("comment")
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private PostService postService;

    public CommentController() {
    }

    public CommentService getCommentService() {
        return commentService;
    }

    public void setCommentService(CommentService commentService) {
        this.commentService = commentService;
    }

    public PostService getPostService() {
        return postService;
    }

    public void setPostService(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/createComment/{id}")
    public String showComment(@PathVariable Long id, Model model, Principal principal) {

        // Just curious  what if we get username from Principal instead of SecurityContext

        // the end of curiosity //

//        // get username of current logged in session user
//        String authUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        // find user by username

        // find post by id
        Optional<Post> postOptional = this.postService.getPostById(id);
        // if both optionals is present set user and post to a new comment and put former in the model

            Comment comment = new Comment();
            comment.setPost(postOptional.get());

            model.addAttribute("comment", comment);
            System.err.println("GET comment/{id}: " + comment + "/" + id); // for testing debugging purposes
            return "createComment";
        }


    @PostMapping("/comment")
    public String validateComment(@Valid @ModelAttribute Comment comment, BindingResult bindingResult, SessionStatus sessionStatus) {
        System.err.println("POST comment: " + comment); // for testing debugging purposes
        if (bindingResult.hasErrors()) {
            System.err.println("Comment did not validate");
            return "commentForm";
        } else {
            this.commentService.saveComment(comment);
            System.err.println("SAVE comment: " + comment); // for testing debugging purposes
            sessionStatus.setComplete();
            return "redirect:/seePost/" + comment.getPost().getId();
        }
    }

}