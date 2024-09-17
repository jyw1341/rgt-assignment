package com.rgt.assignment.service;

import com.rgt.assignment.domain.Member;
import com.rgt.assignment.exception.LoginFailException;
import com.rgt.assignment.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;

    public Member login(String email, String password) {
        return memberRepository.findByEmail(email)
                .filter(member -> member.getPassword().equals(password))
                .orElseThrow(LoginFailException::new);
    }
}
