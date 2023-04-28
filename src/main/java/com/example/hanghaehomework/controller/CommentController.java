package com.example.hanghaehomework.controller;

import com.example.hanghaehomework.dto.CommentDeleteResponseDto;
import com.example.hanghaehomework.dto.CommentRequestDto;
import com.example.hanghaehomework.dto.CommentResponseDto;
import com.example.hanghaehomework.security.MemberDetailsImpl;
import com.example.hanghaehomework.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comment")    // 댓글 작성
    public CommentResponseDto createComment(@RequestBody CommentRequestDto commentRequestDto, HttpServletRequest request, @AuthenticationPrincipal MemberDetailsImpl memberDetails){
        return commentService.createComment(commentRequestDto, request, memberDetails.getUser());
    }

    @PutMapping("/comment/{id}")
    public CommentResponseDto updateComment(@PathVariable Long id,@RequestBody CommentRequestDto commentRequestDto, HttpServletRequest request, @AuthenticationPrincipal MemberDetailsImpl memberDetails){
        return commentService.updateComment(id,commentRequestDto, request, memberDetails.getUser());
    }

    @DeleteMapping("/comment/{id}")
    public CommentDeleteResponseDto deleteComment(@PathVariable Long id,  HttpServletRequest request, @AuthenticationPrincipal MemberDetailsImpl memberDetails){
        return commentService.deleteComment(id, request, memberDetails.getUser());
    }
}
