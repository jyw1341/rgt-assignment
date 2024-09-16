package com.rgt.assignment.argumentsresolver;

import com.rgt.assignment.constant.SessionConstant;
import com.rgt.assignment.dto.LoginInfo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Slf4j
public class LoginArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {

        log.info("supportsParameter 실행");

        boolean hasLoginAnnotation = parameter.hasParameterAnnotation(Login.class);
        boolean hasLoginInfoType = LoginInfo.class.isAssignableFrom(parameter.getParameterType());

        return hasLoginAnnotation && hasLoginInfoType;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        log.info("resolveArgument 실행");
        HttpServletRequest nativeRequest = (HttpServletRequest) webRequest.getNativeRequest();
        HttpSession session = nativeRequest.getSession(false);

        if(session == null) {
            return null;
        }

        return new LoginInfo(
                (Long) session.getAttribute(SessionConstant.MEMBER_ID),
                (Long) session.getAttribute(SessionConstant.RESTAURANT_ID),
                (Integer) session.getAttribute(SessionConstant.TABLE_NUMBER));
    }
}
