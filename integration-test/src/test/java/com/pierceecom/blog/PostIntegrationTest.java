package com.pierceecom.blog;


import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static com.jayway.restassured.RestAssured.get;
import static com.jayway.restassured.RestAssured.with;
import static org.hamcrest.CoreMatchers.is;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PostIntegrationTest {

    private static final String BLOG_POST = "{\"id\":\"1\",\"title\":\"First title\",\"content\":\"First content\"}";

    @BeforeEach
    void setup() {
        RestAssured.baseURI = "http://localhost:8080/blog-web";
        RestAssured.port = 8080;
    }

    @Order(1)
    @Test
    void getPosts_noPostsPresent() {
        get("/posts/")
                .then()
                .statusCode(200)
                .assertThat()
                .body(is("[]"));
    }

    @Order(2)
    @Test
    void addPost() {
        with()
                .body(BLOG_POST)
                .contentType(ContentType.JSON)
                .when()
                .post("/posts/")
                .then()
                .statusCode(201);
    }

    @Order(3)
    @Test
    void addPost_invalidPost() {
        with()
                .body("{\"id\":\"2\",\"title\":\"First title\",\"content\":null}")
                .contentType(ContentType.JSON)
                .when()
                .post("/posts/")
                .then()
                .statusCode(400);
    }

    @Order(4)
    @Test
    void getPosts() {
        get("/posts/")
                .then()
                .statusCode(200)
                .assertThat();
    }

    @Order(5)
    @Test
    void getPostByPostId() {
        get("/posts/" + 1)
                .then()
                .statusCode(200)
                .assertThat()
                .body("id", is("1"))
                .body("title", is("First title"))
                .body("content", is("First content"));
    }

    @Order(6)
    @Test
    void getPostByPostId_notFound() {
        get("/posts/" + 10)
                .then()
                .statusCode(204);
    }

    @Order(7)
    @Test
    void updatePost() {
        with()
                .body("{\"id\":\"1\",\"title\":\"First title\",\"content\":\"Updated content\"}")
                .contentType(ContentType.JSON)
                .when()
                .put("/posts/")
                .then()
                .statusCode(201)
                .assertThat()
                .body("content", is("Updated content"));
    }

    @Order(8)
    @Test
    void updatePost_invalidPost() {
        with()
                .body("{\"id\":\"1\",\"title\":null,\"content\":\"Updated content\"}")
                .contentType(ContentType.JSON)
                .when()
                .put("/posts/")
                .then()
                .statusCode(400);
    }

    @Order(9)
    @Test
    void deletePostByPostId() {
        with()
                .queryParam("postId", 1)
                .delete("/posts/")
                .then()
                .statusCode(200);
    }

    @Order(10)
    @Test
    void deletePostByPostId_postNotFound() {
        with()
                .queryParam("postId", 10)
                .delete("/posts/")
                .then()
                .statusCode(404);
    }

    @Order(11)
    @Test
    public void getAllPostsShouldNowBeEmpty() {
        get("/posts/")
                .then()
                .statusCode(200)
                .assertThat()
                .body(is("[]"));
    }
}
