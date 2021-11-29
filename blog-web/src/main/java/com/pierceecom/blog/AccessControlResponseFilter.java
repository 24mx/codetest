package com.pierceecom.blog;

import java.io.IOException;

import javax.annotation.Priority;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.Priorities;

/**
 * This is a response filter class to add CORS headers in the response.  
 * So that other applications (e.g. Angular application) can access resources from this web service resources.
 */

@Provider
@Priority(Priorities.HEADER_DECORATOR)
public class AccessControlResponseFilter implements ContainerResponseFilter {
	
    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        final MultivaluedMap<String,Object> headers = responseContext.getHeaders();
        headers.add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        headers.add("Access-Control-Allow-Origin", "*");
        if (requestContext.getMethod().equalsIgnoreCase("OPTIONS")) {
            headers.add("Access-Control-Allow-Headers", requestContext.getHeaderString("Access-Control-Request-Headers"));
        }
    }
}