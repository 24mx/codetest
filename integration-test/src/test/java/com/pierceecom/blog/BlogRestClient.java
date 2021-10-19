package com.pierceecom.blog;

import com.pierceecom.blog.model.Post;
import io.restassured.response.Response;

import javax.ws.rs.core.MediaType;

import static io.restassured.RestAssured.given;

public class BlogRestClient {

    public static final String POSTS_URI = "http://localhost:8080/blog-web/posts/";

    public static Response addPost(Post post) {
        return given()
                .log().all()
                .contentType(MediaType.APPLICATION_JSON)
                .body(post)
                .post(POSTS_URI)
                .then()
                .log().all()
                .extract().response();
    }

    public static Response deletePost(Integer postId) {
        return given()
                .log().all()
                .delete(POSTS_URI + postId)
                .then()
                .log().all()
                .extract().response();
    }

    public static Response getAllPosts() {
        return given()
                .log().all()
                .get(POSTS_URI)
                .then()
                .log().all()
                .extract().response();
    }

    public static Response getPostById(Integer postId) {
        return given()
                .log().all()
                .get(POSTS_URI + postId)
                .then()
                .log().all()
                .extract().response();
    }

    public static Response updateOrCreatePost(Post post) {
        return given()
                .log().all()
                .contentType(MediaType.APPLICATION_JSON)
                .body(post)
                .put(POSTS_URI)
                .then()
                .log().all()
                .extract().response();
    }
}
