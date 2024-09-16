package com.rgt.assignment.controller;

import com.rgt.assignment.domain.Member;
import com.rgt.assignment.dto.LoginRequest;
import com.rgt.assignment.service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    public void login(@RequestBody LoginRequest loginRequest, HttpServletRequest servletRequest) {
        Member loginMember = loginService.login(loginRequest.getEmail(), loginRequest.getPassword());

        HttpSession session = servletRequest.getSession();
        session.setAttribute("loginMember", loginMember);
    }
}
