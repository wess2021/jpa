package com.example.jpa.controller;

import com.example.jpa.exception.ResourceNotFoundException;
import com.example.jpa.model.Post;
import com.example.jpa.repository.PostRepository;
import com.example.jpa.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
public class PostController {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private PostService postService;
    @Autowired
    public PostController() {

    }

    public PostRepository getPostRepository() {
        return postRepository;
    }

    public void setPostRepository(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public PostService getPostService() {
        return postService;
    }

    public void setPostService(PostService postService) {
        this.postService = postService;
    }

    @RequestMapping(value = { "/postList" }, method = RequestMethod.GET)
    public ModelAndView postListHome() {
        ModelAndView mav =new ModelAndView("postList");
     List<Post> postList=postService.getPostList();
        mav.addObject("postList",postList);

        return mav;
    }

    @GetMapping("/post")
    public String createPost(Model model) {

        Post post=new Post();
        model.addAttribute("post",post);
        return "createPost";

    }
    @PostMapping("/post")
    public String createdPost(@ModelAttribute("post") Post post) {

postService.savePost(post);
        return "created_succeded";

    }

    @GetMapping("/editPost/{id}")
    public String showUpdateFormm(@PathVariable("id") long id, Model model) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

        model.addAttribute("post", post);
        return "updatePost";
    }
    @PostMapping("/updatePost/{id}")
    public String updatePost(@PathVariable("id") long id, @Valid Post post,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            post.setId(id);
            return "updatePost";
        }
        postRepository.save(post);
        model.addAttribute("posts", postRepository.findAll());
        return "redirect:/postList";
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Long postId) {
        return postRepository.findById(postId).map(post -> {
            postRepository.delete(post);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("PostId " + postId + " not found"));
    }
    @GetMapping("/seePost/{id}")
    public String showBlog(@PathVariable("id") long id, Model model) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

        model.addAttribute("post", post);
        return "seePost";
    }

    @GetMapping("/deletePost/{id}")
    public String deletePost(@PathVariable("id") long id, Model model) {
        postService.getPostById(id);

        postService.deletePost(id);
        model.addAttribute("posts", postService.getPostList());
        return "redirect:/postList";
    }


}