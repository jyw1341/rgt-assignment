package com.rgt.assignment;

import com.rgt.assignment.domain.Member;
import com.rgt.assignment.repository.MemberRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TestDataInitializer {

    private final MemberRepository memberRepository;

    @PostConstruct
    public void init() {
        Member member = new Member("test", "test@gmail.com", "1234");
        memberRepository.save(member);
    }
}
