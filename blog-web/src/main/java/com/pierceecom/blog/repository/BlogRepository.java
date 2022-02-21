package com.pierceecom.blog.repository;


import java.util.Optional;

import javax.validation.Valid;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pierceecom.blog.entity.BlogPost;

@Repository
public interface BlogRepository extends JpaRepository<BlogPost, String>{

	Optional<BlogPost> findByPostId(@Valid String PostId);

	void deleteByPostId(@Valid String id);

	
}

