package com.pierceecom.blog.service;

import com.pierceecom.blog.model.Post;
import com.pierceecom.blog.repository.PostRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.net.URI;
import java.util.List;

@ApplicationScoped
public class PostsService {

    protected final static String POSTS_URL = "http://localhost:8080/blog-web/posts/";

    private PostRepository postRepository;

    protected PostsService() {
        // weld wants this...
    }

    @Inject
    public PostsService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public URI addPost(Post post) {
        Long id = postRepository.create(post);
        return URI.create(POSTS_URL + id);
    }

    public boolean deletePost(Integer postId) {
        return postRepository.delete(postId);
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public List<Post> getPost(Integer postId) {
        return postRepository.find(postId);
    }

    public URI updateOrCreatePost(Post post) {
        if (postRepository.update(post)) {
            return URI.create(POSTS_URL + post.getId());
        } else {
            return addPost(post);
        }
    }
}
