package org.example.cs308project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.example.cs308project.service.register_service;
import org.example.cs308project.service.login_service;
import org.example.cs308project.model.register_model;
import org.example.cs308project.model.login_model;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class user_controller {

    @Autowired
    private register_service registerService;

    @Autowired
    private login_service loginService;

    // Register Endpoint (Handles both Customers & Workers)
    @PostMapping("/register")
    public Map<String, String> register(@RequestBody register_model user) {
        Map<String, String> response = new HashMap<>();

        if (registerService.userExists(user.getEmail())) {
            response.put("message", "User already exists!");
        } else {
            // Ensure role is either "customer" or "worker"
            if (!user.getRole().equalsIgnoreCase("customer") && !user.getRole().equalsIgnoreCase("worker")) {
                response.put("message", "Invalid role! Choose 'customer' or 'worker'.");
                return response;
            }

            // Ensure workers have a valid category
            if (user.getRole().equalsIgnoreCase("worker") &&
                    (user.getCategory() == null ||
                            (!user.getCategory().equalsIgnoreCase("admin") &&
                                    !user.getCategory().equalsIgnoreCase("product_manager") &&
                                    !user.getCategory().equalsIgnoreCase("sales_manager")))) {
                response.put("message", "Invalid category! Choose 'admin', 'product_manager', or 'sales_manager'.");
                return response;
            }

            registerService.registerUser(user.getEmail(), user.getPassword(), user.getRole(), user.getCategory());
            response.put("message", "User registered successfully!");
        }
        return response;
    }

    // Login Endpoint
    @PostMapping("/login")
    public Map<String, String> login(@RequestBody login_model loginUser) {
        Map<String, String> response = new HashMap<>();

        register_model user = loginService.findByEmail(loginUser.getEmail());

        if (user == null) {
            response.put("message", "User not found");
        } else if (!user.getPassword().equals(loginUser.getPassword())) {
            response.put("message", "Invalid password");
        } else {
            response.put("message", "Login successful");
            response.put("role", user.getRole());
            if (user.getRole().equalsIgnoreCase("worker")) {
                response.put("category", user.getCategory());
            }
        }
        return response;
    }
}
