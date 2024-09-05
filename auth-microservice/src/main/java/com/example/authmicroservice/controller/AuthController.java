package com.example.authmicroservice.controller;

import com.example.authmicroservice.config.RestApis;
import com.example.authmicroservice.dto.AuthResponseDto;
import com.example.authmicroservice.dto.LoginRequestDto;
import com.example.authmicroservice.dto.RegisterRequestDto;
import com.example.authmicroservice.exception.PasswordsMismatchException;
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
    public ResponseEntity<AuthResponseDto> register(@RequestBody RegisterRequestDto registerRequestDto) throws PasswordsMismatchException {
        return new ResponseEntity<>(service.register(registerRequestDto), HttpStatus.OK);
    }

    @Tag(name = "Login")
    @PostMapping(RestApis.LOGIN)
    public ResponseEntity<Boolean> login(@RequestBody LoginRequestDto loginRequestDto) {
        return new ResponseEntity<>(service.login(loginRequestDto), HttpStatus.OK);
    }
}
