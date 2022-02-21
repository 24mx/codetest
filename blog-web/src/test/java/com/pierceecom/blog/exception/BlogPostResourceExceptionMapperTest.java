package com.pierceecom.blog.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import javax.ws.rs.core.Response;
import java.util.Set;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class BlogPostResourceExceptionMapperTest {

    private PostResourceExceptionMapper postResourceExceptionMapper;

    @BeforeEach
    void setUp() {
        postResourceExceptionMapper = new PostResourceExceptionMapper();
    }

    @Test
    void toResponse() {
        Path path = mock(Path.class);
        when(path.toString()).thenReturn("some property path");
        ConstraintViolation<String> constraintViolation = mock(ConstraintViolation.class);
        when(constraintViolation.getMessage()).thenReturn("Message");
        when(constraintViolation.getPropertyPath()).thenReturn(path);

        ConstraintViolationException exception = new ConstraintViolationException(Set.of(constraintViolation));

        Response response = postResourceExceptionMapper.toResponse(exception);

        assertThat("response status ", response.getStatus(), is(BAD_REQUEST.getStatusCode()));
        String responseEntity = (String) response.getEntity();
        assertThat("response entity ", responseEntity, containsString("some property path"));
    }
}