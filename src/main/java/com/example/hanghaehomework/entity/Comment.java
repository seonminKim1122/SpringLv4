package com.example.hanghaehomework.entity;


import com.example.hanghaehomework.dto.CommentRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @Column(nullable = false)
    private String content;


    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public Comment(CommentRequestDto requestDto){
        this.board = requestDto.getBoard();
        this.content = requestDto.getContent();
        this.member = requestDto.getMember();
    }

    public void update(CommentRequestDto commentRequestDto) {
        this.content = commentRequestDto.getContent();
    }
}
