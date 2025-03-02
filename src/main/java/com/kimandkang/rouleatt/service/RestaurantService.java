package com.kimandkang.rouleatt.service;

import com.kimandkang.rouleatt.domain.Restaurant;
import com.kimandkang.rouleatt.dto.RestaurantResponse;
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
    public List<RestaurantResponse> findNearbyRestaurants(double x, double y, double distance) {
        return restaurantRepository.findNearbyRestaurants(x, y, distance)
                .stream()
                .map(RestaurantResponse::from)
                .toList();
    }
}
