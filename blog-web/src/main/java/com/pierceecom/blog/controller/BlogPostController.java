package com.pierceecom.blog.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pierceecom.blog.entity.BlogPost;
import com.pierceecom.blog.payload.ApiResponse;
import com.pierceecom.blog.payload.BlogPostRequest;
import com.pierceecom.blog.payload.BlogPostResponse;
import com.pierceecom.blog.service.BlogPostService;

@RestController
@RequestMapping("/blog-web")
public class BlogPostController {

	@Autowired
	BlogPostService blogPostService;
	
	@GetMapping(value = "/posts/getAllPosts")
    public ResponseEntity<?> getAllPosts() {
        return blogPostService.getAllPosts();
    }
	
	@PostMapping(value = "/posts/addPost")
    public BlogPostResponse addNewPost(@Valid @RequestBody BlogPostRequest newPostRequest){
        return blogPostService.addPost(newPostRequest);
    }

	@GetMapping(value = "/posts/getPostById/{postId}")
    public ResponseEntity<BlogPost> findByPostId(@Valid @PathVariable String postId){
		BlogPost allPosts = blogPostService.getPost(postId);
        return new ResponseEntity< >(allPosts, HttpStatus.OK);
    }
	
	@PutMapping(value = "/posts/updatePost/{postId}")
	public ResponseEntity<BlogPost> updatePost(@Valid @RequestBody BlogPostRequest newPostRequest) {
		BlogPost post = blogPostService.updatePost(newPostRequest);

		return new ResponseEntity< >(post, HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/posts/deletePost/{postId}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable(name = "postId") String id) {
		ApiResponse apiResponse = blogPostService.deletePost(id);

		return new ResponseEntity< >(apiResponse, HttpStatus.OK);
	}
}
