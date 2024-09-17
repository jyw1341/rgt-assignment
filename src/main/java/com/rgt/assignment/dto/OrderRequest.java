package com.rgt.assignment.dto;

import lombok.Data;

@Data
public class OrderRequest {

    private Long cartId;
    private Long menuId;
    private Integer orderPrice;
    private Integer quantity;
}
