package com.example.hanghaehomework.service;

import com.example.hanghaehomework.dto.BoardRequestDto;
import com.example.hanghaehomework.dto.BoardResponseDto;
import com.example.hanghaehomework.entity.Board;
import com.example.hanghaehomework.entity.Member;
import com.example.hanghaehomework.entity.UserRoleEnum;
import com.example.hanghaehomework.jwt.JwtUtil;
import com.example.hanghaehomework.repository.BoardRepository;
import com.example.hanghaehomework.repository.MemberRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {
    public final BoardRepository boardRepository ;
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;


    //게시글 작성
    @Transactional
    public BoardResponseDto createBoard(BoardRequestDto requestDto, HttpServletRequest request) {
        Member member = checkJwtToken(request);
            if (member == null) {
            throw new IllegalArgumentException("로그인이 필요합니다.");
        }
        requestDto.setMember(member);
        Board board = new Board(requestDto);
        return new BoardResponseDto(boardRepository.save(board));
    }

    //게시글 목록 조회
    @Transactional(readOnly = true)
    public List<BoardResponseDto> getList() {
        List<Board> boardList = boardRepository.findAll();
        return boardList.stream().sorted((memo1, memo2) -> memo2.getModifiedAt().compareTo(memo1.getModifiedAt())).map(BoardResponseDto::new).toList();
    }

    //선택한 게시글 조회
    @Transactional(readOnly = true)
    public BoardResponseDto getBoard(Long id){
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 틀립니다.")
        );
        return new BoardResponseDto(board);
    }

    //게시글 수정
    @Transactional
    public  BoardResponseDto update(Long id, BoardRequestDto requestDto, HttpServletRequest request) {
        Member member = checkJwtToken(request);

        Board board =boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );
        if(member.getUsername().equals(board.getMember().getUsername()) || member.getRole() == UserRoleEnum.ADMIN) {
            board.update(requestDto);
        }else{
            throw new IllegalArgumentException("권한이 없습니다");
        }
        return new BoardResponseDto(board);
    }

    //게시글 삭제
    @Transactional
    public  String deleteBoard(Long id, HttpServletRequest request) {
        Member member = checkJwtToken(request);

        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );
        if(member.getUsername().equals(board.getMember().getUsername()) || member.getRole() == UserRoleEnum.ADMIN) {
            boardRepository.deleteById(id);
        }else{
            throw new IllegalArgumentException("권한이 없습니다");
        }

        return "게시글 삭제 성공.";
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