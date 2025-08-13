package com.zomato.service;

import com.zomato.dto.ReviewDto;
import com.zomato.entity.Review;
import com.zomato.entity.Restaurant;
import com.zomato.entity.User;
import com.zomato.repository.ReviewRepository;
import com.zomato.repository.RestaurantRepository;
import com.zomato.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    public List<ReviewDto> getReviewsByRestaurant(Long restaurantId) {
        return reviewRepository.findByRestaurantIdOrderByTimestampDesc(restaurantId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public ReviewDto getReviewById(Long id) {
        Optional<Review> reviewOpt = reviewRepository.findById(id);
        if (reviewOpt.isEmpty()) {
            throw new RuntimeException("Review not found");
        }
        return convertToDto(reviewOpt.get());
    }

    public ReviewDto createReview(ReviewDto reviewDto) {
        Optional<User> userOpt = userRepository.findById(reviewDto.getUserId());
        if (userOpt.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        Optional<Restaurant> restaurantOpt = restaurantRepository.findById(reviewDto.getRestaurantId());
        if (restaurantOpt.isEmpty()) {
            throw new RuntimeException("Restaurant not found");
        }

        Review review = new Review();
        review.setUser(userOpt.get());
        review.setRestaurant(restaurantOpt.get());
        review.setRating(reviewDto.getRating());
        review.setComment(reviewDto.getComment());

        Review savedReview = reviewRepository.save(review);

        // Update restaurant rating
        updateRestaurantRating(restaurantOpt.get());

        return convertToDto(savedReview);
    }

    public ReviewDto updateReview(Long id, ReviewDto reviewDto) {
        Optional<Review> reviewOpt = reviewRepository.findById(id);
        if (reviewOpt.isEmpty()) {
            throw new RuntimeException("Review not found");
        }

        Review review = reviewOpt.get();
        review.setRating(reviewDto.getRating());
        review.setComment(reviewDto.getComment());

        Review updatedReview = reviewRepository.save(review);

        // Update restaurant rating
        updateRestaurantRating(review.getRestaurant());

        return convertToDto(updatedReview);
    }

    public void deleteReview(Long id) {
        Optional<Review> reviewOpt = reviewRepository.findById(id);
        if (reviewOpt.isEmpty()) {
            throw new RuntimeException("Review not found");
        }

        Restaurant restaurant = reviewOpt.get().getRestaurant();
        reviewRepository.deleteById(id);

        // Update restaurant rating
        updateRestaurantRating(restaurant);
    }

    private void updateRestaurantRating(Restaurant restaurant) {
        List<Review> reviews = reviewRepository.findByRestaurantId(restaurant.getId());
        if (!reviews.isEmpty()) {
            double avgRating = reviews.stream()
                    .mapToInt(Review::getRating)
                    .average()
                    .orElse(0.0);
            restaurant.setRating(avgRating);
            restaurantRepository.save(restaurant);
        }
    }

    private ReviewDto convertToDto(Review review) {
        ReviewDto dto = new ReviewDto();
        dto.setId(review.getId());
        dto.setUserId(review.getUser().getId());
        dto.setUserName(review.getUser().getName());
        dto.setRestaurantId(review.getRestaurant().getId());
        dto.setRating(review.getRating());
        dto.setComment(review.getComment());
        dto.setTimestamp(review.getTimestamp());
        return dto;
    }
}
