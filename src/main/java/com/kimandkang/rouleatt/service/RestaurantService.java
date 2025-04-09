package com.kimandkang.rouleatt.service;

import com.kimandkang.rouleatt.domain.Restaurant;
import com.kimandkang.rouleatt.dto.RestaurantResponses;
import com.kimandkang.rouleatt.repository.RestaurantRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
            LocalDateTime now,
            Pageable pageable
    ) {
        if (now == null) {
            now = LocalDateTime.now();
        }
        if (exclude == null || exclude.isEmpty()) {
            Set<String> categories = restaurantRepository.findNearbyCategoriesWithoutExclude(x, y, distance);
            Page<Restaurant> page = restaurantRepository.findNearbyRestaurantsWithoutExclude(x, y, distance, pageable);
            return RestaurantResponses.from(categories, page, now);
        }

        Set<String> categories = restaurantRepository.findNearbyCategoriesWithExclude(x, y, distance, exclude);
        Page<Restaurant> page = restaurantRepository.findNearbyRestaurantsWithExclude(x, y, distance, exclude, pageable);
        return RestaurantResponses.from(categories, page, now);
    }
}
