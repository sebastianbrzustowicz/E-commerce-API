package com.sebastianbrzustowicz.shopapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Product {
    private String productId;
    private String productName;
    private BigDecimal price;
    private String description;
    private String category;
    private int availableQuantity;
    private String imagePath;

    // Constructors, getters, setters are done by Lombok

}