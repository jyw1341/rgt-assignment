package com.rgt.assignment.service;

import com.rgt.assignment.domain.Restaurant;
import com.rgt.assignment.exception.InvalidRequestException;
import com.rgt.assignment.repository.RestaurantRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestaurantServiceTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @InjectMocks
    private RestaurantService restaurantService;

    @Test
    @DisplayName("레스토랑 ID가 유효할 때 레스토랑을 성공적으로 반환한다")
    void testGetRestaurant_ValidRestaurantId() {
        // given
        Long restaurantId = 1L;
        Restaurant mockRestaurant = mock(Restaurant.class);
        given(mockRestaurant.getId()).willReturn(restaurantId);
        given(restaurantRepository.findById(restaurantId)).willReturn(Optional.of(mockRestaurant));

        // when
        Restaurant result = restaurantService.getRestaurant(restaurantId);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(restaurantId);
        verify(restaurantRepository, times(1)).findById(restaurantId);
    }

    @Test
    @DisplayName("레스토랑 ID가 유효하지 않을 때 InvalidRequestException을 던진다")
    void testGetRestaurant_InvalidRestaurantId() {
        // given
        Long restaurantId = 999L;
        given(restaurantRepository.findById(restaurantId)).willReturn(Optional.empty());

        // when then
        assertThatThrownBy(() -> restaurantService.getRestaurant(restaurantId))
                .isInstanceOf(InvalidRequestException.class);
        verify(restaurantRepository, times(1)).findById(restaurantId);
    }
}
