package com.example.jpa.service;

import com.example.jpa.model.Post;
import com.example.jpa.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    public PostRepository getPostRepository() {
        return postRepository;
    }

    public void setPostRepository(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> getPostList() {
        return (List<Post>) postRepository.findAll();
    }

    public Post savePost(Post post) {return postRepository.save(post);
    }

    public Optional<Post> getPostById(long id) {return postRepository.findById(id);
    }

    public String deletePost(long id) {return postRepository.deleteById(id);
    }

    public Optional<Post> findById(Long postId) {return postRepository.findById(postId);
    }
}
