package com.pierceecom.blog.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Entity
@Data
@Table(name = "posts")
public class BlogPost {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GenericGenerator(name="posts" , strategy="increment")
	@GeneratedValue(generator="posts")
	private Long id;
	
	@Column(name = "postId", unique=true)
	private String postId;

	@Column(name = "title")
	private String title;

	@Column(name = "content")
	private String content;
}
