package com.example.authmicroservice.repository;

import com.example.authmicroservice.model.Auth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepository extends JpaRepository<Auth, Long> {
    Boolean existsByUsernameAndPassword(String username, String password);

    Auth findByUsername(String username);
}
