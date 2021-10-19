package com.pierceecom.blog.controller;

import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;

public class HelloPierceControllerTest {
    
    private HelloPierceController resource;

    @Before
    public void setup() {
        resource = new HelloPierceController();
    }

    @Test
    public void testHello() {
        Response helloResponse = resource.hello();
        String hello = (String)helloResponse.getEntity();
        assertEquals("{\"message\":\"Hello Pierce\"}", hello);
    }
    
}
