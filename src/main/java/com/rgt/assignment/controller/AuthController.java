package com.rgt.assignment.controller;

import com.rgt.assignment.constant.SessionConstant;
import com.rgt.assignment.domain.Member;
import com.rgt.assignment.dto.LoginInfo;
import com.rgt.assignment.dto.LoginRequest;
import com.rgt.assignment.service.AuthService;
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
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public void login(@RequestBody LoginRequest loginRequest, HttpServletRequest servletRequest) {
        Member member = authService.login(loginRequest.getEmail(), loginRequest.getPassword());

        HttpSession session = servletRequest.getSession();
        LoginInfo loginInfo = new LoginInfo(
                member.getId(),
                member.getName(),
                loginRequest.getRestaurantId(),
                loginRequest.getTableNumber()
        );
        session.setAttribute(SessionConstant.LOGIN_INFO, loginInfo);
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest servletRequest) {
        HttpSession session = servletRequest.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }
}
