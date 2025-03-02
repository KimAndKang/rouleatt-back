package com.kimandkang.rouleatt.dto;

import com.kimandkang.rouleatt.domain.Restaurant;

public record RestaurantResponse(
        Long id,
        String name,
        String location,
        String category,
        String address,
        String roadAddress
) {
    public static RestaurantResponse from(Restaurant restaurant) {
        return new RestaurantResponse(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getLocation(),
                restaurant.getCategory(),
                restaurant.getAddress(),
                restaurant.getRoadAddress()
        );
    }
}
