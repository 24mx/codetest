package com.pierceecom.blog.service;

import com.pierceecom.blog.model.Post;
import com.pierceecom.blog.repository.PostRepository;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PostServiceTest {

    private PostsService service;
    private PostRepository repository;

    @Before
    public void setup() {
        repository = mock(PostRepository.class);
        service = new PostsService(repository);
    }

    @Test
    public void addPost() {
        // Setup
        Long id = 1L;
        when(repository.create(any())).thenReturn(id);

        // Act
        URI result = service.addPost(any());

        // Assert
        assertThat(result.toString()).isEqualTo(PostsService.POSTS_URL + id);
    }

    @Test
    public void updateOrCreatePost_update() {
        // Setup
        Post post = new Post();
        post.setId("1");
        when(repository.update(post)).thenReturn(true);

        // Act
        URI result = service.updateOrCreatePost(post);

        // Assert
        assertThat(result.toString()).isEqualTo(PostsService.POSTS_URL + post.getId());
    }

    @Test
    public void updateOrCreatePost_create() {
        // Setup
        Long id = 1L;
        when(repository.update(any())).thenReturn(false);
        when(repository.create(any())).thenReturn(id);

        // Act
        URI result = service.updateOrCreatePost(any());

        // Assert
        assertThat(result.toString()).isEqualTo(PostsService.POSTS_URL + id);
    }
}
