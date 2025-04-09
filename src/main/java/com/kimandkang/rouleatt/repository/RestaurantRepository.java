package com.kimandkang.rouleatt.repository;

import com.kimandkang.rouleatt.domain.Restaurant;

import java.util.List;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface RestaurantRepository extends CrudRepository<Restaurant, Long> {

    @Query(value = """
            SELECT *
            FROM restaurant r
            WHERE ST_Contains(
                (ST_Buffer(ST_GeomFromText(CONCAT('POINT(', :y, ' ', :x, ')'), 4326), :distance)),
                r.coordinate)
            """,
            countQuery = """
            SELECT COUNT(*)
            FROM restaurant r
            WHERE ST_Contains(
                (ST_Buffer(ST_GeomFromText(CONCAT('POINT(', :y, ' ', :x, ')'), 4326), :distance)),
                r.coordinate)
            """,
            nativeQuery = true)
    Page<Restaurant> findNearbyRestaurantsWithoutExclude(
            @Param("x") double x,
            @Param("y") double y,
            @Param("distance") double distance,
            Pageable pageable
    );

    @Query(value = """
        SELECT DISTINCT r.category
        FROM restaurant r
        WHERE ST_Contains(
            (ST_Buffer(ST_GeomFromText(CONCAT('POINT(', :y, ' ', :x, ')'), 4326), :distance)),
            r.coordinate)
        """, nativeQuery = true)
    Set<String> findNearbyCategoriesWithoutExclude(
            @Param("x") double x,
            @Param("y") double y,
            @Param("distance") double distance
    );

    @Query(value = """
            SELECT *
            FROM restaurant r
            WHERE ST_Contains(
                (ST_Buffer(ST_GeomFromText(CONCAT('POINT(', :y, ' ', :x, ')'), 4326), :distance)),
                r.coordinate) 
                AND r.category NOT IN :exclude
            """,
            countQuery = """
            SELECT COUNT(*)
            FROM restaurant r
            WHERE ST_Contains(
                (ST_Buffer(ST_GeomFromText(CONCAT('POINT(', :y, ' ', :x, ')'), 4326), :distance)),
                r.coordinate)
                AND r.category NOT IN :exclude
            """,
            nativeQuery = true)
    Page<Restaurant> findNearbyRestaurantsWithExclude(
            @Param("x") double x,
            @Param("y") double y,
            @Param("distance") double distance,
            @Param("exclude") List<String> exclude,
            Pageable pageable
    );

    @Query(value = """
        SELECT DISTINCT r.category
        FROM restaurant r
        WHERE ST_Contains(
            (ST_Buffer(ST_GeomFromText(CONCAT('POINT(', :y, ' ', :x, ')'), 4326), :distance)),
            r.coordinate)
            AND r.category NOT IN :exclude
        """, nativeQuery = true)
    Set<String> findNearbyCategoriesWithExclude(
            @Param("x") double x,
            @Param("y") double y,
            @Param("distance") double distance,
            @Param("exclude") List<String> exclude
    );
}
