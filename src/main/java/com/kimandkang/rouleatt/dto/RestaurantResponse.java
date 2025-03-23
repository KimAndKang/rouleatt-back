package com.kimandkang.rouleatt.dto;

import com.kimandkang.rouleatt.domain.Restaurant;
import com.kimandkang.rouleatt.utils.BizHourUtils;
import com.kimandkang.rouleatt.utils.MenuUtils;
import java.time.LocalDateTime;
import java.util.List;

public record RestaurantResponse(
        Long id,
        String name,
        double x,
        double y,
        String category,
        String address,
        String roadAddress,
        String isOpen,
        String avgPrice,
        List<MenuResponse> menus,
        List<ReviewResponse> reviews,
        List<BizHourResponse> bizHours
) {
    public static RestaurantResponse from(Restaurant restaurant, LocalDateTime now) {
        return new RestaurantResponse(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getCoordinate().getX(),
                restaurant.getCoordinate().getY(),
                restaurant.getCategory(),
                restaurant.getAddress(),
                restaurant.getRoadAddress(),
                BizHourUtils.isOpen(restaurant, now),
                MenuUtils.avgPrice(restaurant),
                restaurant.getMenus().stream().map(MenuResponse::from).toList(),
                restaurant.getReviews().stream().map(ReviewResponse::from).toList(),
                BizHourUtils.sortBizHours(restaurant.getBizHours())
        );
    }


}
