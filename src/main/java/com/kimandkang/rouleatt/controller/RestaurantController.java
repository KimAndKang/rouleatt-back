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
        return restaurantService.findNearbyRestaurants(x, y, distance, exclude);
    }
}
