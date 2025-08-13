package com.zomato.repository;

import com.zomato.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    List<Restaurant> findByCuisine(String cuisine);

    List<Restaurant> findByNameContainingIgnoreCase(String name);

    List<Restaurant> findByAddressContainingIgnoreCase(String address);

    @Query("SELECT r FROM Restaurant r WHERE r.name LIKE %:searchTerm% OR r.cuisine LIKE %:searchTerm% OR r.address LIKE %:searchTerm%")
    List<Restaurant> searchRestaurants(@Param("searchTerm") String searchTerm);
}
