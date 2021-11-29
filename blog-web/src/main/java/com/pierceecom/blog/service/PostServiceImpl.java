package com.pierceecom.blog.service;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.enterprise.context.ApplicationScoped;

import com.pierceecom.blog.model.Post;

@ApplicationScoped
public class PostServiceImpl implements PostService {

	private ConcurrentMap<String, Post> postMap = new ConcurrentHashMap<>();
	
	protected PostServiceImpl() {
	}
	
	@Override
	public void addPost(Post post) {
		postMap.put(post.getId(), post);
	}

	@Override
	public void deletePost(String postId) {
		postMap.remove(postId);
	}

	@Override
	public Collection<Post> getAllPosts() {
		return postMap.values();
	}

	@Override
	public Optional<Post> getPostById(String postId) {
		return Optional.ofNullable(postMap.get(postId));
	}

	@Override
	public void updatePost(Post post) {
		postMap.put(post.getId(), post);
	}
	
}
