package com.kimandkang.rouleatt.domain;

import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@Entity
public class Review {

    @Id
    private Long id;

    private String name;

    private String type;

    @Column(name = "review_url")
    private String reviewUrl;

    private String title;

    private String idx;

    private String content;

    @Column(name = "profile_url")
    private String profileUrl;

    @Column(name = "author_name")
    private String authorName;

    @Column(name = "created_at")
    private String createdAt;

    @ManyToOne
    @JoinColumn(name = "restaurant_id") // FK
    private Restaurant restaurant;

    @OneToMany(mappedBy = "review") // 주인 표시
    private List<ReviewImage> reviewImages;
}
