package com.pierceecom.resource;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;



import javax.ws.rs.core.Response;

import com.pierceecom.model.Post;
import com.pierceecom.service.PostService;


@Path("posts")
public class BlogPostResource {
	PostService postService = new PostService();
	 @GET
	 @Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	 	 public List<Post>  getPosts() {
	        return postService.getAllPosts();
	    }
	 @GET
	 @Path("/{postId}")
	 @Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	 public Post getPost(@PathParam("postId") String postId){
		 return postService.getPostById(postId); 
	 }
	
	 @POST
	 @Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	 @Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	 public Response addPost(Post p){
		 return Response
			      .status(Response.Status.CREATED)
			      .entity(postService.addPost(p))
			      .build();
	 }
	 
	 @PUT
	 @Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	 @Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	 public Response updatePost(Post p){
		 return Response
			      .status(Response.Status.CREATED)
			      .entity(postService.putPost(p))
			      .build();
	 }
	 
	 @DELETE
	 @Path("/{postId}")
	 @Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	 public Response deletepost(@PathParam("postId") String postId){
		 System.out.println(postId);
		  postService.deletePost(postId); 
		    return Response
			      .status(Response.Status.OK)
			      .build();
	 }
}