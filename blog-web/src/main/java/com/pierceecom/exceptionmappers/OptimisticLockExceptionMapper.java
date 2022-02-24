package com.pierceecom.exceptionmappers;

import javax.persistence.OptimisticLockException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.pierceecom.model.ErrorMessage;

@Provider
public class OptimisticLockExceptionMapper implements ExceptionMapper<OptimisticLockException> {
	@Override
	public Response toResponse(OptimisticLockException ex){
	
		ErrorMessage errorMessage =new ErrorMessage("Post not found", 404, "");
		return Response.status(Status.NOT_FOUND).entity(errorMessage).build();
	
	}

}
