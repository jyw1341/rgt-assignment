package com.rgt.assignment.interceptor;

import com.rgt.assignment.constant.SessionConstant;
import com.rgt.assignment.exception.LoginFailException;
import com.rgt.assignment.exception.LoginRequiredException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestUri = request.getRequestURI();
        log.info("인증 체크 인터셉터 실행 {}", requestUri);
        HttpSession session = request.getSession();

        if(session == null || session.getAttribute(SessionConstant.LOGIN_INFO) == null) {
            log.info("미인증 사용자 요청");
            throw new LoginRequiredException();
        }
        return true;
    }
}
