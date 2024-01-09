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
public class Review {
    private String reviewID;
    private String productID;
    private String userID;
    private String comment;
    private int rating;
    private LocalDateTime reviewTime;
}