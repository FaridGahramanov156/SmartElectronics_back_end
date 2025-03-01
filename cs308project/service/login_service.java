package org.example.cs308project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.example.cs308project.repository.register_repository;
import org.example.cs308project.model.register_model;
import org.example.cs308project.model.login_model;

@Service
public class login_service {

    @Autowired
    private register_repository registerRepository;

    public register_model findByEmail(String email) {
        return registerRepository.findByEmail(email);
    }
}

