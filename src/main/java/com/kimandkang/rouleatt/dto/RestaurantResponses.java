package com.kimandkang.rouleatt.dto;

import com.kimandkang.rouleatt.domain.Restaurant;
import com.kimandkang.rouleatt.utils.RestaurantUtils;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;

public record RestaurantResponses(
        Set<String> categories,
        List<RestaurantResponse> restaurants,
        int page,
        int size,
        long totalElements,
        int totalPages,
        boolean isLast
) {
    public static RestaurantResponses from(Set<String> categories, Page<Restaurant> page, LocalDateTime now) {

        Set<String> normalizedCategories = categories.stream()
                .map(category -> RestaurantUtils.normalize(category))
                .collect(Collectors.toSet());

        List<RestaurantResponse> restaurantResponses = page.getContent().stream()
                .map(restaurant -> RestaurantResponse.from(restaurant, now))
                .toList();

        return new RestaurantResponses(
                normalizedCategories,
                restaurantResponses,
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast());
    }
}
