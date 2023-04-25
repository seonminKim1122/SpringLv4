package com.example.hanghaehomework.dto;

import com.example.hanghaehomework.entity.Board;
import com.example.hanghaehomework.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentRequestDto {
    private Long boardId;
    private Board board;
    private String content;
    private Member member;

}
