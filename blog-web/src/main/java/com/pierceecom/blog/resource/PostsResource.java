package com.pierceecom.blog.resource;

import com.pierceecom.blog.api.PostsApi;
import com.pierceecom.blog.model.Post;
import com.pierceecom.blog.service.PostsService;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.net.URI;
import java.util.List;

public class PostsResource implements PostsApi {

    private PostsService postsService;

    protected PostsResource() {}

    @Inject
    public PostsResource(PostsService postsService) {
        this.postsService = postsService;
    }

    @Override
    public Response addPost(Post post, SecurityContext securityContext) {
        URI uri = postsService.addPost(post);

        return (uri != null) ?
                Response.created(uri).build()
                :
                Response.status(400).build();
    }

    @Override
    public Response deletePost(Integer postId, SecurityContext securityContext) {
        return postsService.deletePost(postId) ?
                Response.ok().build()
                :
                Response.status(404).build();
    }

    @Override
    public Response getAllPosts(SecurityContext securityContext) {
        return Response.ok(postsService.getAllPosts()).build();
    }

    @Override
    public Response getPostById(Integer postId, SecurityContext securityContext) {
        List<Post> result = postsService.getPost(postId);

        return (result.isEmpty()) ?
                Response.noContent().build()
                :
                Response.ok(result.get(0)).build();
    }

    @Override
    public Response updateOrCreatePost(Post post, SecurityContext securityContext) {
        URI uri = postsService.updateOrCreatePost(post);

        return (uri != null) ?
                Response.created(uri).build()
                :
                Response.status(400).build();
    }
}
