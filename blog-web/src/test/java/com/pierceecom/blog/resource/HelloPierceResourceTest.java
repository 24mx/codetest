package com.pierceecom.blog.resource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class HelloPierceResourceTest {

    private HelloPierceResource resource;

    @BeforeEach
    void setUp() {
        resource = new HelloPierceResource();
    }

    @Test
    public void hello() {
        Response helloResponse = resource.hello();

        assertEquals(Response.Status.OK.getStatusCode(), helloResponse.getStatus());
        String hello = (String) helloResponse.getEntity();
        assertEquals("{\"message\":\"Hello Pierce\"}", hello);
    }
}
