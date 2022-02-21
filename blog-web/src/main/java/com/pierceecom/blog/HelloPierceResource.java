
package com.pierceecom.blog;

import javax.ws.rs.core.Response;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/blog-web")
public class HelloPierceResource {


	@GetMapping(value = "/hello-pierce", produces = MediaType.APPLICATION_JSON_VALUE)
	public Response hello() {
		return  Response.ok("{\"message\":\"Hello Pierce\"}").build();
	}
}
