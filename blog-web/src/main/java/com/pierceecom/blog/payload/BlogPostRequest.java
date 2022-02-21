package com.pierceecom.blog.payload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class BlogPostRequest {
	
	@NotBlank
	@Size(min =5,max =12)
	private String postId;

	@NotBlank
	@Size(min = 5,max =20)
	private String title;

	@NotBlank
	@Size(min = 10,max =40)
	private String content;
}
