package com.zomato.service;

import com.zomato.dto.OrderDto;
import com.zomato.entity.Order;
import com.zomato.entity.Restaurant;
import com.zomato.entity.User;
import com.zomato.repository.OrderRepository;
import com.zomato.repository.RestaurantRepository;
import com.zomato.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    public List<OrderDto> getOrdersByUser(Long userId) {
        return orderRepository.findByUserIdOrderByTimestampDesc(userId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<OrderDto> getOrdersByRestaurant(Long restaurantId) {
        return orderRepository.findByRestaurantId(restaurantId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public OrderDto getOrderById(Long id) {
        Optional<Order> orderOpt = orderRepository.findById(id);
        if (orderOpt.isEmpty()) {
            throw new RuntimeException("Order not found");
        }
        return convertToDto(orderOpt.get());
    }

    public OrderDto createOrder(OrderDto orderDto) {
        Optional<User> userOpt = userRepository.findById(orderDto.getUserId());
        if (userOpt.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        Optional<Restaurant> restaurantOpt = restaurantRepository.findById(orderDto.getRestaurantId());
        if (restaurantOpt.isEmpty()) {
            throw new RuntimeException("Restaurant not found");
        }

        Order order = new Order();
        order.setUser(userOpt.get());
        order.setRestaurant(restaurantOpt.get());
        order.setTotalPrice(orderDto.getTotalPrice());
        order.setOrderStatus(Order.OrderStatus.PENDING);

        Order savedOrder = orderRepository.save(order);
        return convertToDto(savedOrder);
    }

    public OrderDto updateOrderStatus(Long id, Order.OrderStatus status) {
        Optional<Order> orderOpt = orderRepository.findById(id);
        if (orderOpt.isEmpty()) {
            throw new RuntimeException("Order not found");
        }

        Order order = orderOpt.get();
        order.setOrderStatus(status);

        Order updatedOrder = orderRepository.save(order);
        return convertToDto(updatedOrder);
    }

    private OrderDto convertToDto(Order order) {
        OrderDto dto = new OrderDto();
        dto.setId(order.getId());
        dto.setUserId(order.getUser().getId());
        dto.setUserName(order.getUser().getName());
        dto.setRestaurantId(order.getRestaurant().getId());
        dto.setRestaurantName(order.getRestaurant().getName());
        dto.setTotalPrice(order.getTotalPrice());
        dto.setOrderStatus(order.getOrderStatus());
        dto.setTimestamp(order.getTimestamp());
        return dto;
    }
}
