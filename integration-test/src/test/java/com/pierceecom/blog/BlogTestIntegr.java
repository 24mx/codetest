package com.pierceecom.blog;

import com.pierceecom.blog.model.Post;
import io.restassured.response.Response;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * TODO, Consider it part of the test to replace HttpURLConnection with better
 * APIs (for example Jersey-client, JSON-P etc-) to call REST-service
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BlogTestIntegr {

    @Test
    public void test_1_BlogWithoutPosts() {
        // Setup

        // Act
        Response response = BlogRestClient.getAllPosts();

        // Assert
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.body().as(List.class)).isEmpty();
    }

    @Test
    public void test_2_AddPost() {
        // Setup
        Post post = new Post();
        post.setTitle("title");
        post.setContent("content");

        // Act
        Response response = BlogRestClient.addPost(post);

        // Assert
        assertThat(response.getHeaders().get("Location").getValue()).isNotEmpty();
    }

    @Test
    public void test_3_GetPost() {
        // Setup
        Response response = BlogRestClient.getAllPosts();
        Post post = Arrays.asList(response.getBody().as(Post[].class)).get(0);

        // Act
        response = BlogRestClient.getPostById(Integer.parseInt(post.getId()));

        // Assert
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.body().as(Post.class).getId()).isEqualTo(post.getId());
    }

    @Test
    public void test_4_GetAllPosts() {
        // Setup

        // Act
        Response response = BlogRestClient.getAllPosts();

        // Assert
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.body().as(List.class)).isNotEmpty();
    }

    @Test
    public void test_5_updateOrCreatePosts() {
        // Update an existing Post
        // Setup
        Response response = BlogRestClient.getAllPosts();
        Post post = Arrays.asList(response.getBody().as(Post[].class)).get(0);
        post.setTitle("Update Title");
        post.setContent("Update Content");

        // Act
        response = BlogRestClient.updateOrCreatePost(post);

        // Assert
        assertThat(response.statusCode()).isEqualTo(201);
        assertThat(response.getHeaders().get("Location").getValue()).isEqualTo(BlogRestClient.POSTS_URI + post.getId());
        response = BlogRestClient.getPostById(Integer.parseInt(post.getId()));
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.body().as(Post.class).getTitle()).isEqualTo("Update Title");

        // Create a new Post
        // Setup
        response = BlogRestClient.getAllPosts();
        List<Post> posts = Arrays.asList(response.getBody().as(Post[].class));

        post = new Post();
        post.setTitle("Update Title");
        post.setContent("Update Content");

        // Act
        response = BlogRestClient.updateOrCreatePost(post);

        // Assert
        assertThat(response.statusCode()).isEqualTo(201);
        int id = Integer.parseInt(posts.get(posts.size() - 1).getId()) + 1;
        assertThat(response.getHeaders().get("Location").getValue()).isEqualTo(BlogRestClient.POSTS_URI + id);
    }

    @Test
    public void test_6_DeletePosts() {
        // Setup
        Response response = BlogRestClient.getAllPosts();
        List<Post> posts = Arrays.asList(response.getBody().as(Post[].class));

        // Act
        posts.forEach(post -> BlogRestClient.deletePost(Integer.parseInt(post.getId())));
        response = BlogRestClient.getAllPosts();

        // Assert
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.body().as(List.class)).isEmpty();
    }

}
