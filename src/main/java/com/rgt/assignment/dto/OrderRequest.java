package com.rgt.assignment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderRequest {

    private Long cartId;
    private Long menuId;
    private Integer orderPrice;
    private Integer quantity;
}
