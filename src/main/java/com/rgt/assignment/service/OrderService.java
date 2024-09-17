package com.rgt.assignment.service;

import com.rgt.assignment.domain.*;
import com.rgt.assignment.dto.LoginInfo;
import com.rgt.assignment.dto.OrderRequest;
import com.rgt.assignment.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final RestaurantService restaurantService;
    private final MemberService memberService;
    private final MenuService menuService;

    @Transactional
    public Order addOrder(List<OrderRequest> requests, LoginInfo loginInfo) {
        Order order = null;
        Member member = memberService.getMember(loginInfo.getMemberId());
        Optional<Order> optional = orderRepository.findByRestaurantIdAndTableNumberAndStatus(
                loginInfo.getRestaurantId(),
                loginInfo.getTableNumber(),
                OrderStatus.CONFIRM
        );

        if(optional.isPresent()) {
            order = optional.get();
        }
        if(optional.isEmpty()) {
            Restaurant restaurant = restaurantService.getRestaurant(loginInfo.getRestaurantId());
            order = new Order(restaurant, loginInfo.getTableNumber(), OrderStatus.CONFIRM, LocalDateTime.now());
        }

        for (OrderRequest request : requests) {
            Menu menu = menuService.getMenu(request.getMenuId());
            order.addOrderMenu(new OrderMenu(member, order, menu, request.getOrderPrice(), request.getQuantity()));
        }
        orderRepository.save(order);

        return order;
    }
}
