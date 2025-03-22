package com.kimandkang.rouleatt.domain;

import static jakarta.persistence.GenerationType.*;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
public class Menu {

    @Id
    private Long id;

    private String name;

    private String price;

    private boolean isRecommended;

    private String description;

    private int idx;

    @ManyToOne
    @JoinColumn(name = "restaurant_id") // FK
    private Restaurant restaurant;

    @OneToMany(mappedBy = "menu") // 주인 표시
    private List<MenuImage> menuImages;
}
