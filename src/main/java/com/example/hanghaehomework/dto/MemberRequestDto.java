package com.example.hanghaehomework.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberRequestDto {
    private String username;
    private String password;
    private boolean admin = false;
    private String adminToken = "";
//    private String userId;  // 이거왜있어요?


}
