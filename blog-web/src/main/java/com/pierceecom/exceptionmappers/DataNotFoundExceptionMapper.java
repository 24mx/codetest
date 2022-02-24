package com.pierceecom.exceptionmappers;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.pierceecom.exceptions.DataNotFoundException;
import com.pierceecom.model.ErrorMessage;

@Provider
public class DataNotFoundExceptionMapper implements ExceptionMapper<DataNotFoundException> {
	@Override
	public Response toResponse(DataNotFoundException ex){
	
		ErrorMessage errorMessage =new ErrorMessage(ex.getMessage(),204,"");
		return Response.status(Status.NOT_FOUND).entity(errorMessage).build();
	
	}
	

}
