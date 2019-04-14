package com.pierceecom.service;

import java.util.List;

import com.pierceecom.exceptions.DataNotFoundException;
import com.pierceecom.dao.PostDAO;

import com.pierceecom.model.Post;

public class PostService {

	private static PostDAO postDAO;

	public PostService() {
		postDAO = new PostDAO();
	}

	public PostDAO postDAO() {
		return postDAO;
	}

	public Post addPost(Post entity) {
		postDAO.openCurrentSessionwithTransaction();
		postDAO.persist(entity);
		postDAO.closeCurrentSessionwithTransaction();
		return (getPostById(entity.getId()));
	}

	public Post putPost(Post entity) {
		postDAO.openCurrentSessionwithTransaction();
		postDAO.update(entity);
		postDAO.closeCurrentSessionwithTransaction();
		return (getPostById(entity.getId()));
	}

	public Post getPostById(String id) {
		postDAO.openCurrentSession();
		Post post = postDAO.findById(id);
		postDAO.closeCurrentSession();
		if (post == null) {
			throw new DataNotFoundException("No content");
		}
		return post;
	}

	public void deletePost(String id) {
		postDAO.openCurrentSessionwithTransaction();
		Post post = postDAO.findById(id);
		postDAO.delete(post);
		postDAO.closeCurrentSessionwithTransaction();
	}

	public List<Post> getAllPosts() {
		postDAO.openCurrentSession();
		List<Post> posts = postDAO.findAll();
		postDAO.closeCurrentSession();
		return posts;
	}

	public void deleteAllPosts() {
		postDAO.openCurrentSessionwithTransaction();
		postDAO.deleteAll();
		postDAO.closeCurrentSessionwithTransaction();
	}

}
