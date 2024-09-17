package com.rgt.assignment.dto;

import com.rgt.assignment.domain.Order;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class OrderResponse {

    private Long orderId;
    private String restaurantName;
    private Integer tableNumber;
    private List<OrderMenuResponse> orderMenuResponses;
    private Integer totalPrice;

    public OrderResponse(Order order) {
        if(order != null) {
            orderId = order.getId();
            restaurantName = order.getRestaurant().getName();
            tableNumber = order.getTableNumber();
            orderMenuResponses = order.getOrderMenus()
                    .stream()
                    .map(OrderMenuResponse::new)
                    .collect(Collectors.toList());
            totalPrice = orderMenuResponses.stream()
                    .mapToInt(orderMenuResponse -> orderMenuResponse.getOrderPrice() * orderMenuResponse.getQuantity())
                    .sum();
        }
    }
}
