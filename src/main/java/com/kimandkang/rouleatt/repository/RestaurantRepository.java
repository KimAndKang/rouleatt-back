package com.kimandkang.rouleatt.repository;

import com.kimandkang.rouleatt.domain.Restaurant;
import java.util.List;
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
                    AND r.category NOT IN :exclude
            """, nativeQuery = true)
    List<Restaurant> findNearbyRestaurants(
            @Param("x") double x,
            @Param("y") double y,
            @Param("distance") double distance,
            @Param("exclude") List<String> exclude
    );
}
