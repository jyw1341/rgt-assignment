package com.rgt.assignment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rgt.assignment.constant.SessionConstant;
import com.rgt.assignment.domain.Member;
import com.rgt.assignment.dto.LoginInfo;
import com.rgt.assignment.dto.LoginRequest;
import com.rgt.assignment.exception.LoginFailException;
import com.rgt.assignment.service.LoginService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LoginController.class)
class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoginService loginService;

    @Autowired
    private ObjectMapper objectMapper;

    private MockHttpSession session;

    @BeforeEach
    void setUp() {
        session = new MockHttpSession();
    }

    @Test
    @DisplayName("유효한 로그인 요청에 대해 세션에 로그인 정보를 저장하고 성공 응답을 반환한다")
    void testLogin_Successful() throws Exception {
        // given
        Long memberId = 1L;
        String email = "test@example.com";
        String password = "password123";
        String memberName = "Test User";
        Member mockMember = mock(Member.class);

        LoginRequest loginRequest = new LoginRequest(email, password);
        given(mockMember.getId()).willReturn(memberId);
        given(mockMember.getName()).willReturn(memberName);
        given(loginService.login(email, password)).willReturn(mockMember);

        // when, then
        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest))
                        .session(session))
                .andExpect(status().isOk());

        HttpSession createdSession = session;
        LoginInfo loginInfo = (LoginInfo) createdSession.getAttribute(SessionConstant.LOGIN_INFO);
        assertThat(loginInfo).isNotNull();
        assertThat(loginInfo.getMemberId()).isEqualTo(memberId);
        assertThat(loginInfo.getMemberName()).isEqualTo(memberName);
        assertThat(loginInfo.getRestaurantId()).isEqualTo(SessionConstant.RESTAURANT_ID);
        assertThat(loginInfo.getTableNumber()).isEqualTo(SessionConstant.TABLE_NUMBER);
    }

    @Test
    @DisplayName("로그인 실패 시 400 응답을 반환한다")
    void testLogin_Failed() throws Exception {
        // given
        String email = "test@example.com";
        String password = "wrongPassword";
        LoginRequest loginRequest = new LoginRequest(email, password);

        given(loginService.login(email, password)).willThrow(new LoginFailException());

        // when, then
        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest))
                        .session(session))
                .andExpect(status().isBadRequest());
    }
}
