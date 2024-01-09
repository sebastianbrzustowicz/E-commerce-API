package com.sebastianbrzustowicz.shopapi.controller;

import com.sebastianbrzustowicz.shopapi.model.*;
import com.sebastianbrzustowicz.shopapi.repository.ShopRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ShopController {

    private final ShopRepository shopRepository;

    public ShopController(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    @GetMapping("/test")
    public String getAll() {
        String email = "email";
        String password = "password";
        //return ShopRepository.test(email, password);
        return "it works";
    }

    @GetMapping("/products")
    public List<Product> retrieveProducts() {
        return shopRepository.getAllProducts();
    }

    @GetMapping("/products/{productId}")
    public Product retrieveProduct(@PathVariable String productId) {
        return shopRepository.getProductById(productId);
    }

    @PostMapping("/register")
    public String registerUser(@RequestBody User user) {
        return shopRepository.registerUser(user);
    }

    @PostMapping("/login")
    public String loginUser(@RequestBody Credentials credentials) {
        return shopRepository.authenticateUser(credentials);
    }

    @GetMapping("/cart")
    public List<Cart> viewShoppingCart(@RequestParam String userID) {
        return shopRepository.getShoppingCart(userID);
    }

    @PostMapping("/cart/add")
    public String addToCart(@RequestBody Cart cart) {
        String cartID = shopRepository.addToCart(cart);
        return "Product added to cart. CartID: " + cartID;
    }

    @PostMapping("/orders/add")
    public Order addCartToOrders(@RequestBody OrderRequest request) {
        return shopRepository.addCartToOrders(request);
    }

    @GetMapping("/orders/past")
    public List<Order> viewPastOrders(@RequestParam String userID) {
        return shopRepository.viewPastOrders(userID);
    }

    @GetMapping("/orders/details")
    public List<OrderedProduct> getOrderDetails(@RequestParam String orderID) {
        return shopRepository.getOrderDetails(orderID);
    }

    @GetMapping("/categories")
    public List<Category> getCategories() {
        return shopRepository.getCategories();
    }

    @GetMapping("/profile/info")
    public User getUserProfileInfo(@RequestParam String userID) {
        return shopRepository.getUserProfileInfo(userID);
    }

    @PostMapping("/profile/update")
    public String updateUserProfile(@RequestParam String userID, @RequestBody User updatedUser) {
        return shopRepository.updateUserProfile(userID, updatedUser);
    }

    @GetMapping("/products/{id}/reviews")
    public List<Review> getProductReviews(@PathVariable String id) {
        return shopRepository.getProductReviews(id);
    }

    @PostMapping("/products/{productID}/reviews/add")
    public String addReviewForProduct(@PathVariable String productID, @RequestBody Review review) {
        return shopRepository.addReviewForProduct(productID, review);
    }

    @GetMapping("/search")
    public List<Product> searchProducts(@RequestParam String searchQuery) {
        return shopRepository.searchProducts(searchQuery);
    }

}