-- Sample Users
INSERT INTO users (id, name, email, password, address, role) VALUES
(1, 'John Doe', 'john@example.com', '$2a$10$rDmFN6ZqJ9K9ZqJ9K9ZqJ9K9ZqJ9K9ZqJ9K9ZqJ9K9ZqJ9K9ZqJ9K9ZqJ9', '123 Main St, City', 'USER'),
(2, 'Jane Smith', 'jane@example.com', '$2a$10$rDmFN6ZqJ9K9ZqJ9K9ZqJ9K9ZqJ9K9ZqJ9K9ZqJ9K9ZqJ9K9ZqJ9K9ZqJ9', '456 Oak Ave, Town', 'USER'),
(3, 'Admin User', 'admin@example.com', '$2a$10$rDmFN6ZqJ9K9ZqJ9K9ZqJ9K9ZqJ9K9ZqJ9K9ZqJ9K9ZqJ9K9ZqJ9K9ZqJ9', '789 Admin Blvd, City', 'ADMIN');

-- Sample Restaurants
INSERT INTO restaurants (id, name, address, cuisine, rating, image_url) VALUES
(1, 'Pizza Palace', '123 Pizza St, City', 'Italian', 4.5, 'https://images.unsplash.com/photo-1513104890138-7c749659a591?w=400'),
(2, 'Burger House', '456 Burger Ave, Town', 'American', 4.2, 'https://images.unsplash.com/photo-1571091718767-18b5b1457add?w=400'),
(3, 'Sushi Master', '789 Sushi Blvd, City', 'Japanese', 4.8, 'https://images.unsplash.com/photo-1579584425555-c3ce17fd4351?w=400'),
(4, 'Taco Fiesta', '321 Taco Lane, Town', 'Mexican', 4.0, 'https://images.unsplash.com/photo-1565299585323-38d6b0865b47?w=400'),
(5, 'Curry Corner', '654 Curry Rd, City', 'Indian', 4.6, 'https://images.unsplash.com/photo-1565557623262-b51c2513a641?w=400');

-- Sample Menu Items
INSERT INTO menu_items (id, name, description, price, restaurant_id) VALUES
(1, 'Margherita Pizza', 'Classic tomato and mozzarella pizza', 12.99, 1),
(2, 'Pepperoni Pizza', 'Spicy pepperoni with melted cheese', 14.99, 1),
(3, 'Classic Burger', 'Beef patty with lettuce, tomato, and cheese', 9.99, 2),
(4, 'Cheese Burger', 'Double cheese with special sauce', 11.99, 2),
(5, 'California Roll', 'Avocado, cucumber, and crab', 8.99, 3),
(6, 'Salmon Nigiri', 'Fresh salmon over seasoned rice', 6.99, 3),
(7, 'Chicken Tacos', 'Grilled chicken with fresh salsa', 7.99, 4),
(8, 'Beef Burrito', 'Spicy beef with rice and beans', 10.99, 4),
(9, 'Butter Chicken', 'Creamy tomato-based curry', 13.99, 5),
(10, 'Naan Bread', 'Fresh baked Indian bread', 2.99, 5);

-- Sample Reviews
INSERT INTO reviews (id, user_id, restaurant_id, rating, comment) VALUES
(1, 1, 1, 5, 'Amazing pizza! Best in town.'),
(2, 2, 1, 4, 'Great food and service.'),
(3, 1, 2, 4, 'Delicious burgers, will come back.'),
(4, 2, 3, 5, 'Fresh sushi, excellent quality.'),
(5, 1, 4, 3, 'Good tacos but a bit expensive.'),
(6, 2, 5, 5, 'Authentic Indian flavors!');

-- Sample Orders
INSERT INTO orders (id, user_id, restaurant_id, total_price, order_status, timestamp) VALUES
(1, 1, 1, 27.98, 'DELIVERED', '2024-01-15 12:30:00'),
(2, 2, 2, 21.98, 'PREPARING', '2024-01-15 13:45:00'),
(3, 1, 3, 15.98, 'PENDING', '2024-01-15 14:20:00');
