package com.example.hanghaehomework.service;


import com.example.hanghaehomework.dto.MemberRequestDto;
import com.example.hanghaehomework.dto.SignupRequestDto;
import com.example.hanghaehomework.entity.Member;
import com.example.hanghaehomework.entity.UserRoleEnum;
import com.example.hanghaehomework.jwt.JwtUtil;
import com.example.hanghaehomework.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;
    // ADMIN_TOKEN
    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";
    @Transactional(readOnly = true)
    public void login(MemberRequestDto memberRequestDto, HttpServletResponse response){
        String userName = memberRequestDto.getUsername();
        String password = memberRequestDto.getPassword();


        Member member = memberRepository.findByUsername(userName).orElseThrow(
                () -> new IllegalArgumentException("등록된 아이디가 없습니다.")
        );

        if(!member.getPassword().equals(password)){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(member.getUsername(), member.getRole()));
    }
    @Transactional
    public void signup(SignupRequestDto signupRequestDto)  {
        //회원가입 유저 아이디 중복확인
        Optional<Member> overlapUser = memberRepository.findByUsername(signupRequestDto.getUsername());
        if(overlapUser.isPresent()) {
            throw new IllegalArgumentException("중복된 아이디가 있습니다.");
        }
        UserRoleEnum role = UserRoleEnum.USER;
        if (signupRequestDto.isAdmin()) {
            if (!signupRequestDto.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRoleEnum.ADMIN;
        }
        Member member = new Member(signupRequestDto, role);
        memberRepository.save(member);

    }



}
