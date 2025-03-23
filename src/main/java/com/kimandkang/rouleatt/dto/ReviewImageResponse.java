package com.kimandkang.rouleatt.dto;

import com.kimandkang.rouleatt.domain.ReviewImage;

public record ReviewImageResponse(
        Long id,
        String url
) {
    public static ReviewImageResponse from(ReviewImage image) {
        return new ReviewImageResponse(
                image.getId(),
                image.getImageUrl()
        );
    }
}
