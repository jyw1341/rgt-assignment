package com.rgt.assignment.dto;

import com.rgt.assignment.domain.OrderMenu;
import lombok.Data;

@Data
public class OrderMenuResponse {

    private String customer;
    private String menuName;
    private Integer quantity;
    private Integer orderPrice;

    public OrderMenuResponse(OrderMenu orderMenu) {
        customer = orderMenu.getMember().getName();
        menuName = orderMenu.getMenu().getName();
        quantity = orderMenu.getQuantity();
        orderPrice = orderMenu.getOrderPrice();
    }
}
