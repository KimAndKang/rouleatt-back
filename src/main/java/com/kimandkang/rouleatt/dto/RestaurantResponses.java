package com.kimandkang.rouleatt.dto;

import com.kimandkang.rouleatt.domain.Restaurant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public record RestaurantResponses(
        Set<String> categories,
        List<RestaurantResponse> restaurants
) {
    public static RestaurantResponses from(List<Restaurant> restaurants, LocalDateTime now) {

        Set<String> categories = restaurants.stream()
                .map(restaurant -> restaurant.getCategory())
                .collect(Collectors.toSet());

        List<RestaurantResponse> restaurantResponses = restaurants.stream()
                .map(restaurant -> RestaurantResponse.from(restaurant, now))
                .toList();

        return new RestaurantResponses(categories, restaurantResponses);
    }
}
