package com.rgt.assignment.service;

import com.rgt.assignment.domain.Member;
import com.rgt.assignment.exception.InvalidRequestException;
import com.rgt.assignment.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member getMember(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(InvalidRequestException::new);
    }
}
