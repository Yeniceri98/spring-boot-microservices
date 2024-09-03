package com.example.usermicroservice.service;

import com.example.usermicroservice.document.UserProfile;
import com.example.usermicroservice.dto.UserProfileRequestDto;
import com.example.usermicroservice.dto.UserProfileResponseDto;
import com.example.usermicroservice.exception.UserNotFoundException;
import com.example.usermicroservice.repository.UserProfileRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserProfileService {
    private final UserProfileRepository repository;

    public UserProfileService(UserProfileRepository repository) {
        this.repository = repository;
    }

    public UserProfileRequestDto createUser(UserProfileRequestDto userProfileRequestDto) {
        UserProfile user = mapToEntityRequest(userProfileRequestDto);

        UserProfile savedUser = repository.save(user);

        return mapToDtoRequest(savedUser);
    }

    public List<UserProfileResponseDto> getAllUsers() {
        return repository.findAll().stream()
                .map(user -> mapToDtoResponse(user))
                .collect(Collectors.toList());
    }

    public void deleteUser(String id) {
        UserProfile user = repository.findById(id).orElseThrow(() -> new UserNotFoundException("User is not found with the id " + id));
        repository.delete(user);
    }

    private UserProfile mapToEntityRequest(UserProfileRequestDto userProfileRequestDto) {
        UserProfile userProfile = new UserProfile();
        userProfile.setAuthId(userProfileRequestDto.getAuthId());
        userProfile.setUsername(userProfileRequestDto.getUsername());
        userProfile.setEmail(userProfileRequestDto.getEmail());
        return userProfile;
    }

    private UserProfileRequestDto mapToDtoRequest(UserProfile userProfile) {
        UserProfileRequestDto userProfileRequestDto = new UserProfileRequestDto();
        userProfileRequestDto.setAuthId(userProfile.getAuthId());
        userProfileRequestDto.setUsername(userProfile.getUsername());
        userProfileRequestDto.setEmail(userProfile.getEmail());
        return userProfileRequestDto;
    }

    private UserProfile mapToEntityResponse(UserProfileResponseDto userProfileResponseDto) {
        UserProfile userProfile = new UserProfile();
        userProfile.setId(userProfileResponseDto.getId());
        userProfile.setAuthId((userProfileResponseDto.getAuthId()));
        userProfile.setUsername(userProfileResponseDto.getUsername());
        userProfile.setEmail(userProfileResponseDto.getEmail());
        userProfile.setCreatedAt(LocalDate.now());
        return userProfile;
    }

    private UserProfileResponseDto mapToDtoResponse(UserProfile userProfile) {
        UserProfileResponseDto userProfileResponseDto = new UserProfileResponseDto();
        userProfileResponseDto.setId(userProfile.getId());
        userProfileResponseDto.setAuthId(userProfile.getAuthId());
        userProfileResponseDto.setUsername(userProfile.getUsername());
        userProfileResponseDto.setEmail(userProfile.getEmail());
        userProfileResponseDto.setCreatedAt(LocalDate.now());
        return userProfileResponseDto;
    }
}
