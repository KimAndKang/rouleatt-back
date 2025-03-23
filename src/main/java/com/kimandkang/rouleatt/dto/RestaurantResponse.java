package com.kimandkang.rouleatt.dto;

import com.kimandkang.rouleatt.domain.Restaurant;
import com.kimandkang.rouleatt.utils.BizHourUtils;
import com.kimandkang.rouleatt.utils.MenuUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "음식점 조회 응답 DTO")
public record RestaurantResponse(
        Long id,
        String name,
        double x,
        double y,
        String category,
        String address,
        String roadAddress,
        @Schema(description = "영업 여부", example = "`영업 시간 정보가 없어요`, `영업 준비 중`, `영업 종료`, `브레이크 타임`, `영업 중` 문자열 중 하나")
        String isOpen,
        @Schema(description = "가격대", example = "`4500`, `4500~7500`, `가격 정보 없음` 문자열 형태 중 하나")
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
