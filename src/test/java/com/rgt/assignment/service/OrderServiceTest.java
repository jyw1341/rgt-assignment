package com.rgt.assignment.service;

import com.rgt.assignment.domain.*;
import com.rgt.assignment.dto.LoginInfo;
import com.rgt.assignment.dto.OrderRequest;
import com.rgt.assignment.repository.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    @Mock
    private OrderRepository orderRepository;

    @Mock
    private RestaurantService restaurantService;

    @Mock
    private MemberService memberService;

    @Mock
    private MenuService menuService;

    @InjectMocks
    private OrderService orderService;

    @Test
    @DisplayName("주문이 존재하지 않을 때 새 주문을 생성하고 저장한다")
    void testAddOrder_CreateNewOrder() {
        // given
        Long restaurantId = 123L;
        int tableNumber = 1;
        Long memberId = 1L;
        String memberName = "Test User";
        Long menuId = 2L;
        int orderPrice = 10000;
        int quantity = 2;

        LoginInfo loginInfo = new LoginInfo(memberId, memberName, restaurantId, tableNumber);

        OrderRequest orderRequest = new OrderRequest(1L, menuId, orderPrice, quantity);
        List<OrderRequest> orderRequests = Collections.singletonList(orderRequest);

        Restaurant restaurant = mock(Restaurant.class);
        Member member = mock(Member.class);
        Menu menu = mock(Menu.class);
        Order order = new Order(restaurant, tableNumber, OrderStatus.CONFIRM, LocalDateTime.now());

        given(orderRepository.findByRestaurantIdAndTableNumberAndStatus(restaurantId, tableNumber, OrderStatus.CONFIRM))
                .willReturn(Optional.empty());
        given(restaurantService.getRestaurant(restaurantId)).willReturn(restaurant);
        given(memberService.getMember(memberId)).willReturn(member);
        given(menuService.getMenu(menuId)).willReturn(menu);
        given(orderRepository.save(any(Order.class))).willReturn(order);

        // when
        Order result = orderService.addOrder(orderRequests, loginInfo);

        // then
        assertThat(result).isNotNull();
        verify(orderRepository, times(1)).findByRestaurantIdAndTableNumberAndStatus(restaurantId, tableNumber, OrderStatus.CONFIRM);
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    @DisplayName("주문이 이미 존재할 때 기존 주문을 사용한다")
    void testAddOrder_ExistingOrder() {
        // given
        Long restaurantId = 123L;
        int tableNumber = 1;
        Long memberId = 1L;
        Long menuId = 2L;
        int orderPrice = 10000;
        int quantity = 2;

        LoginInfo loginInfo = new LoginInfo(memberId, "Test User", restaurantId, tableNumber);

        List<OrderRequest> orderRequests = Collections.singletonList(new OrderRequest(1L, menuId, orderPrice, quantity));
        Order existingOrder = mock(Order.class);
        Member member = mock(Member.class);
        Menu menu = mock(Menu.class);

        given(orderRepository.findByRestaurantIdAndTableNumberAndStatus(restaurantId, tableNumber, OrderStatus.CONFIRM))
                .willReturn(Optional.of(existingOrder));
        given(memberService.getMember(memberId)).willReturn(member);
        given(menuService.getMenu(menuId)).willReturn(menu);

        // when
        Order result = orderService.addOrder(orderRequests, loginInfo);

        // then
        assertThat(result).isEqualTo(existingOrder);
        verify(orderRepository, times(1)).findByRestaurantIdAndTableNumberAndStatus(restaurantId, tableNumber, OrderStatus.CONFIRM);
        verify(orderRepository, times(1)).save(any(Order.class));
    }
}
