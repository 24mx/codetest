package com.pierceecom.blog.service;

import java.util.Collection;
import java.util.Optional;

import com.pierceecom.blog.model.Post;

public interface PostService {
	
	void addPost(Post post);
	
	void deletePost(String postId);
	
	Collection<Post> getAllPosts();
	
	Optional<Post> getPostById(String postId);
	
	void updatePost(Post post);

}
