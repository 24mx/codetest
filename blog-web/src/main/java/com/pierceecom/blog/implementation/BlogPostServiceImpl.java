package com.pierceecom.blog.implementation;

import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.pierceecom.blog.entity.BlogPost;
import com.pierceecom.blog.exception.ResourceNotFoundException;
import com.pierceecom.blog.payload.ApiResponse;
import com.pierceecom.blog.payload.BlogPostRequest;
import com.pierceecom.blog.payload.BlogPostResponse;
import com.pierceecom.blog.repository.BlogRepository;
import com.pierceecom.blog.service.BlogPostService;


@Component
public class BlogPostServiceImpl implements BlogPostService{

	private static final String POST = "Post";
	private static final String PostID = "id";

	@Autowired
	private BlogRepository blogPostRepository;

	@Override
	public ResponseEntity<?> getAllPosts() {
		return new ResponseEntity<>(blogPostRepository.findAll().stream().collect(Collectors.toList()), HttpStatus.OK);
	}

	@Override
	public BlogPostResponse addPost(@Valid BlogPostRequest request) {
		BlogPost blogPost = new BlogPost();
		blogPost.setPostId(request.getPostId());
		blogPost.setTitle(request.getTitle());
		blogPost.setContent(request.getContent());

		BlogPost newPost= blogPostRepository.save(blogPost);
		
		BlogPostResponse postResponse= new BlogPostResponse();
		postResponse.setPostId(newPost.getPostId());
		postResponse.setTitle(newPost.getTitle());
		postResponse.setContent(newPost.getContent());
		return postResponse;
	}
	
	
	@Override
	public BlogPost getPost(@Valid String postId) {
		
		return blogPostRepository.findByPostId(postId).orElseThrow(() -> new ResourceNotFoundException(POST, PostID, postId));
		
	}

	@Override
	public BlogPost updatePost(@Valid BlogPostRequest newPostRequest) {
		BlogPost updatePost = blogPostRepository.findByPostId(newPostRequest.getPostId()).orElseThrow(() -> new ResourceNotFoundException(POST, PostID, newPostRequest.getPostId()));
		updatePost.setTitle(newPostRequest.getTitle());
		updatePost.setContent(newPostRequest.getContent());
		return blogPostRepository.save(updatePost);
	}
	
	@Override
	@Transactional
	public ApiResponse deletePost(String postId) {
		blogPostRepository.findByPostId(postId).orElseThrow(() -> new ResourceNotFoundException(POST, PostID, postId));
		blogPostRepository.deleteByPostId(postId);
			return new ApiResponse(Boolean.TRUE, "You successfully deleted post");
		}

}
