package com.pierceecom.blog.service;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.pierceecom.blog.entity.BlogPost;
import com.pierceecom.blog.payload.ApiResponse;
import com.pierceecom.blog.payload.BlogPostRequest;
import com.pierceecom.blog.payload.BlogPostResponse;

@Service
public interface BlogPostService {

	ResponseEntity<?> getAllPosts();
	
	BlogPostResponse addPost(@Valid BlogPostRequest request);
	
	//updatePost
	BlogPost updatePost(@Valid BlogPostRequest newPostRequest);
	
	//getPostById
	BlogPost getPost(String postId);
	
	//deletePost
	ApiResponse deletePost(String postId);

	
}
