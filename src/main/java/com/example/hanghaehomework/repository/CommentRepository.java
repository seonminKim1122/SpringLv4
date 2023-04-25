package com.example.hanghaehomework.repository;

import com.example.hanghaehomework.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
