package com.kimandkang.rouleatt.service;

import com.kimandkang.rouleatt.domain.Restaurant;
import com.kimandkang.rouleatt.dto.RestaurantResponses;
import com.kimandkang.rouleatt.repository.RestaurantRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    @Transactional(readOnly = true)
    public RestaurantResponses findNearbyRestaurants(double x, double y, double distance, List<String> exclude) {
        List<Restaurant> restaurants = restaurantRepository.findNearbyRestaurants(x, y, distance, exclude);
        return RestaurantResponses.from(restaurants);
    }
}
