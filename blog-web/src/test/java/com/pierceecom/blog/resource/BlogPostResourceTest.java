package com.pierceecom.blog.resource;

import com.pierceecom.blog.entity.Post;
import com.pierceecom.blog.service.PostService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.NO_CONTENT;
import static javax.ws.rs.core.Response.Status.OK;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BlogPostResourceTest {

    @Mock
    private PostService postService;

    @InjectMocks
    private PostResource postResource;

    @Test
    void getAllBlogPosts() {
        Post expectedPost = createBlogPost("1", "title", "content");

        when(postService.findAll()).thenReturn(List.of(expectedPost));

        Response response = postResource.getAllPosts();

        assertThat("response status ", response.getStatus(), is(OK.getStatusCode()));
        List<Post> responseEntities = (List<Post>) response.getEntity();
        assertThat("response entities ", responseEntities, hasSize(1));
        Post responseEntity = responseEntities.iterator().next();
        assertThat("response entity ", responseEntity, is(expectedPost));
    }

    @Test
    void getAllBlogPosts_noData() {
        when(postService.findAll()).thenReturn(List.of());

        Response response = postResource.getAllPosts();

        assertThat("response status ", response.getStatus(), is(OK.getStatusCode()));
        List<Post> responseEntities = (List<Post>) response.getEntity();
        assertThat("response entities ", responseEntities, hasSize(0));
    }

    @Test
    void addBlogPost() {
        Post post = createBlogPost("2", "some title", "some content");

        Response response = postResource.addPost(post);

        assertThat("response status ", response.getStatus(), is(CREATED.getStatusCode()));
        Post responseEntity = (Post) response.getEntity();
        assertThat("response entity ", responseEntity, is(post));
        verify(postService).save(post);
    }

    @Test
    void updateBlogPost() {
        Post savedPost = createBlogPost("2", "title", "content");
        Post updatedPost = createBlogPost("2", "updated title", "some content");

        when(postService.findById(updatedPost.getId())).thenReturn(Optional.of(savedPost));

        Response response = postResource.updatePost(updatedPost);

        assertThat("response status ", response.getStatus(), is(CREATED.getStatusCode()));
        Post responseEntity = (Post) response.getEntity();
        assertThat("response entity ", responseEntity, is(updatedPost));
        verify(postService).update(updatedPost);
    }

    @Test
    void updateBlogPost_blogPostNotPresent() {
        Post updatedPost = createBlogPost("2", "updated title", "some content");

        when(postService.findById(updatedPost.getId())).thenReturn(Optional.empty());

        Response response = postResource.updatePost(updatedPost);

        assertThat("response status ", response.getStatus(), is(NOT_FOUND.getStatusCode()));
        verify(postService, never()).save(updatedPost);
    }

    @Test
    void findBlogPostById() {
        Post savedPost = createBlogPost("2", "title", "content");

        when(postService.findById(savedPost.getId())).thenReturn(Optional.of(savedPost));

        Response response = postResource.findPostById(savedPost.getId());

        assertThat("response status ", response.getStatus(), is(OK.getStatusCode()));
        Post responseEntity = (Post) response.getEntity();
        assertThat("response entity ", responseEntity, is(savedPost));
    }

    @Test
    void findBlogPostById_notFound() {

        when(postService.findById(anyString())).thenReturn(Optional.empty());

        Response response = postResource.findPostById("non existing post id");

        assertThat("response status ", response.getStatus(), is(NO_CONTENT.getStatusCode()));
    }

    @Test
    void deleteBlogPostById() {
        Post savedPost = createBlogPost("2", "title", "content");

        when(postService.findById(savedPost.getId())).thenReturn(Optional.of(savedPost));

        Response response = postResource.deletePostById(savedPost.getId());

        assertThat("response status ", response.getStatus(), is(OK.getStatusCode()));
    }

    @Test
    void deleteBlogPostById_blogPostNotFound() {

        when(postService.findById(anyString())).thenReturn(Optional.empty());

        Response response = postResource.deletePostById("non existing post id");

        assertThat("response status ", response.getStatus(), is(NOT_FOUND.getStatusCode()));
    }

    private Post createBlogPost(String id,
                                String title,
                                String content) {
        Post post = new Post();
        post.setId(id);
        post.setTitle(title);
        post.setContent(content);

        return post;
    }
}