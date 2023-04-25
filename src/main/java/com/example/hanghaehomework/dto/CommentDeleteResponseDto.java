package com.example.hanghaehomework.dto;

import lombok.Getter;

@Getter
public class CommentDeleteResponseDto {

    private String msg;
    private int statusCode;

    public CommentDeleteResponseDto() {
        this.msg = "댓글 삭제 성공";
        this.statusCode = 200;
    }
}
