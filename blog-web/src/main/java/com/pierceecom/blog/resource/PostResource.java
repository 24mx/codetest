package com.pierceecom.blog.resource;

import com.pierceecom.blog.entity.Post;
import com.pierceecom.blog.service.PostService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.util.Optional;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;

@Path("/posts")
public class PostResource {

    @Inject
    private PostService postService;

    @GET
    @Produces({APPLICATION_JSON, APPLICATION_XML})
    public Response getAllPosts() {
        return Response
                .ok(postService.findAll())
                .build();
    }

    @POST
    @Consumes({APPLICATION_JSON, APPLICATION_XML})
    @Produces({APPLICATION_JSON, APPLICATION_XML})
    public Response addPost(@Valid Post post) {
        Optional<Post> postOptional = getPostById(post.getId());
        if (postOptional.isPresent()) {
            postService.update(post);
        } else {
            postService.save(post);
        }

        return Response
                .status(Response.Status.CREATED)
                .entity(post).build();
    }

    @PUT
    @Consumes({APPLICATION_JSON, APPLICATION_XML})
    @Produces({APPLICATION_JSON, APPLICATION_XML})
    public Response updatePost(@Valid Post post) {
        Optional<Post> postOptional = getPostById(post.getId());
        if (postOptional.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Post persistedPost = postOptional.get();

        updateValues(post, persistedPost);

        postService.update(persistedPost);

        return Response
                .status(Response.Status.CREATED)
                .entity(post).build();
    }

    @GET
    @Path("/{postId}")
    @Produces({APPLICATION_JSON, APPLICATION_XML})
    public Response findPostById(@PathParam("postId") String postId) {
        Optional<Post> postOptional = getPostById(postId);
        if (postOptional.isEmpty()) {
            return Response.noContent().build();
        }

        return Response.ok(postOptional.get()).build();
    }

    @DELETE
    @Produces(APPLICATION_JSON)
    public Response deletePostById(@QueryParam("postId") @NotNull String postId) {
        Optional<Post> postOptional = getPostById(postId);
        if (postOptional.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        postService.delete(postOptional.get());

        return Response.ok().build();
    }

    private Optional<Post> getPostById(String postId) {
        return postService.findById(postId);
    }

    private void updateValues(Post post, Post persistedPost) {
        persistedPost.setId(post.getId());
        persistedPost.setTitle(post.getTitle());
        persistedPost.setContent(post.getContent());
    }
}
