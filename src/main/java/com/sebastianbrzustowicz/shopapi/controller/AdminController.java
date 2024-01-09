package com.sebastianbrzustowicz.shopapi.controller;

import com.sebastianbrzustowicz.shopapi.repository.AdminRepository;
import com.sebastianbrzustowicz.shopapi.model.Product;
import com.sebastianbrzustowicz.shopapi.model.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminRepository adminRepository;

    public AdminController(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @GetMapping("/test")
    public String getAll() {
        String email = "email";
        String password = "password";
        //return ShopRepository.test(email, password);
        return "admin endpoint works";
    }

    @PostMapping("/products/add")
    public String addProduct(@RequestBody Product product) {
        return adminRepository.addProduct(product);
    }

    @DeleteMapping("/products/delete/{productID}")
    public String deleteProduct(@PathVariable String productID) {
        return adminRepository.deleteProduct(productID);
    }

    @PatchMapping("/products/update/{productId}")
    public String updateProduct(@PathVariable String productId, @RequestBody Product updatedProduct) {
        int rowsAffected = adminRepository.updateProduct(productId, updatedProduct);
        return (rowsAffected > 0) ? "Product updated." : "Failed to update product.";
    }

    @PostMapping("/orders/fulfill/{orderID}")
    public String fulfillOrder(@PathVariable String orderID) {
        int rowsAffected = adminRepository.fulfillOrder(orderID);
        return (rowsAffected > 0) ? "Order fulfilled." : "Failed to fulfill order.";
    }

    @PostMapping("/users/update/{userId}")
    public String updateUser(@PathVariable String userId, @RequestBody User updatedUser) {
        int rowsAffected = adminRepository.updateUser(userId, updatedUser);
        return (rowsAffected > 0) ? "User updated." : "Failed to update user.";
    }

    @GetMapping("/reports/sales")
    public List<Product> retrieveSalesReport() {
        return adminRepository.getSalesReport();
    }

    @GetMapping("/reports/inventory")
    public List<Product> retrieveInventoryReport() {
        return adminRepository.getInventoryReport();
    }

}