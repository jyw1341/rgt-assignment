package com.rgt.assignment.service;

import com.rgt.assignment.domain.Member;
import com.rgt.assignment.exception.InvalidRequestException;
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
class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberService memberService;

    @Test
    @DisplayName("멤버 ID가 유효할 때 멤버를 성공적으로 반환한다")
    void testGetMember_ValidMemberId() {
        // given
        Long memberId = 1L;
        Member mockMember = mock(Member.class);
        given(mockMember.getId()).willReturn(memberId);
        given(memberRepository.findById(memberId)).willReturn(Optional.of(mockMember));

        // when
        Member result = memberService.getMember(memberId);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(memberId);
        verify(memberRepository, times(1)).findById(memberId);
    }

    @Test
    @DisplayName("멤버 ID가 유효하지 않을 때 InvalidRequestException을 던진다")
    void testGetMember_InvalidMemberId() {
        // given
        Long memberId = 999L;
        given(memberRepository.findById(memberId)).willReturn(Optional.empty());

        // when then
        assertThatThrownBy(() -> memberService.getMember(memberId)).isInstanceOf(InvalidRequestException.class);
        verify(memberRepository, times(1)).findById(memberId);
    }
}
