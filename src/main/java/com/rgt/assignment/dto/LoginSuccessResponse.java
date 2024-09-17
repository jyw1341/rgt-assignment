package com.rgt.assignment.dto;

import com.rgt.assignment.domain.Member;
import lombok.Data;

@Data
public class LoginSuccessResponse {

    private boolean success;
    private MemberResponse memberResponse;

    public LoginSuccessResponse(Member member) {
        this.success = true;
        this.memberResponse = new MemberResponse(member);
    }
}
