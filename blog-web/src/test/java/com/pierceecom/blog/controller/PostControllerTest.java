package com.pierceecom.blog.controller;

import com.pierceecom.blog.model.Post;
import com.pierceecom.blog.service.PostsService;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PostControllerTest {

    private PostsController controller;
    private PostsService service;

    @Before
    public void setup() {
        service = mock(PostsService.class);
        controller = new PostsController(service);
    }

    @Test
    public void addPost_201() {
        // Setup
        URI uri = URI.create("http://woozaa/");
        when(service.addPost(any())).thenReturn(uri);

        // Act
        Response result = controller.addPost(any(), null);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getStatus()).isEqualTo(201);
        assertThat(result.getHeaders().get("Location").get(0)).isEqualTo(uri);
    }

    @Test
    public void addPost_400() {
        // Setup
        when(service.addPost(any())).thenThrow(new IllegalArgumentException());

        // Act
        Response result = controller.addPost(any(), null);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getStatus()).isEqualTo(400);
    }

    @Test
    public void deletePost_200() {
        // Setup
        when(service.deletePost(any())).thenReturn(true);

        // Act
        Response result = controller.deletePost(any(), null);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getStatus()).isEqualTo(200);
    }

    @Test
    public void deletePost_404() {
        // Setup
        when(service.deletePost(any())).thenReturn(false);

        // Act
        Response result = controller.deletePost(any(), null);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getStatus()).isEqualTo(404);
    }

    @Test
    public void getAllPosts_200() {
        // Setup
        when(service.getAllPosts()).thenReturn(new ArrayList<>());

        // Act
        Response result = controller.getAllPosts(null);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getStatus()).isEqualTo(200);
        assertThat(result.getEntity()).isInstanceOf(List.class);
        assertThat((List<Post>) result.getEntity()).isEmpty();
    }

    @Test
    public void getPostById_204() {
        // Setup
        when(service.getPost(any())).thenReturn(new ArrayList<>());

        // Act
        Response result = controller.getPostById(any(), null);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getStatus()).isEqualTo(204);
    }

    @Test
    public void getPostById_200() {
        // Setup
        List<Post> list = new ArrayList<>();
        list.add(new Post());
        when(service.getPost(any())).thenReturn(list);

        // Act
        Response result = controller.getPostById(any(), null);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getStatus()).isEqualTo(200);
        assertThat(result.getEntity()).isInstanceOf(Post.class);
    }

    @Test
    public void updateOrCreatePost_201() {
        // Setup
        URI uri = URI.create("http://woozaa/");
        when(service.updateOrCreatePost(any())).thenReturn(uri);

        // Act
        Response result = controller.updateOrCreatePost(any(), null);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getStatus()).isEqualTo(201);
        assertThat(result.getHeaders().get("Location").get(0)).isEqualTo(uri);
    }

    @Test
    public void updateOrCreatePost_400() {
        // Setup
        when(service.updateOrCreatePost(any())).thenThrow(new IllegalArgumentException());

        // Act
        Response result = controller.updateOrCreatePost(any(), null);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getStatus()).isEqualTo(400);
    }
}
