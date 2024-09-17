package com.rgt.assignment.repository;

import com.rgt.assignment.domain.Order;
import com.rgt.assignment.domain.OrderStatus;
import com.rgt.assignment.domain.Restaurant;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ExtendWith(MockitoExtension.class)
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    private Restaurant restaurant;

    @BeforeEach
    void setUp() {
        restaurant = new Restaurant(123L, "test", 10);
        restaurantRepository.save(restaurant);
    }

    @AfterEach
    void clear() {
        orderRepository.deleteAll();
        restaurantRepository.deleteAll();
    }

    @Test
    @DisplayName("주문이 존재할 때 올바른 주문을 반환한다")
    void testFindByRestaurantIdAndTableNumberAndStatus_Found() {
        // given
        Long restaurantId = 123L;
        int tableNumber = 1;
        OrderStatus status = OrderStatus.CONFIRM;
        Restaurant restaurant = new Restaurant(restaurantId, "test", 10);
        Order order = new Order(restaurant, tableNumber, status, LocalDateTime.now());
        orderRepository.save(order);

        // when
        Optional<Order> result = orderRepository.findByRestaurantIdAndTableNumberAndStatus(restaurantId, tableNumber, status);

        // then
        assertThat(result).isPresent();
        assertThat(result.get().getRestaurant().getId()).isEqualTo(restaurantId);
        assertThat(result.get().getTableNumber()).isEqualTo(tableNumber);
        assertThat(result.get().getStatus()).isEqualTo(status);
    }

    @Test
    @DisplayName("주문이 존재하지 않을 때 빈 Optional을 반환한다")
    void testFindByRestaurantIdAndTableNumberAndStatus_NotFound() {
        // given
        Long restaurantId = 123L;
        int tableNumber = 1;
        OrderStatus status = OrderStatus.CONFIRM;

        // when
        Optional<Order> result = orderRepository.findByRestaurantIdAndTableNumberAndStatus(restaurantId, tableNumber, status);

        // then
        assertThat(result).isNotPresent();
    }

    @Test
    @DisplayName("레스토랑 ID와 테이블 넘버가 같지만 상태가 다른 경우 빈 Optional을 반환한다")
    void testFindByRestaurantIdAndTableNumberAndStatus_DifferentStatus() {
        // given
        Long restaurantId = 123L;
        int tableNumber = 1;

        Restaurant restaurant = new Restaurant(restaurantId, "test", 10);
        Order order1 = new Order(restaurant, tableNumber, OrderStatus.COMPLETE, LocalDateTime.now());
        orderRepository.save(order1);
        Order order2 = new Order(restaurant, tableNumber, OrderStatus.CANCEL, LocalDateTime.now());
        orderRepository.save(order2);

        // when
        Optional<Order> result = orderRepository.findByRestaurantIdAndTableNumberAndStatus(restaurantId, tableNumber, OrderStatus.CONFIRM);

        // then
        assertThat(result).isNotPresent();
    }
}
