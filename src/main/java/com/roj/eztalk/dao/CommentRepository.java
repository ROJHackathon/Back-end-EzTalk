package com.roj.eztalk.dao;

import org.springframework.data.repository.CrudRepository;

import com.roj.eztalk.domain.Comment;

public interface CommentRepository extends CrudRepository<Comment, Long> {
    
}