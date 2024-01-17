package com.sebastianbrzustowicz.shopapi.repository;

import com.sebastianbrzustowicz.shopapi.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public class ShopRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ShopRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public String getDBname() {
        String sql = "SELECT DATABASE();";
        return jdbcTemplate.queryForObject(sql, String.class);
    }

    public List<Product> getAllProducts() {
        String sql = "SELECT * FROM shopAPI.products";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Product.class));
    }

    public Product getProductById(String productId) {
        String sql = "SELECT * FROM shopAPI.products WHERE productId = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Product.class), productId);
    }

    public String registerUser(User user) {
        String sql = "INSERT IGNORE INTO shopAPI.users (userID, name, email, password, phoneNum, role, accCreated) " +
                "VALUES (UUID(), ?, ?, ?, ?, ?, CONVERT_TZ(NOW(), 'UTC', 'Europe/Warsaw'))";

        int isRegistered = jdbcTemplate.update(sql,
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getPhoneNum(),
                user.getRole());

        String message = (isRegistered == 1) ? "Account registered" : "Cannot register your account, propably email taken";
        return message;
    }

    public String authenticateUser(Credentials credentials) {
        String sql = "SELECT userID FROM shopAPI.users WHERE email = ? AND password = ?";
        try {
            return jdbcTemplate.queryForObject(sql, String.class, credentials.getEmail(), credentials.getPassword());
        } catch (EmptyResultDataAccessException e) {
            // Handle authentication failure (return a specific message or throw an exception)
            return "Authentication failed";
        }
    }

    public List<Cart> getShoppingCart(String userID) {
        String sql = "SELECT * FROM shopAPI.cart WHERE userID = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Cart.class), userID);
    }

    public String addToCart(Cart cart) {
        // Check if the product is already in the cart for the user
        String checkSql = "SELECT * FROM shopAPI.cart WHERE userID = ? AND productID = ?";
        List<Cart> existingCartItems = jdbcTemplate.query(checkSql, new BeanPropertyRowMapper<>(Cart.class),
                cart.getUserID(), cart.getProductID());

        if (!existingCartItems.isEmpty()) {
            // If the product is already in the cart, update the quantity
            String updateSql = "UPDATE shopAPI.cart SET quantity = quantity + ? WHERE userID = ? AND productID = ?";
            jdbcTemplate.update(updateSql, cart.getQuantity(), cart.getUserID(), cart.getProductID());
        } else {
            // If the product is not in the cart, insert a new cart item
            String insertSql = "INSERT INTO shopAPI.cart (cartID, userID, productID, quantity) VALUES (?, ?, ?, ?)";
            jdbcTemplate.update(insertSql, UUID.randomUUID().toString(), cart.getUserID(),
                    cart.getProductID(), cart.getQuantity());
        }

        return UUID.randomUUID().toString(); // Return a new cartID
    }

    public Order addCartToOrders(OrderRequest request) {
        // Step 0: Check if there are any rows in shopAPI.cart for the given userID
        String userID = request.getUserID();
        String orderType = request.getOrderType();
        String additionalInfo = request.getAdditionalInfo();

        String checkCartSql = "SELECT COUNT(*) FROM shopAPI.cart WHERE userID = ?";
        int cartRowCount = jdbcTemplate.queryForObject(checkCartSql, Integer.class, userID);

        if (cartRowCount == 0) {
            // Return null or throw an exception indicating no items in the cart
            return null;
        }

        // Step 1: Register order into shopAPI.orders table
        String orderID = UUID.randomUUID().toString();
        LocalDateTime registrationTime = LocalDateTime.now();

        String insertOrderSql = "INSERT INTO shopAPI.orders (userID, orderID, orderType, additionalInfo, registrationTime) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(insertOrderSql, userID, orderID, orderType, additionalInfo, registrationTime);

        // Step 2: Register each product ordered into shopAPI.orderedProducts table
        String insertOrderedProductSql = "INSERT INTO shopAPI.orderedProducts (orderID, productID, productName, price, quantity) VALUES (?, ?, ?, ?, ?)";
        String clearCartSql = "DELETE FROM shopAPI.cart WHERE userID = ?";

        List<Cart> cartList = jdbcTemplate.query("SELECT * FROM shopAPI.cart WHERE userID = ?", new BeanPropertyRowMapper<>(Cart.class), userID);

        for (Cart cart : cartList) {
            String productID = cart.getProductID();

            // Fetch product details (name and price) from shopAPI.products
            String getProductDetailsSql = "SELECT productName, price FROM shopAPI.products WHERE productID = ?";
            Product product = jdbcTemplate.queryForObject(getProductDetailsSql, new BeanPropertyRowMapper<>(Product.class), productID);

            String productName = product.getProductName();
            BigDecimal price = product.getPrice();
            int quantity = cart.getQuantity();

            jdbcTemplate.update(insertOrderedProductSql, orderID, productID, productName, price, quantity);
        }

        // Step 3: Clear shopAPI.cart for the given userID
        jdbcTemplate.update(clearCartSql, userID);

        // Step 4: Return the created Order
        return new Order(userID, orderID, orderType, additionalInfo, registrationTime);
    }

    public List<Order> viewPastOrders(String userID) {
        String sql = "SELECT * FROM shopAPI.orders WHERE userID = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Order.class), userID);
    }

    public List<OrderedProduct> getOrderDetails(String orderID) {
        String sql = "SELECT * FROM shopAPI.orderedProducts WHERE orderID = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(OrderedProduct.class), orderID);
    }

    public List<Category> getCategories() {
        String sql = "SELECT DISTINCT category FROM shopAPI.products";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Category.class));
    }

    public User getUserProfileInfo(String userID) {
        String sql = "SELECT * FROM shopAPI.users WHERE userID = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), userID);
    }

    public String updateUserProfile(String userID, User updatedUser) {
        String updateSql = "UPDATE shopAPI.users SET name = ?, email = ?, password = ?, phoneNum = ?, role = ? WHERE userID = ?";

        int rowsAffected = jdbcTemplate.update(updateSql,
                updatedUser.getName(),
                updatedUser.getEmail(),
                updatedUser.getPassword(),
                updatedUser.getPhoneNum(),
                updatedUser.getRole(),
                userID);

        return (rowsAffected > 0) ? "Profile updated successfully." : "Failed to update profile.";
    }

    public List<Review> getProductReviews(String productID) {
        String sql = "SELECT * FROM shopAPI.reviews WHERE productID = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Review.class), productID);
    }

    public String addReviewForProduct(String productID, Review review) {
        // Check if the provided productID exists in the products table
        String checkProductSql = "SELECT COUNT(*) FROM shopAPI.products WHERE productID = ?";
        int productCount = jdbcTemplate.queryForObject(checkProductSql, Integer.class, productID);

        if (productCount == 0) {
            // The provided productID does not exist, return an error message
            return "Failed to add review. Product with the specified productID does not exist.";
        }

        // Check if the provided userID exists in the users table
        String checkUserSql = "SELECT COUNT(*) FROM shopAPI.users WHERE userID = ?";
        int userCount = jdbcTemplate.queryForObject(checkUserSql, Integer.class, review.getUserID());

        if (userCount == 0) {
            // The provided userID does not exist, return an error message
            return "Failed to add review. User with the specified userID does not exist.";
        }

        // If both productID and userID exist, proceed with the review insertion
        String insertSql = "INSERT INTO shopAPI.reviews (reviewID, productID, userID, comment, rating, reviewTime) " +
                "VALUES (UUID(), ?, ?, ?, ?, CONVERT_TZ(NOW(), 'UTC', 'Europe/Warsaw'))";

        try {
            int rowsAffected = jdbcTemplate.update(insertSql,
                    productID,
                    review.getUserID(),
                    review.getComment(),
                    review.getRating());

            return (rowsAffected > 0) ? "Review added successfully." : "Failed to add review.";
        } catch (DataIntegrityViolationException e) {
            // Handle the case where a foreign key constraint is violated (non-existent userID or productID)
            return "Failed to add review. User or product with the specified ID does not exist.";
        } catch (DataAccessException e) {
            // Handle other data access exceptions
            return "Failed to add review. An unexpected error occurred.";
        }
    }

    public List<Product> searchProducts(String searchQuery) {
        String sql = "SELECT * FROM shopAPI.products WHERE productName LIKE ?";
        String searchParam = "%" + searchQuery + "%";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Product.class), searchParam);
    }

}