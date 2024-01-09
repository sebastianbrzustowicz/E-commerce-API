package com.sebastianbrzustowicz.shopapi;

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
public class AdminRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AdminRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public String addProduct(Product product) {
        String sql = "INSERT IGNORE INTO shopAPI.products " +
                "(productId, productName, price, description, category, availableQuantity, imagePath) " +
                "VALUES (UUID(), ?, ?, ?, ?, ?, ?)";

        int rowsAffected = jdbcTemplate.update(sql, product.getProductName(),
                product.getPrice(), product.getDescription(), product.getCategory(),
                product.getAvailableQuantity(), product.getImagePath());

        return (rowsAffected > 0) ? "Product added." : "Failed to add product.";
    }

    public String deleteProduct(String productID) {
        String sql = "DELETE FROM shopAPI.products WHERE productId = ?";
        int rowsAffected =  jdbcTemplate.update(sql, productID);

        return (rowsAffected > 0) ? "Product deleted." : "Failed to delete product.";
    }

    public int updateProduct(String productId, Product updatedProduct) {
        String sql = "UPDATE shopAPI.products SET " +
                "productName = ?, " +
                "price = ?, " +
                "description = ?, " +
                "category = ?, " +
                "availableQuantity = ?, " +
                "imagePath = ? " +
                "WHERE productID = ?";

        return jdbcTemplate.update(sql, updatedProduct.getProductName(), updatedProduct.getPrice(),
                updatedProduct.getDescription(), updatedProduct.getCategory(),
                updatedProduct.getAvailableQuantity(), updatedProduct.getImagePath(), productId);
    }

    public int fulfillOrder(String orderID) {
        String sql = "UPDATE shopAPI.orders SET orderType = 'Done' WHERE orderID = ?";
        return jdbcTemplate.update(sql, orderID);
    }

    public int updateUser(String userId, User updatedUser) {
        String sql = "UPDATE shopAPI.users SET " +
                "name = ?, " +
                "email = ?, " +
                "password = ?, " +
                "phoneNum = ?, " +
                "role = ?, " +
                "accCreated = ? " +
                "WHERE userID = ?";

        return jdbcTemplate.update(sql, updatedUser.getName(), updatedUser.getEmail(),
                updatedUser.getPassword(), updatedUser.getPhoneNum(),
                updatedUser.getRole(), updatedUser.getAccCreated(), userId);
    }

    public List<Product> getSalesReport() {
        String sql = "SELECT * FROM shopAPI.products WHERE LOWER(description) LIKE '%sale%'";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Product.class));
    }

    public List<Product> getInventoryReport() {
        String sql = "SELECT * FROM shopAPI.products WHERE availableQuantity > 0";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Product.class));
    }

}