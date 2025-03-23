package com.kimandkang.rouleatt.service;

import com.kimandkang.rouleatt.domain.Restaurant;
import com.kimandkang.rouleatt.dto.RestaurantResponses;
import com.kimandkang.rouleatt.repository.RestaurantRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    @Transactional(readOnly = true)
    public RestaurantResponses findNearbyRestaurants(
            double x,
            double y,
            double distance,
            List<String> exclude,
            LocalDateTime now
    ) {
        if (now == null) {
            now = LocalDateTime.now();
        }
        if (exclude == null || exclude.isEmpty()) {
            List<Restaurant> restaurants = restaurantRepository.findNearbyRestaurantsWithoutExclude(x, y, distance);
            return RestaurantResponses.from(restaurants, now);
        }
        List<Restaurant> restaurants = restaurantRepository.findNearbyRestaurantsWithExclude(x, y, distance, exclude);
        return RestaurantResponses.from(restaurants, now);
    }
}
