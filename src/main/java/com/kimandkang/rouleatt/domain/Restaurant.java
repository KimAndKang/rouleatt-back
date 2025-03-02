package com.kimandkang.rouleatt.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@Table(indexes = {@Index(name = "idx_location", columnList = "location")})
@Entity
public class Restaurant {

    @Id
    private Long id;

    private String name;

    @Column(nullable = false, columnDefinition = "POINT SRID 4326")
    private Point coordinate;

    private String category;

    private String address;

    @Column(name = "road_address")
    private String roadAddress;

    public String getLocation() {
        return coordinate.toText();
    }
}

