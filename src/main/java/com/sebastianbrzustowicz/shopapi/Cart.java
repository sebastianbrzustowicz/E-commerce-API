package com.sebastianbrzustowicz.shopapi;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Cart {

    private String cartID;
    private String userID;
    private String productID;
    private int quantity;

    // Constructors, getters, setters are done by Lombok
}