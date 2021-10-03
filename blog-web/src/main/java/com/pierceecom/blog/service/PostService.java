package com.pierceecom.blog.service;

import com.pierceecom.blog.entity.Post;

import java.util.List;
import java.util.Optional;

public interface PostService {

    void save(Post post);

    void update(Post post);

    List<Post> findAll();

    Optional<Post> findById(String blogPostId);

    void delete(Post post);
}
