package com.pierceecom.blog.service;

import com.pierceecom.blog.dao.PostDao;
import com.pierceecom.blog.entity.Post;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Stateless
@Transactional
public class PostServiceImpl implements PostService {

    @Inject
    private PostDao postDao;

    @Override
    public void save(Post post) {
        postDao.save(post);
    }

    @Override
    public void update(Post post) {
        postDao.update(post);
    }

    @Override
    public List<Post> findAll() {
        return postDao.findAll();
    }

    @Override
    public Optional<Post> findById(String blogPostId) {
        return postDao.findById(blogPostId);
    }

    @Override
    public void delete(Post post) {
        postDao.delete(post);
    }
}
