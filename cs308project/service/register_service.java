package org.example.cs308project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.example.cs308project.repository.register_repository;
import org.example.cs308project.model.register_model;

@Service
public class register_service {

    @Autowired
    private register_repository registerRepository;

    public register_model registerUser(String email, String password, String role, String category) {
        register_model user = new register_model();
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(role);

        if (role.equalsIgnoreCase("worker")) {
            user.setCategory(category); // Only workers have categories
        } else {
            user.setCategory(null); // Customers don't have a category
        }

        return registerRepository.save(user);
    }

    public boolean userExists(String email) {
        return registerRepository.findByEmail(email) != null;
    }
}
