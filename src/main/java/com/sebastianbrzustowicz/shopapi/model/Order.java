package com.sebastianbrzustowicz.shopapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Order {

    private String userID;
    private String orderID;
    private String orderType;
    private String additionalInfo;
    private LocalDateTime registrationTime;

    // Constructors, getters, setters are done by Lombok
}