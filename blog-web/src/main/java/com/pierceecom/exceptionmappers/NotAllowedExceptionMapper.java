package com.pierceecom.exceptionmappers;


import javax.ws.rs.NotAllowedException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.pierceecom.model.ErrorMessage;

@Provider

public class NotAllowedExceptionMapper implements ExceptionMapper<NotAllowedException> {
	@Override
		public Response toResponse(NotAllowedException ex){
	
		ErrorMessage errorMessage =new ErrorMessage("Invalid input", 404, "#/definitions/Post");
		return Response.status(Status.METHOD_NOT_ALLOWED).entity(errorMessage).type(MediaType.APPLICATION_JSON).build();
	
	}

}

