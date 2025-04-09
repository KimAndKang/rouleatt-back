package com.kimandkang.rouleatt.controller;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME;

import com.kimandkang.rouleatt.dto.RestaurantResponses;
import com.kimandkang.rouleatt.service.RestaurantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
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
            @RequestParam(required = false) @DateTimeFormat(iso = DATE_TIME) LocalDateTime now,
            @Parameter(description = "페이지네이션", example = "&page=0&size=10")
            Pageable pageable
    ) {
        return restaurantService.findNearbyRestaurants(x, y, d, exclude, now, pageable);
    }
}
