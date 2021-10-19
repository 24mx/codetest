package com.pierceecom.blog;

import com.pierceecom.blog.model.Post;
import io.restassured.response.Response;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

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
        Response response = BlogRestClient.getAllPosts();
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.body().as(List.class)).isEmpty();
    }

    @Test
    public void test_2_AddPosts() {
        Post post = new Post();
        post.setTitle("title");
        post.setContent("content");

        Response response = BlogRestClient.addPost(post);
        assertThat(response.getHeaders().get("Location").getValue()).isEqualTo(BlogRestClient.POSTS_URI + "1");

        response = BlogRestClient.addPost(post);
        assertThat(response.getHeaders().get("Location").getValue()).isEqualTo(BlogRestClient.POSTS_URI + "2");
    }

    @Test
    public void test_3_GetPost() {
        Response response = BlogRestClient.getPostById(1);
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.body().as(Post.class).getId()).isEqualTo("1");

        response = BlogRestClient.getPostById(2);
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.body().as(Post.class).getId()).isEqualTo("2");
    }

    @Test
    public void test_4_GetAllPosts() {
        Response response = BlogRestClient.getAllPosts();
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.body().as(List.class).size()).isEqualTo(2);
    }

    @Test
    public void test_5_updateOrCreatePosts() {
        Post post = new Post();
        post.setId("1");
        post.setTitle("Update Title");
        post.setContent("Update Content");

        Response response = BlogRestClient.updateOrCreatePost(post);
        assertThat(response.statusCode()).isEqualTo(201);
        assertThat(response.getHeaders().get("Location").getValue()).isEqualTo(BlogRestClient.POSTS_URI + "1");
        response = BlogRestClient.getPostById(1);
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.body().as(Post.class).getTitle()).isEqualTo("Update Title");

        post = new Post();
        post.setTitle("Update Title");
        post.setContent("Update Content");

        response = BlogRestClient.updateOrCreatePost(post);
        assertThat(response.statusCode()).isEqualTo(201);
        assertThat(response.getHeaders().get("Location").getValue()).isEqualTo(BlogRestClient.POSTS_URI + "3");
    }

    @Test
    public void test_6_DeletePosts() {
        Response response = BlogRestClient.deletePost(1);
        assertThat(response.statusCode()).isEqualTo(200);
        response = BlogRestClient.getPostById(1);
        assertThat(response.statusCode()).isEqualTo(204);

        BlogRestClient.deletePost(2);
        BlogRestClient.deletePost(3);

    }

    @Test
    public void test_6_GetAllPostsShouldNowBeEmpty() {
        Response response = BlogRestClient.getAllPosts();
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.body().as(List.class)).isEmpty();
    }

}
