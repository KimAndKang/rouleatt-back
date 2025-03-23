package com.kimandkang.rouleatt.dto;

import com.kimandkang.rouleatt.domain.MenuImage;

public record MenuImageResponse(
        Long id,
        String url
) {
    public static MenuImageResponse from(MenuImage image) {
        return new MenuImageResponse(
                image.getId(),
                image.getImageUrl()
        );
    }

}
