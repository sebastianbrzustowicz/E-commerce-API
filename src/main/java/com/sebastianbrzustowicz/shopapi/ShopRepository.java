package com.sebastianbrzustowicz.shopapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ShopRepository {

    @Autowired
    static JdbcTemplate jdbcTemplate;

    public static String getAll(String email, String password) {
        String sql = "SELECT userId FROM users WHERE email = ? AND password = ?";
        return jdbcTemplate.queryForObject(sql, String.class, email, password);
    }

    public static List<Product> getAllProducts() {
        String sql = "SELECT * FROM shopAPI.products";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Product.class));
    }


}