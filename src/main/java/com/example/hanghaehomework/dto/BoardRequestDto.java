package com.example.hanghaehomework.dto;


import com.example.hanghaehomework.entity.Member;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardRequestDto {
    private Member member;
    private String title;
    private String contents;



}
