package com.example.hanghaehomework.entity;


import com.example.hanghaehomework.dto.BoardRequestDto;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Getter
@Entity
@NoArgsConstructor
public class Board extends Timestamped {
    @Id
    @Column(name = "board_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String contents;
    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    @OrderBy("createdAt DESC")
    private List<Comment> commentList = new ArrayList<>();

    public Board(BoardRequestDto requestDto) {
        this.member = requestDto.getMember();
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
    }




    public void update(BoardRequestDto requestDto) {
        this.member = requestDto.getMember();
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
    }
}