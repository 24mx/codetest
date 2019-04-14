package com.pierceecom.exceptionmappers;

import java.lang.IllegalArgumentException;
import java.lang.Override;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;



import com.pierceecom.model.ErrorMessage;


@Provider
public class IllegalArgumentExceptionMapper implements ExceptionMapper<IllegalArgumentException> {
	@Override
	public Response toResponse(IllegalArgumentException ex){
	
		ErrorMessage errorMessage =new ErrorMessage("Post not found", 404, "");
		return Response.status(Status.NOT_FOUND).entity(errorMessage).build();
	
	}
	

}
