package com.example.authmicroservice.service;

import com.example.authmicroservice.dto.AuthResponseDto;
import com.example.authmicroservice.dto.LoginRequestDto;
import com.example.authmicroservice.dto.RegisterRequestDto;
import com.example.authmicroservice.dto.UserProfileRequestDto;
import com.example.authmicroservice.exception.PasswordsMismatchException;
import com.example.authmicroservice.feign.UserProfileManager;
import com.example.authmicroservice.model.Auth;
import com.example.authmicroservice.repository.AuthRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AuthService {
    private final AuthRepository repository;
    private final UserProfileManager userProfileManager;

    public AuthService(AuthRepository repository, UserProfileManager userProfileManager) {
        this.repository = repository;
        this.userProfileManager = userProfileManager;
    }

    // Before Feign Client Added
    public AuthResponseDto registerBeforeFeignClient(RegisterRequestDto registerRequestDto) {
        if (!registerRequestDto.getPassword().equals(registerRequestDto.getRepassword())) {
            throw new RuntimeException("Passwords don't match!");
        }

        Auth auth = mapToEntity(registerRequestDto);

        Auth savedAuth = repository.save(auth);

        return mapToDto(savedAuth);
    }

    // After Feign Client Added
    public AuthResponseDto register(RegisterRequestDto registerRequestDto) throws PasswordsMismatchException {
        if (!registerRequestDto.getPassword().equals(registerRequestDto.getRepassword())) {
            throw new PasswordsMismatchException("Passwords don't match!");
        }

        Auth auth = mapToEntity(registerRequestDto);
        Auth savedAuth = repository.save(auth);

        // Sending User Registration to user-service with Feign Client
        /*
            auth-microservice'de "register" isteği atıldığında user-microservice'in "createUser" metodu da çalışmış olup user oluşturulmuş olacak
            Böylece Feign Client kullanılarak servisler arasında iletişim kurulmuş oldu
        */
        UserProfileRequestDto userProfileRequestDto = UserProfileRequestDto.builder()
                .authId(savedAuth.getId())
                .username(savedAuth.getUsername())
                .email(savedAuth.getEmail())
                .build();

        userProfileManager.createUser(userProfileRequestDto);

        return mapToDto(savedAuth);
    }

    public Boolean login(LoginRequestDto loginRequestDto) {
        Auth auth = repository.findByUsername(loginRequestDto.getUsername());

        if (auth == null) {
            return false;
        }

        return repository.existsByUsernameAndPassword(loginRequestDto.getUsername(), loginRequestDto.getPassword());
    }

    private Auth mapToEntity(RegisterRequestDto registerRequestDto) {
        return Auth.builder()
                .username(registerRequestDto.getUsername())
                .password(registerRequestDto.getPassword())
                .email(registerRequestDto.getEmail())
                .createdAt(LocalDate.now())
                .build();
    }

    private AuthResponseDto mapToDto(Auth auth) {
        return AuthResponseDto.builder()
                .id(auth.getId())
                .username(auth.getUsername())
                .email(auth.getEmail())
                .createdAt(auth.getCreatedAt())
                .build();
    }
}
