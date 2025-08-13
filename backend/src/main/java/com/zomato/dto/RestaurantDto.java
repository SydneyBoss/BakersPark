package com.zomato.dto;

import jakarta.validation.constraints.NotBlank;
import java.util.List;

public class RestaurantDto {
    private Long id;

    @NotBlank(message = "Restaurant name is required")
    private String name;

    private String address;
    private String cuisine;
    private Double rating;
    private String imageUrl;
    private List<MenuItemDto> menuItems;
    private List<ReviewDto> reviews;

    // Constructors
    public RestaurantDto() {
    }

    public RestaurantDto(String name, String address, String cuisine, Double rating, String imageUrl) {
        this.name = name;
        this.address = address;
        this.cuisine = cuisine;
        this.rating = rating;
        this.imageUrl = imageUrl;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<MenuItemDto> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(List<MenuItemDto> menuItems) {
        this.menuItems = menuItems;
    }

    public List<ReviewDto> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewDto> reviews) {
        this.reviews = reviews;
    }
}
