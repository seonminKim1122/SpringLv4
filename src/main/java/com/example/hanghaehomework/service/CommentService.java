package com.example.hanghaehomework.service;

import com.example.hanghaehomework.dto.BoardResponseDto;
import com.example.hanghaehomework.dto.CommentRequestDto;
import com.example.hanghaehomework.dto.CommentResponseDto;
import com.example.hanghaehomework.dto.CommentDeleteResponseDto;
import com.example.hanghaehomework.entity.Board;
import com.example.hanghaehomework.entity.Member;
import com.example.hanghaehomework.entity.Comment;
import com.example.hanghaehomework.entity.UserRoleEnum;
import com.example.hanghaehomework.jwt.JwtUtil;
import com.example.hanghaehomework.repository.BoardRepository;
import com.example.hanghaehomework.repository.CommentRepository;
import com.example.hanghaehomework.repository.MemberRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final JwtUtil jwtUtil;

    // 댓글 작성
    @Transactional
    public CommentResponseDto createComment(CommentRequestDto commentRequestDto, HttpServletRequest request) {
        Member member = checkJwtToken(request);
        if(member == null){
            throw new IllegalArgumentException("로그인이 필요합니다");
        }
        commentRequestDto.setMember(member);
        commentRequestDto.setBoard(boardRepository.findById(commentRequestDto.getBoardId()).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        ));
        Comment comment = new Comment(commentRequestDto);
        return new CommentResponseDto(commentRepository.save(comment));
    }

    // 댓글 삭제
    @Transactional
    public CommentDeleteResponseDto deleteComment(Long id, HttpServletRequest request) {
        Member member = checkJwtToken(request);
        if(member == null){
            throw new IllegalArgumentException("로그인이 필요합니다.");
        }
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("댓글이 존재하지 않습니다.")
        );
        if(member.getUsername().equals(comment.getMember().getUsername()) || member.getRole() == UserRoleEnum.ADMIN) {
            commentRepository.deleteById(id);
        }else{
            throw new IllegalArgumentException("권한이 없습니다");
        }
        return new CommentDeleteResponseDto();

    }

    // 댓글 수정
    @Transactional
    public CommentResponseDto updateComment(Long id, CommentRequestDto commentRequestDto, HttpServletRequest request) {
        Member member = checkJwtToken(request);
        if(member == null){
            throw new IllegalArgumentException("로그인이 필요합니다.");
        }
        Comment comment =commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("댓글이 존재하지 않습니다.")
        );
        if(member.getUsername().equals(comment.getMember().getUsername()) || member.getRole() == UserRoleEnum.ADMIN) {
            comment.update(commentRequestDto);
        }else{
            throw new IllegalArgumentException("권한이 없습니다");
        }
        return new CommentResponseDto(comment);
    }

    public Member checkJwtToken(HttpServletRequest request){
        String token = jwtUtil.resolveToken(request);
        Claims claims;
        if(token != null){
            if(jwtUtil.validateToken(token)){
                claims = jwtUtil.getUserInfoFromToken(token);
            }else{
                throw new IllegalArgumentException("Token Error");
            }

            Member member = memberRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다")
            );
            return member;
        }else {
            return null;
        }
    }


}
