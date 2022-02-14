package com.pierceecom.blog.api;

import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;

import com.pierceecom.blog.model.Post;
import com.pierceecom.blog.service.PostService;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class PostsApiTest {
	
	private PostService postService;
	private PostsApiImpl postsApi;
	
	@Before
	public void setup() {
		postService = mock(PostService.class);
		postsApi = new PostsApiImpl(postService);
	}
	
	@Test
	public void test_1_0_AddPost_Ok() {
		Post post = samplePost("","Valid post","It's valid post!");
		Response response = postsApi.addPost(post, null);
		assertEquals(response.getStatus(),Response.Status.CREATED.getStatusCode());
		assertEquals((Post)response.getEntity(), post);
	}
	
	@Test
	public void test_1_1_AddPost_TitleLengthMoreThanTwenty() {
		Post post = samplePost("","Trying to add a post's title more than 20 characters","It's invalid post!");
		Response response = postsApi.addPost(post, null);
		assertEquals(response.getStatus(),Response.Status.BAD_REQUEST.getStatusCode());
	}
	
	@Test
	public void test_1_2_AddPost_BadRequest() {
		Post post = samplePost("1","Trying to create a post with Id!","Id is auto generated field. So it's invalid post!");
		Response response = postsApi.addPost(post, null);
		assertEquals(response.getStatus(),Response.Status.BAD_REQUEST.getStatusCode());
	}
	
	@Test
	public void test_1_3_AddPost_WithInvalidChar() {
		Post post = samplePost("","Content with invalid emoji character","It contains invalid char 😦");
		Response response = postsApi.addPost(post, null);
		assertEquals(response.getStatus(),Response.Status.BAD_REQUEST.getStatusCode());
	}
	
	@Test
	public void test_2_0_DeletePost_Ok() {
		Post post = samplePost("3","Delete Post","Post to be deleted!");
		when(postService.getPostById(post.getId())).thenReturn(Optional.of(post));
		Response response = postsApi.deletePost(post.getId(), null);
		assertEquals(response.getStatus(),Response.Status.OK.getStatusCode());
	}
	
	@Test
	public void test_2_1_DeletePost_NotFound() {
		Post post = samplePost("4","Delete Post","Post to be deleted!");
		when(postService.getPostById(post.getId())).thenReturn(Optional.of(post));
		Response response = postsApi.deletePost("100", null);
		assertEquals(response.getStatus(),Response.Status.NOT_FOUND.getStatusCode());
	}
	
	@Test
	public void test_3_0_GetAllPosts_Ok() {
		Post post1 = samplePost("5","Post 1","Getting all post1!");
		Post post2 = samplePost("6","Post 2","Getting all post2!");
		Collection<Post> postList = Arrays.asList(post1, post2);
		when(postService.getAllPosts()).thenReturn(postList);
		Response response = postsApi.getAllPosts(null);
		assertEquals(response.getStatus(),Response.Status.OK.getStatusCode());
		Collection<Post> returnedList = (Collection<Post>) response.getEntity();
		assertEquals(returnedList.size(),2);
	}
	
	@Test
	public void test_3_1_GetAllPosts_WithOutData() {
		when(postService.getAllPosts()).thenReturn(List.of());
		Response response = postsApi.getAllPosts(null);
		assertEquals(response.getStatus(),Response.Status.OK.getStatusCode());
		Collection<Post> returnedList =(Collection<Post>) response.getEntity();
		assertEquals(returnedList.size(),0);
	}
	
	@Test
	public void test_4_0_GetPostById_Ok() {
		Post post = samplePost("7","Get Post","Getting post by ID!");
		when(postService.getPostById(post.getId())).thenReturn(Optional.of(post));
		Response response = postsApi.getPostById(post.getId(), null);
		assertEquals(response.getStatus(),Response.Status.OK.getStatusCode());
		assertEquals((Post)response.getEntity(), post);
	}
	
	@Test
	public void test_4_1_GetPostById_NoContent() {
		when(postService.getPostById(anyString())).thenReturn(Optional.empty());
		Response response = postsApi.getPostById(anyString(), null);
		assertEquals(response.getStatus(),Response.Status.NO_CONTENT.getStatusCode());
	}
	
	@Test
	public void test_5_0_UpdatePost_Ok() {
		Post updatedPost = samplePost("8","Update Post","Updating a post!");
		when(postService.getPostById(updatedPost.getId())).thenReturn(Optional.of(updatedPost));
		Response updateResponse = postsApi.updatePost(updatedPost, null);
		assertEquals(updateResponse.getStatus(),Response.Status.CREATED.getStatusCode());
		assertEquals((Post)updateResponse.getEntity(), updatedPost);
	}
	
	@Test
	public void test_5_1_UpdatePost_NotFound() {
		Post updatedPost = samplePost("9","Update Post","Updating a post!");
		when(postService.getPostById(updatedPost.getId())).thenReturn(Optional.empty());
		Response updateResponse = postsApi.updatePost(updatedPost, null);
		assertEquals(updateResponse.getStatus(),Response.Status.NOT_FOUND.getStatusCode());
	}
	
	// helper method.
	private Post samplePost(String id, String title, String content) {
		Post post = new Post();
		post.setId(id);
		post.setTitle(title);
		post.setContent(content);
		return post;
	}

}
