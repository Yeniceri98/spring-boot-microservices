package com.example.usermicroservice.service;

import com.example.usermicroservice.document.UserProfile;
import com.example.usermicroservice.dto.CreateUserRequestDto;
import com.example.usermicroservice.repository.UserProfileRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserProfileService {
    private final UserProfileRepository repository;

    public UserProfileService(UserProfileRepository repository) {
        this.repository = repository;
    }

    public void createUser(CreateUserRequestDto createUserDto) {
        repository.save(
                UserProfile.builder()
                        .authId(createUserDto.getAuthId())
                        .username(createUserDto.getUsername())
                        .email(createUserDto.getUsername())
                        .build()
        );
    }

    public List<UserProfile> getAllUsers() {
        return repository.findAll();
    }
}
