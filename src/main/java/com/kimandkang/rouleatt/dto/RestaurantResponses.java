package com.kimandkang.rouleatt.dto;

import com.kimandkang.rouleatt.domain.Restaurant;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public record RestaurantResponses(
        Set<String> categories,
        List<RestaurantResponse> restaurants
) {
    public static RestaurantResponses from(List<Restaurant> restaurants) {

        Set<String> categories = restaurants.stream()
                .map(Restaurant::getCategory)
                .flatMap(category -> Arrays.stream(category.split(",")))
                .collect(Collectors.toSet());

        List<RestaurantResponse> restaurantResponses = restaurants.stream()
                .map(RestaurantResponse::from)
                .toList();

        return new RestaurantResponses(categories, restaurantResponses);
    }
}
