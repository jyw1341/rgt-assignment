package com.rgt.assignment.dto;

import com.rgt.assignment.domain.Member;
import lombok.Data;

@Data
public class MemberResponse {

    private String name;
    private String email;

    public MemberResponse(Member member) {
        name = member.getName();
        email = member.getEmail();
    }
}
