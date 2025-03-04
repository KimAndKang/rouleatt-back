package com.kimandkang.rouleatt.dto;

import com.kimandkang.rouleatt.domain.Restaurant;

public record RestaurantResponse(
        Long id,
        String name,
        double x,
        double y,
        String category,
        String address,
        String roadAddress
) {
    public static RestaurantResponse from(Restaurant restaurant) {
        return new RestaurantResponse(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getCoordinate().getX(),
                restaurant.getCoordinate().getY(),
                restaurant.getCategory(),
                restaurant.getAddress(),
                restaurant.getRoadAddress()
        );
    }
}
