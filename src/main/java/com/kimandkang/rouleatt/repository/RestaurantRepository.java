package com.kimandkang.rouleatt.repository;

import com.kimandkang.rouleatt.domain.Restaurant;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RestaurantRepository {

    private final EntityManager em;

    public List<Restaurant> findNearbyRestaurants(double x, double y, double distance, List<String> excepts) {
        StringBuilder sql = new StringBuilder("""
            SELECT * FROM restaurant r
            WHERE ST_Contains(
                (ST_Buffer(
                    ST_GeomFromText(CONCAT('POINT(', :y, ' ', :x, ')'), 4326),
                    :distance)),
                r.coordinate)
            """);

        if (excepts != null && !excepts.isEmpty()) {
            for (int i = 0; i < excepts.size(); i++) {
                sql.append(" AND r.category NOT LIKE :except").append(i);
            }
        }

        Query query = em.createNativeQuery(sql.toString(), Restaurant.class);
        query.setParameter("x", x);
        query.setParameter("y", y);
        query.setParameter("distance", distance);

        if (excepts != null && !excepts.isEmpty()) {
            for (int i = 0; i < excepts.size(); i++) {
                query.setParameter("except" + i, "%" + excepts.get(i) + "%");
            }
        }

        return query.getResultList();
    }
}
