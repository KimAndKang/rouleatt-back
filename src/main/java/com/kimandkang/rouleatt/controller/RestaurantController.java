package com.kimandkang.rouleatt.controller;

import com.kimandkang.rouleatt.dto.RestaurantResponses;
import com.kimandkang.rouleatt.service.RestaurantService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restaurants")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @GetMapping("/nearby")
    public RestaurantResponses findNearbyRestaurants(
            @RequestParam double x,
            @RequestParam double y,
            @RequestParam(defaultValue = "100", name = "distance") double distance,
            @RequestParam(required = false, name = "exclude") List<String> exclude
    ) {
        // 거리가 벗어날 경우
        if (distance < 100 || distance > 1000) {
            distance = 100;
        }
        // 제외할 카테고리가 존재하지 않을 경우
        if (exclude == null || exclude.isEmpty()) {
            return restaurantService.findNearbyRestaurantsWithoutExclude(x, y, distance);
        }
        // 제외할 카테고리가 존재할 경우
        return restaurantService.findNearbyRestaurantsWithExclude(x, y, distance, exclude);
    }
}
