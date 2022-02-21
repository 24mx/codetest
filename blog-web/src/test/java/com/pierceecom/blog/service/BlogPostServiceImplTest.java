package com.pierceecom.blog.service;

import com.pierceecom.blog.entity.Post;
import com.pierceecom.blog.dao.PostDao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BlogPostServiceImplTest {

    @Mock
    private PostDao postDao;

    @Mock
    private Post post;

    @InjectMocks
    private PostServiceImpl blogPostService;

    @Test
    void save() {
        blogPostService.save(post);

        verify(postDao).save(post);
    }

    @Test
    void update() {
        blogPostService.update(post);

        verify(postDao).update(post);
    }

    @Test
    void findAll() {
        when(postDao.findAll()).thenReturn(List.of(post));

        List<Post> posts = blogPostService.findAll();

        assertThat("posts ", posts, hasSize(1));
        assertThat("post ", posts, hasItem(post));
    }

    @Test
    void findById() {
        when(postDao.findById(anyString())).thenReturn(Optional.of(post));

        Optional<Post> blogPostOptional = blogPostService.findById("id");

        assertThat("post ", blogPostOptional, not(Optional.empty()));
        assertThat("post ", blogPostOptional.get(), is(post));
    }

    @Test
    void delete() {
        blogPostService.delete(post);

        verify(postDao).delete(post);
    }
}