package com.sebastianbrzustowicz.shopapi.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sebastianbrzustowicz.shopapi.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AIRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AIRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public String getDBname() {
        String sql = "SELECT DATABASE();";
        return jdbcTemplate.queryForObject(sql, String.class);
    }

    public String getGPTConfig() {
        String sql = "SELECT config FROM shopAPI.AIconfig;";
        return jdbcTemplate.queryForObject(sql, String.class);
    }

    public List<Product> getAllProducts() {
        String sql = "SELECT productName, price, description, category, availableQuantity FROM shopAPI.products WHERE availableQuantity > 0";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Product.class));
    }


    public String convertProducts(List<Product> actualProducts) {
        String productsAsString = "";
        try {
            productsAsString = convertProductListToString(actualProducts);
        } catch (
                JsonProcessingException e) {
            e.printStackTrace();
        }
        return productsAsString;
    }

    public static String convertProductListToString(List<Product> products) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(products);
    }

    public String getGPTResponse() {
        String sql = "SELECT DATABASE();";
        return jdbcTemplate.queryForObject(sql, String.class);
    }
}
