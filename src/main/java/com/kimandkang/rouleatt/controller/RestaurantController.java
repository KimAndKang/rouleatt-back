package com.kimandkang.rouleatt.controller;

import com.kimandkang.rouleatt.dto.RestaurantResponse;
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
    public List<RestaurantResponse> findNearbyRestaurants(
            @RequestParam double x,
            @RequestParam double y,
            @RequestParam(defaultValue = "500", name = "d") double distance
    ) {
        return restaurantService.findNearbyRestaurants(x, y, distance);
    }
}
