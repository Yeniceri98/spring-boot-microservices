package com.example.usermicroservice.service;

import com.example.usermicroservice.document.UserProfile;
import com.example.usermicroservice.dto.CreateUserRequestDto;
import com.example.usermicroservice.dto.UserProfileResponseDto;
import com.example.usermicroservice.repository.UserProfileRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<UserProfileResponseDto> getAllUsers() {
        return repository.findAll().stream()
                .map(user -> mapToDto(user))
                .collect(Collectors.toList());
    }

    private UserProfileResponseDto mapToDto(UserProfile userProfile) {
        UserProfileResponseDto userProfileResponseDto = new UserProfileResponseDto();
        userProfileResponseDto.setAuthId(userProfile.getId());
        userProfileResponseDto.setUsername(userProfile.getUsername());
        userProfileResponseDto.setEmail(userProfile.getEmail());
        return userProfileResponseDto;
    }

    private UserProfile mapToEntity(UserProfileResponseDto userProfileResponseDto) {
        UserProfile userProfile = new UserProfile();
        userProfile.setId(userProfileResponseDto.getAuthId());
        userProfile.setUsername(userProfileResponseDto.getUsername());
        userProfile.setEmail(userProfileResponseDto.getEmail());
        return userProfile;
    }
}
