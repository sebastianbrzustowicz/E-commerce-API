package com.sebastianbrzustowicz.shopapi;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderedProduct {

    private String orderID;
    private String productID;
    private String productName;
    private BigDecimal price;
    private int quantity;
}