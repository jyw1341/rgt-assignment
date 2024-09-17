package com.rgt.assignment.controller;

import com.rgt.assignment.constant.SessionConstant;
import com.rgt.assignment.domain.Member;
import com.rgt.assignment.dto.LoginInfo;
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
        Member member = loginService.login(loginRequest.getEmail(), loginRequest.getPassword());

        HttpSession session = servletRequest.getSession();
        LoginInfo loginInfo = new LoginInfo(
                member.getId(),
                member.getName(),
                SessionConstant.RESTAURANT_ID,
                SessionConstant.TABLE_NUMBER
        );
        session.setAttribute(SessionConstant.LOGIN_INFO, loginInfo);
    }
}
