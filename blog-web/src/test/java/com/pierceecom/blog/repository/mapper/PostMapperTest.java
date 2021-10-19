package com.pierceecom.blog.repository.mapper;

import com.pierceecom.blog.model.Post;
import com.pierceecom.blog.repository.entity.PostEntity;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PostMapperTest {

    private Post post;
    private PostEntity postEntity;

    @Before
    public void setup() {
        post = new Post();
        post.setId("1");
        post.setTitle("title");
        post.setContent("content");

        postEntity = new PostEntity();
        postEntity.setId(1L);
        postEntity.setTitle("title");
        postEntity.setContent("content");
    }

    @Test
    public void mapPostToPostEntity() {
        // Setup

        // Act
        PostEntity result = PostMapper.map(post, new PostEntity());

        // Assert
        assertThat(result.getId()).isEqualTo(postEntity.getId());
        assertThat(result.getTitle()).isEqualTo(postEntity.getTitle());
        assertThat(result.getContent()).isEqualTo(postEntity.getContent());
    }

    @Test
    public void mapPostEntityToPost() {
        // Setup

        // Act
        Post result = PostMapper.map(postEntity, new Post());

        // Assert
        assertThat(result.getId()).isEqualTo(post.getId());
        assertThat(result.getTitle()).isEqualTo(post.getTitle());
        assertThat(result.getContent()).isEqualTo(post.getContent());
    }
}
