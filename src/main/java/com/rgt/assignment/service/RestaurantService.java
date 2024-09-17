package com.rgt.assignment.service;

import com.rgt.assignment.domain.Restaurant;
import com.rgt.assignment.exception.InvalidRequestException;
import com.rgt.assignment.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public Restaurant getRestaurant(Long restaurantId) {
        return restaurantRepository.findById(restaurantId).orElseThrow(InvalidRequestException::new);
    }
}
