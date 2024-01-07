package com.sebastianbrzustowicz.shopapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ShopRepository {

    @Autowired
    static JdbcTemplate jdbcTemplate;

    public static String getAll(String email, String password) {
        String sql = "SELECT userId FROM users WHERE email = ? AND password = ?";
        return jdbcTemplate.queryForObject(sql, String.class, email, password);
    }

}