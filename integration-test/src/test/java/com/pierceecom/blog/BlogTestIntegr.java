package com.pierceecom.blog;

import static io.restassured.RestAssured.with;
import static io.restassured.RestAssured.get;
import static org.hamcrest.Matchers.is;

import java.util.List;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import io.restassured.RestAssured;
import jakarta.ws.rs.core.MediaType;


/**
 * TODO, Consider it part of the test to replace HttpURLConnection with better
 * APIs (for example Jersey-client, JSON-P etc-) to call REST-service
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BlogTestIntegr {

    private static final String VALID_POST = "{\"id\":\"\",\"title\":\"First title\",\"content\":\"First content\"}";
    private static final String INVALID_POST = "{\"id\":\"\",\"title\":\"Second title\",\"content\":\"Invalid content🙂\"}";
    private static final String UPDATE_POST = "{\"id\":\"ID\",\"title\":\"First title\",\"content\":\"Content Updated\"}";
    
    public BlogTestIntegr() {
    }
    
    @Before
    public void setup(){
        RestAssured.baseURI = "http://localhost:8080/blog-web";
    }
    
    @Test
   	public void  test_1_0_GetAllPosts_NoPost() {
    	get("/posts")
    	.then()
    	.assertThat()
    	.body(is("[]"));
   	}
    
    @Test
	public void  test_2_0_AddPost_Ok() {
		  with()
		  .body(VALID_POST)
		  .contentType(MediaType.APPLICATION_JSON)
		  .when()
		  .request("POST", "/posts")
		  .then()
		  .statusCode(201);
	}
    
    @Test
	public void  test_2_1_AddPost_InvalidContent() {
		  with()
		  .body(INVALID_POST)
		  .contentType(MediaType.APPLICATION_JSON)
		  .when()
		  .request("POST", "/posts")
		  .then()
		  .statusCode(400);
	}

	@Test
    public void test_3_0_GetPost_Ok() {
		get("/posts/"+getPostId())
		.then()
		.statusCode(200);
    }
	
	@Test
    public void test_3_1_GetPost_NoContent() {
		get("/posts/WrongId")
		.then()
		.statusCode(204);
    }
	
    @Test
    public void test_4_0_GetAllPosts() {
    	get("/posts")
    	.then()
    	.assertThat()
		.statusCode(200);
    }
    
    @Test
    public void test_5_0_UpdatePost_Ok() {
    	String post = UPDATE_POST.replace("ID", getPostId());
    	with()
		  .body(post)
		  .contentType(MediaType.APPLICATION_JSON)
		  .when()
		  .request("PUT", "/posts")
		  .then()
		  .statusCode(201)
		  .assertThat()
		  .body("content", is("Content Updated"));
    }
    
    @Test
    public void test_5_1_UpdatePost_NotFound() {
    	with()
		  .body(UPDATE_POST)
		  .contentType(MediaType.APPLICATION_JSON)
		  .when()
		  .request("PUT", "/posts")
		  .then()
		  .statusCode(404);
    }
    
    @Test
    public void test_6_0_DeletePost_NotFound() {
    	with()
		  .request("DELETE", "/posts/WrongId")
		  .then()
		  .statusCode(404);
    }
    
    @Test
    public void test_6_1_DeletePost_Ok() {
    	with()
		  .request("DELETE", "/posts/"+getPostId())
		  .then()
		  .statusCode(200);
    }
    
	@Test
	public void test_7_0_GetAllPostsShouldNowBeEmpty() {
		get("/posts")
	  	  .then()
	  	  .assertThat()
	  	  .body(is("[]"));
	  }
    
	  // Helper method.
    private String getPostId() {
    	List<String> postIds = get("/posts")
				.getBody()
				.jsonPath()
				.getList("id");
		return postIds.get(0);
    }

}
