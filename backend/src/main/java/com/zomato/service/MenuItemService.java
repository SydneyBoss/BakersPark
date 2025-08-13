package com.zomato.service;

import com.zomato.dto.MenuItemDto;
import com.zomato.entity.MenuItem;
import com.zomato.entity.Restaurant;
import com.zomato.repository.MenuItemRepository;
import com.zomato.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MenuItemService {

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    public List<MenuItemDto> getMenuItemsByRestaurant(Long restaurantId) {
        return menuItemRepository.findByRestaurantId(restaurantId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public MenuItemDto getMenuItemById(Long id) {
        Optional<MenuItem> menuItemOpt = menuItemRepository.findById(id);
        if (menuItemOpt.isEmpty()) {
            throw new RuntimeException("Menu item not found");
        }
        return convertToDto(menuItemOpt.get());
    }

    public MenuItemDto createMenuItem(MenuItemDto menuItemDto) {
        Optional<Restaurant> restaurantOpt = restaurantRepository.findById(menuItemDto.getRestaurantId());
        if (restaurantOpt.isEmpty()) {
            throw new RuntimeException("Restaurant not found");
        }

        MenuItem menuItem = new MenuItem();
        menuItem.setName(menuItemDto.getName());
        menuItem.setDescription(menuItemDto.getDescription());
        menuItem.setPrice(menuItemDto.getPrice());
        menuItem.setRestaurant(restaurantOpt.get());

        MenuItem savedMenuItem = menuItemRepository.save(menuItem);
        return convertToDto(savedMenuItem);
    }

    public MenuItemDto updateMenuItem(Long id, MenuItemDto menuItemDto) {
        Optional<MenuItem> menuItemOpt = menuItemRepository.findById(id);
        if (menuItemOpt.isEmpty()) {
            throw new RuntimeException("Menu item not found");
        }

        MenuItem menuItem = menuItemOpt.get();
        menuItem.setName(menuItemDto.getName());
        menuItem.setDescription(menuItemDto.getDescription());
        menuItem.setPrice(menuItemDto.getPrice());

        MenuItem updatedMenuItem = menuItemRepository.save(menuItem);
        return convertToDto(updatedMenuItem);
    }

    public void deleteMenuItem(Long id) {
        if (!menuItemRepository.existsById(id)) {
            throw new RuntimeException("Menu item not found");
        }
        menuItemRepository.deleteById(id);
    }

    private MenuItemDto convertToDto(MenuItem menuItem) {
        MenuItemDto dto = new MenuItemDto();
        dto.setId(menuItem.getId());
        dto.setName(menuItem.getName());
        dto.setDescription(menuItem.getDescription());
        dto.setPrice(menuItem.getPrice());
        dto.setRestaurantId(menuItem.getRestaurant().getId());
        return dto;
    }
}
