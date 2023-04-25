package com.example.hanghaehomework.dto;



import lombok.Data;


import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotBlank;

@Data
public class SignupRequestDto {

    @Size(min = 4, max = 10,message = "아이디는 4글자~10자 사이로 입력해주세요.")
    @Pattern(regexp = "^[a-z0-9]*$")
    private String username;

    @Size(min = 8, max = 15)
    @NotBlank
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,15}", message = "비밀번호는 8~15자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    private String password;

    private boolean admin = false;
    private String adminToken = "";

}