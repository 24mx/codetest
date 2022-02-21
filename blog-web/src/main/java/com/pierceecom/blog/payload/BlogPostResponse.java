package com.pierceecom.blog.payload;

import lombok.Data;

@Data
public class BlogPostResponse {

		private String postId;
		private String title;
		private String content;
}
