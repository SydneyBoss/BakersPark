package com.zomato.service;

import com.zomato.dto.RestaurantDto;
import com.zomato.entity.Restaurant;
import com.zomato.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    public List<RestaurantDto> getAllRestaurants() {
        return restaurantRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public RestaurantDto getRestaurantById(Long id) {
        Optional<Restaurant> restaurantOpt = restaurantRepository.findById(id);
        if (restaurantOpt.isEmpty()) {
            throw new RuntimeException("Restaurant not found");
        }
        return convertToDto(restaurantOpt.get());
    }

    public List<RestaurantDto> searchRestaurants(String searchTerm) {
        return restaurantRepository.searchRestaurants(searchTerm).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<RestaurantDto> getRestaurantsByCuisine(String cuisine) {
        return restaurantRepository.findByCuisine(cuisine).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public RestaurantDto createRestaurant(RestaurantDto restaurantDto) {
        Restaurant restaurant = new Restaurant();
        restaurant.setName(restaurantDto.getName());
        restaurant.setAddress(restaurantDto.getAddress());
        restaurant.setCuisine(restaurantDto.getCuisine());
        restaurant.setRating(restaurantDto.getRating());
        restaurant.setImageUrl(restaurantDto.getImageUrl());

        Restaurant savedRestaurant = restaurantRepository.save(restaurant);
        return convertToDto(savedRestaurant);
    }

    public RestaurantDto updateRestaurant(Long id, RestaurantDto restaurantDto) {
        Optional<Restaurant> restaurantOpt = restaurantRepository.findById(id);
        if (restaurantOpt.isEmpty()) {
            throw new RuntimeException("Restaurant not found");
        }

        Restaurant restaurant = restaurantOpt.get();
        restaurant.setName(restaurantDto.getName());
        restaurant.setAddress(restaurantDto.getAddress());
        restaurant.setCuisine(restaurantDto.getCuisine());
        restaurant.setRating(restaurantDto.getRating());
        restaurant.setImageUrl(restaurantDto.getImageUrl());

        Restaurant updatedRestaurant = restaurantRepository.save(restaurant);
        return convertToDto(updatedRestaurant);
    }

    public void deleteRestaurant(Long id) {
        if (!restaurantRepository.existsById(id)) {
            throw new RuntimeException("Restaurant not found");
        }
        restaurantRepository.deleteById(id);
    }

    private RestaurantDto convertToDto(Restaurant restaurant) {
        RestaurantDto dto = new RestaurantDto();
        dto.setId(restaurant.getId());
        dto.setName(restaurant.getName());
        dto.setAddress(restaurant.getAddress());
        dto.setCuisine(restaurant.getCuisine());
        dto.setRating(restaurant.getRating());
        dto.setImageUrl(restaurant.getImageUrl());
        return dto;
    }
}
