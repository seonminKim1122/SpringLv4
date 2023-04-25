package com.example.hanghaehomework.repository;
import com.example.hanghaehomework.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUsername(String userName);
}
