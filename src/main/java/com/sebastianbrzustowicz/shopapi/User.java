package com.sebastianbrzustowicz.shopapi;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {

    private String userID;
    private String name;
    private String email;
    private String password;
    private int phoneNum;
    private String role;
    private LocalDateTime accCreated;

    // Constructors, getters, setters are done by Lombok
}