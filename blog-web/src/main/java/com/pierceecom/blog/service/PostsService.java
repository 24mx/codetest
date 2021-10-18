package com.pierceecom.blog.service;

import com.pierceecom.blog.model.Post;
import com.pierceecom.blog.repository.PostRepository;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@ApplicationScoped
public class PostsService {

    private static final Logger LOGGER = Logger.getLogger(PostsService.class);

    private PostRepository postRepository;

    protected PostsService() {}

    @Inject
    public PostsService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public URI addPost(Post post) {

        Long id = postRepository.create(post);

        try {
            return new URI("http://localhost:8080/blog-web/posts/" + id);
        } catch (URISyntaxException e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
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
        try {
            if (postRepository.update(post)) {
                return new URI("http://localhost:8080/blog-web/posts/" + post.getId());
            } else {
                return addPost(post);
            }
        } catch (URISyntaxException e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
    }
}
