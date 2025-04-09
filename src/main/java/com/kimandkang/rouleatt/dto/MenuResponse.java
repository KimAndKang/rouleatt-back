package com.kimandkang.rouleatt.dto;

import com.kimandkang.rouleatt.domain.Menu;
import java.util.List;

public record MenuResponse(
        Long id,
        String name,
        String price,
        boolean isRecommended,
        String description,
        int menuIdx,
        List<MenuImageResponse> menuImages
) {
    public static MenuResponse from(Menu menu) {
        return new MenuResponse(
                menu.getId(),
                menu.getName(),
                menu.getPrice(),
                menu.isRecommended(),
                menu.getDescription(),
                menu.getMenuIdx(),
                menu.getMenuImages().stream().map(MenuImageResponse::from).toList()
        );
    }
}
