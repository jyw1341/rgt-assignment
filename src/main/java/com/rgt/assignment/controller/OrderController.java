package com.rgt.assignment.controller;

import com.rgt.assignment.argumentsresolver.Login;
import com.rgt.assignment.dto.LoginInfo;
import com.rgt.assignment.dto.OrderRequest;
import com.rgt.assignment.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/order")
    public void addOrder(@RequestBody List<OrderRequest> orderRequests, @Login LoginInfo loginInfo) {
        orderService.addOrder(orderRequests, loginInfo);
    }
}
