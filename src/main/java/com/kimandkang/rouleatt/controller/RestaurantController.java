package com.kimandkang.rouleatt.controller;

import com.kimandkang.rouleatt.dto.RestaurantResponses;
import com.kimandkang.rouleatt.service.RestaurantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "음식점 조회 컨트롤러")
@RestController
@RequestMapping("/restaurants")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @GetMapping("/nearby")
    @Operation(summary = "음식점 조회 메서드")
    public RestaurantResponses findNearbyRestaurants(
            @Parameter(description = "경도(longitude)", example = "127.0123456789")
            @RequestParam double x,
            @Parameter(description = "위도(latitude)", example = "37.0123456789")
            @RequestParam double y,
            @Parameter(description = "거리(distance)", example = "1000")
            @RequestParam(defaultValue = "100") double d,
            @Parameter(description = "제외할 카테고리", example = "오리요리,생선회")
            @RequestParam(required = false) List<String> exclude,
            @Parameter(description = "요청 시각", example = "2025-03-22T15:30:00")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime now
    ) {
        // 거리가 벗어날 경우
        if (d < 100 || d > 1000) {
            d = 100;
        }
        // 제외할 카테고리가 존재하지 않을 경우
        if (exclude == null || exclude.isEmpty()) {
            return restaurantService.findNearbyRestaurantsWithoutExclude(x, y, d, now);
        }
        // 제외할 카테고리가 존재할 경우
        return restaurantService.findNearbyRestaurantsWithExclude(x, y, d, exclude, now);
    }
}
