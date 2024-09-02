package com.example.authmicroservice.service;

import com.example.authmicroservice.dto.RegisterRequestDto;
import com.example.authmicroservice.dto.LoginRequestDto;
import com.example.authmicroservice.model.Auth;
import com.example.authmicroservice.repository.AuthRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final AuthRepository repository;

    public AuthService(AuthRepository repository) {
        this.repository = repository;
    }

    public Auth register(RegisterRequestDto registerDto) {
        return repository.save(
                Auth.builder()
                        .username(registerDto.getUserName())
                        .password(registerDto.getPassword())
                        .email(registerDto.getEmail())
                        .build()
        );
    }

    public Boolean login(LoginRequestDto loginDto) {
        return repository.existsByUsernameAndPassword(loginDto.getUsername(), loginDto.getPassword());
    }
}
