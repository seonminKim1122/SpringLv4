package com.example.hanghaehomework.controller;

import com.example.hanghaehomework.dto.CommentDeleteResponseDto;
import com.example.hanghaehomework.dto.CommentRequestDto;
import com.example.hanghaehomework.dto.CommentResponseDto;
import com.example.hanghaehomework.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comment")    // 댓글 작성
    public CommentResponseDto createComment(@RequestBody CommentRequestDto commentRequestDto, HttpServletRequest request){
        return commentService.createComment(commentRequestDto, request);
    }

    @PutMapping("/comment/{id}")
    public CommentResponseDto updateComment(@PathVariable Long id,@RequestBody CommentRequestDto commentRequestDto, HttpServletRequest request){
        return commentService.updateComment(id,commentRequestDto, request);
    }

    @DeleteMapping("/comment/{id}")
    public CommentDeleteResponseDto deleteComment(@PathVariable Long id,  HttpServletRequest request){
        return commentService.deleteComment(id, request);
    }
}
