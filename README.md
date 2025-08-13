# Zomato Clone - Full Stack Application

A complete food delivery application built with Java Spring Boot backend and React frontend.

## Features

### Backend Features

- User authentication and authorization with JWT
- Restaurant management (CRUD operations)
- Menu item management
- Order processing and status tracking
- Review and rating system
- Search functionality for restaurants
- PostgreSQL database with JPA/Hibernate

### Frontend Features

- Modern React application with Vite
- Responsive design with Tailwind CSS
- User authentication (login/register)
- Restaurant browsing and search
- Shopping cart functionality
- Order placement and tracking
- User profile management
- Review system

## Tech Stack

### Backend

- **Java 17**
- **Spring Boot 3.x**
- **Spring Security** with JWT
- **Spring Data JPA** with Hibernate
- **PostgreSQL** database
- **Maven** for dependency management

### Frontend

- **React 18** with Vite
- **React Router** for navigation
- **Tailwind CSS** for styling
- **Axios** for API calls
- **Lucide React** for icons

## Prerequisites

- Java 17 or higher
- Node.js 16 or higher
- PostgreSQL database
- Maven

## Setup Instructions

### 1. Database Setup

1. Install PostgreSQL if not already installed
2. Create a new database:
   ```sql
   CREATE DATABASE zomato_clone;
   ```
3. Note down your database credentials

### 2. Backend Setup

1. Navigate to the backend directory:

   ```bash
   cd backend
   ```

2. Update the database configuration in `src/main/resources/application.properties`:

   ```properties
   spring.datasource.username=YOUR_USERNAME
   spring.datasource.password=YOUR_PASSWORD
   ```

3. Build and run the application:

   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

   The backend will start on `http://localhost:8080`

4. The application will automatically:
   - Create database tables from JPA entities
   - Insert sample data from `data.sql`

### 3. Frontend Setup

1. Navigate to the frontend directory:

   ```bash
   cd frontend
   ```

2. Install dependencies:

   ```bash
   npm install
   ```

3. Start the development server:

   ```bash
   npm run dev
   ```

   The frontend will start on `http://localhost:5173`

## API Endpoints

### Authentication

- `POST /api/users/register` - User registration
- `POST /api/users/login` - User login
- `PUT /api/users/{id}` - Update user profile
- `GET /api/users/{id}` - Get user by ID

### Restaurants

- `GET /api/restaurants` - Get all restaurants
- `GET /api/restaurants/{id}` - Get restaurant by ID
- `GET /api/restaurants/search?q={query}` - Search restaurants
- `GET /api/restaurants/cuisine/{cuisine}` - Get restaurants by cuisine
- `POST /api/restaurants` - Create restaurant
- `PUT /api/restaurants/{id}` - Update restaurant
- `DELETE /api/restaurants/{id}` - Delete restaurant

### Menu Items

- `GET /api/menu-items/restaurant/{restaurantId}` - Get menu items by restaurant
- `GET /api/menu-items/{id}` - Get menu item by ID
- `POST /api/menu-items` - Create menu item
- `PUT /api/menu-items/{id}` - Update menu item
- `DELETE /api/menu-items/{id}` - Delete menu item

### Orders

- `GET /api/orders/user/{userId}` - Get orders by user
- `GET /api/orders/restaurant/{restaurantId}` - Get orders by restaurant
- `GET /api/orders/{id}` - Get order by ID
- `POST /api/orders` - Create order
- `PUT /api/orders/{id}/status` - Update order status

### Reviews

- `GET /api/reviews/restaurant/{restaurantId}` - Get reviews by restaurant
- `GET /api/reviews/{id}` - Get review by ID
- `POST /api/reviews` - Create review
- `PUT /api/reviews/{id}` - Update review
- `DELETE /api/reviews/{id}` - Delete review

## Sample Data

The application comes with sample data including:

- 3 users (John Doe, Jane Smith, Admin User)
- 5 restaurants (Pizza Palace, Burger House, Sushi Master, Taco Fiesta, Curry Corner)
- 10 menu items across different restaurants
- 6 reviews
- 3 sample orders

## Usage

1. **Browse Restaurants**: Visit the home page to see all available restaurants
2. **Search**: Use the search bar to find restaurants by name, cuisine, or location
3. **View Restaurant Details**: Click on a restaurant to see its menu and reviews
4. **Add to Cart**: Add menu items to your cart
5. **Place Order**: Complete the checkout process to place an order
6. **Track Orders**: View your order history in the profile section
7. **Write Reviews**: Leave reviews for restaurants you've ordered from

## Project Structure

```
webApp/
├── backend/
│   ├── src/main/java/com/zomato/
│   │   ├── config/
│   │   ├── controller/
│   │   ├── dto/
│   │   ├── entity/
│   │   ├── repository/
│   │   ├── service/
│   │   └── util/
│   ├── src/main/resources/
│   │   ├── application.properties
│   │   └── data.sql
│   └── pom.xml
├── frontend/
│   ├── src/
│   │   ├── components/
│   │   ├── context/
│   │   ├── pages/
│   │   ├── App.jsx
│   │   ├── main.jsx
│   │   └── index.css
│   ├── package.json
│   ├── vite.config.js
│   └── tailwind.config.js
└── README.md
```

## Troubleshooting

### Common Issues

1. **Database Connection Error**: Ensure PostgreSQL is running and credentials are correct
2. **Port Already in Use**: Change the port in `application.properties` or kill the process using the port
3. **Frontend Build Error**: Clear node_modules and reinstall dependencies
4. **CORS Error**: The backend is configured to allow CORS from the frontend development server

### Logs

- Backend logs are available in the console where you run `mvn spring-boot:run`
- Frontend logs are available in the browser console and terminal

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test thoroughly
5. Submit a pull request

## License

This project is for educational purposes only.
