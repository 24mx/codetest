package com.pierceecom.blog.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.stream.Collectors;

@Provider
public class PostResourceExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    private static final Logger logger = LogManager.getLogger(PostResourceExceptionMapper.class);

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        Response response = Response.status(Response.Status.BAD_REQUEST)
                .entity(prepareMessage(exception))
                .type("text/plain")
                .build();

        logger.error("Returned error response is {}", response);

        return response;
    }

    private String prepareMessage(ConstraintViolationException exception) {
        return exception.getConstraintViolations()
                .stream()
                .map(cv -> cv.getPropertyPath() + " " + cv.getMessage() + "\n")
                .collect(Collectors.joining());
    }
}
