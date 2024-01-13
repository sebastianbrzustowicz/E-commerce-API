package com.sebastianbrzustowicz.shopapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Product {
    @JsonIgnore
    private String productId;
    private String productName;
    private BigDecimal price;
    private String description;
    private String category;
    private int availableQuantity;
    @JsonIgnore
    private String imagePath;

    // AI purpose constructor
    public Product(String productName, BigDecimal price, String description, String category, int availableQuantity) {
        this.productName = productName;
        this.price = price;
        this.description = description;
        this.category = category;
        this.availableQuantity = availableQuantity;
    }

}