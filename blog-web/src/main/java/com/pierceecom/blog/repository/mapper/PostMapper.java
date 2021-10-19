package com.pierceecom.blog.repository.mapper;

import com.pierceecom.blog.model.Post;
import com.pierceecom.blog.repository.entity.PostEntity;

public class PostMapper {

    public static PostEntity map(Post post, PostEntity postEntity) {

        postEntity.setTitle(post.getTitle());
        postEntity.setContent(post.getContent());

        return postEntity;
    }

    public static Post map(PostEntity postEntity, Post post) {

        post.setId(postEntity.getId().toString());
        post.setTitle(postEntity.getTitle());
        post.setContent(postEntity.getContent());

        return post;
    }
}
