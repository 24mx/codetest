package com.pierceecom.blog.api;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import com.pierceecom.blog.model.Post;
import com.pierceecom.blog.service.PostService;
import com.pierceecom.blog.swagger.PostsApi;

public class PostsApiImpl implements PostsApi{
	
	private static final AtomicLong postSequenceId = new AtomicLong();
	private static final String VALID_XML_CONTENT_REGEX = "[\\u0009\\u000a\\u000d\\u0020-\\uD7FF\\uE000-\\uFFFD]+$"; // regex to match valid XML Characters.
	private static final int TITLE_MAX_LENGTH = 40;
	private static final int CONTENT_MAX_LENGTH = 100;
	
	private PostService postService;
	
	@Inject
	public PostsApiImpl(PostService postService) {
		this.postService = postService;
	}

	@Override
	public Response addPost(Post post, SecurityContext securityContext) {
		if (!hasValidPostContent(post) || !isNullOrEmpty(post.getId())) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
		post.setId(Long.toString(postSequenceId.incrementAndGet()));
		postService.addPost(post);
		return Response.status(Response.Status.CREATED).entity(post).build();
	}

	@Override
	public Response deletePost(String postId, SecurityContext securityContext) {
		Optional<Post> optPost = postService.getPostById(postId);
		if (optPost.isEmpty()) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		postService.deletePost(postId);
		return Response.ok().build();
	}

	@Override
	public Response getAllPosts(SecurityContext securityContext) {
		return Response.ok(postService.getAllPosts()).build();
	}

	@Override
	public Response getPostById(String postId, SecurityContext securityContext) {
		Optional<Post> optPost = postService.getPostById(postId);
		if (optPost.isEmpty()) {
			return Response.status(Response.Status.NO_CONTENT).build();
		}
		return Response.ok(optPost.get()).build();
	}

	@Override
	public Response updatePost(Post post, SecurityContext securityContext) {
		if (isNullOrEmpty(post.getId()) || !hasValidPostContent(post)) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
		Optional<Post> optPost = postService.getPostById(post.getId());
		if (optPost.isEmpty()) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		postService.updatePost(post);
		return Response.status(Response.Status.CREATED).entity(post).build();
	}
	
	private boolean isNullOrEmpty(String s) {
		if (s == null) {
			return true;
		}
		return s.isBlank();
	}

	private boolean hasValidPostContent(Post post) {
		if (!post.getTitle().matches(VALID_XML_CONTENT_REGEX) || !post.getContent().matches(VALID_XML_CONTENT_REGEX)) {
			return false;
		}
		if (post.getTitle().length() > TITLE_MAX_LENGTH || post.getContent().length() > CONTENT_MAX_LENGTH) {
			return false;
		}
		return true;
	}
}
