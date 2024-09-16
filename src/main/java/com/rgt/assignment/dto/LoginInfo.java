package com.rgt.assignment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginInfo {

    private Long memberId;
    private Long restaurantId;
    private Integer tableNumber;
}
