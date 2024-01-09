package com.sebastianbrzustowicz.shopapi;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderRequest {
    private String userID;
    private String orderType;
    private String additionalInfo;

    // Constructors, getters, and setters (you can use Lombok if available)
}