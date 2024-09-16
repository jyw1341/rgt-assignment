package com.rgt.assignment.controller;

import com.rgt.assignment.domain.Member;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home(@SessionAttribute(name = "loginMember", required = false) Member member) {
        if(member == null) {
            return "로그인이 필요합니다";
        }
        return member.getName();
    }
}
