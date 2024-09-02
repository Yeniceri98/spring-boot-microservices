package com.example.authmicroservice.controller;

import com.example.authmicroservice.config.RestApis;
import com.example.authmicroservice.dto.request.RegisterRequestDto;
import com.example.authmicroservice.dto.request.LoginRequestDto;
import com.example.authmicroservice.model.Auth;
import com.example.authmicroservice.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "AuthController", description = "AuthController Operations")
@RestController
@RequestMapping(RestApis.AUTHSERVICE)
public class AuthController {
    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @Tag(name = "Register")
    @PostMapping(RestApis.REGISTER)
    public ResponseEntity<Auth> register(@RequestBody RegisterRequestDto registerDto) {
        if (!registerDto.getUserName().equals(registerDto.getUserName()) || !registerDto.getPassword().equals(registerDto.getPassword())) {
            throw new RuntimeException("Username or password didn't match");
        }
        return new ResponseEntity<>(service.register(registerDto), HttpStatus.OK);
    }

    @Tag(name = "Login")
    @PostMapping(RestApis.LOGIN)
    public ResponseEntity<Boolean> login(@RequestBody LoginRequestDto loginDto) {
        return new ResponseEntity<>(service.login(loginDto), HttpStatus.OK);
    }
}
