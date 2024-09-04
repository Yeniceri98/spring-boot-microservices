package com.example.usermicroservice.service;

import com.example.usermicroservice.document.UserProfile;
import com.example.usermicroservice.dto.UserProfileRequestDto;
import com.example.usermicroservice.dto.UserProfileResponseDto;
import com.example.usermicroservice.exception.UserNotFoundException;
import com.example.usermicroservice.repository.UserProfileRepository;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserProfileService {
    private final UserProfileRepository repository;
    private final CacheManager cacheManager;

    public UserProfileService(UserProfileRepository repository, CacheManager cacheManager) {
        this.repository = repository;
        this.cacheManager = cacheManager;
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

    @Cacheable("upper-case")
    public String upperName(String name) {
        String result = name.toUpperCase();

        /*
            Örneğin isteği atarken "ahmet" verilirse sürekli "AHMET" cevabı döner
            Aynı response'un döndüğü durumda her seferinde 3 sn beklemek anlamsızdır
            Redis eklenip program başlatıldığında ilk istek 3 sn sürer ve metodun girdi çıktıları cachelenir
            Sonraki istekte bilgi cache'den gelir ve 3 sn beklemeden hızlı bir şekilde response gelir
        */
        try {
            Thread.sleep(3000L);
        } catch (Exception ex) {
            ex.getMessage();
        }

        return result;
    }

    public void clearCache() {
        cacheManager.getCache("upper-case").clear();
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
