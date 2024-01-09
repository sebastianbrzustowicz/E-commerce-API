package com.sebastianbrzustowicz.shopapi;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ShopController {

    @GetMapping("/test")
    public String getAll() {
        String email = "email";
        String password = "password";
        //return ShopRepository.getAll(email, password);
        return "it works";
    }

    @GetMapping("/products")
    public List<Product> retrieveProducts() {
        return ShopRepository.getAllProducts();
    }

}