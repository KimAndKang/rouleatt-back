package com.kimandkang.rouleatt.dto;

import com.kimandkang.rouleatt.domain.Review;
import java.util.List;

public record ReviewResponse(
        Long id,
        String title,
        String content,
        String authorName,
        String profileUrl,
        List<ReviewImageResponse> reviewImages
) {
    public static ReviewResponse from(Review review) {
        return new ReviewResponse(
                review.getId(),
                review.getTitle(),
                review.getContent(),
                review.getAuthorName(),
                review.getProfileUrl(),
                review.getReviewImages().stream().map(ReviewImageResponse::from).toList()
        );
    }
}
