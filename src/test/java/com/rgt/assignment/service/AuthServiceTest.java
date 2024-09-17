package com.rgt.assignment.service;

import com.rgt.assignment.domain.Member;
import com.rgt.assignment.exception.LoginFailException;
import com.rgt.assignment.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private AuthService authService;

    @Test
    @DisplayName("이메일과 비밀번호가 유효할 때 로그인에 성공한다")
    void testLogin_ValidCredentials() {
        // given
        String email = "test@example.com";
        String password = "password123";
        Member mockMember = mock(Member.class);
        given(mockMember.getPassword()).willReturn(password);
        given(memberRepository.findByEmail(email)).willReturn(Optional.of(mockMember));

        // when
        Member result = authService.login(email, password);

        // then
        assertThat(result).isNotNull();
        verify(memberRepository, times(1)).findByEmail(email);
    }

    @Test
    @DisplayName("비밀번호가 일치하지 않을 때 LoginFailException을 던진다")
    void testLogin_InvalidPassword() {
        // given
        String email = "test@example.com";
        String password = "wrongPassword";
        Member mockMember = mock(Member.class);
        given(mockMember.getPassword()).willReturn("correctPassword");
        given(memberRepository.findByEmail(email)).willReturn(Optional.of(mockMember));

        // when then
        assertThatThrownBy(() -> authService.login(email, password))
                .isInstanceOf(LoginFailException.class);
        verify(memberRepository, times(1)).findByEmail(email);
    }

    @Test
    @DisplayName("이메일이 존재하지 않을 때 LoginFailException을 던진다")
    void testLogin_InvalidEmail() {
        // given
        String email = "nonexistent@example.com";
        String password = "password123";
        given(memberRepository.findByEmail(email)).willReturn(Optional.empty());

        // when then
        assertThatThrownBy(() -> authService.login(email, password))
                .isInstanceOf(LoginFailException.class);
        verify(memberRepository, times(1)).findByEmail(email);
    }
}
