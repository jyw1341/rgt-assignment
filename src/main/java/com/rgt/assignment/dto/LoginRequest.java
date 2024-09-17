package com.rgt.assignment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginRequest {

    private String email;
    private String password;
    private Long restaurantId;
    private Integer tableNumber;
}
