package com.example.usermicroservice.service;

import com.example.usermicroservice.document.UserProfile;
import com.example.usermicroservice.dto.UserProfileRequestDto;
import com.example.usermicroservice.dto.UserProfileResponseDto;
import com.example.usermicroservice.repository.UserProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cache.CacheManager;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserProfileServiceTests {

    @Mock
    private UserProfileRepository repository;

    @InjectMocks
    private UserProfileService service;

    private UserProfile userProfile;
    private UserProfileRequestDto userProfileRequestDto;

    @BeforeEach
    public void init() {
        userProfile = UserProfile.builder()
                .id("1")
                .authId(1L)
                .username("user")
                .firstname("firstname")
                .lastname("lastname")
                .email("user@gmail.com")
                .createdAt(LocalDate.now())
                .build();
        userProfileRequestDto = UserProfileRequestDto.builder()
                .authId(1L)
                .username("user")
                .email("user@gmail.com")
                .build();
    }

    @Test
    public void createUser() {
        // Arrange
        when(repository.save(any(UserProfile.class))).thenReturn(userProfile);

        // Act
        UserProfileRequestDto result = service.createUser(userProfileRequestDto);

        // Assert
        assertThat(result).isNotNull();    // AssertJ
        assertNotNull(result);             // JUnit (Jupiter)
        assertEquals(userProfileRequestDto.getAuthId(), result.getAuthId());
        assertEquals(userProfileRequestDto.getUsername(), result.getUsername());
        assertEquals(userProfileRequestDto.getEmail(), result.getEmail());
    }

    @Test
    public void getAllUsers() {
        when(repository.findAll()).thenReturn(List.of(userProfile));

        List<UserProfileResponseDto> result = service.getAllUsers();

        assertThat(result).isNotNull();
        assertEquals(1, result.size());
    }

    @Test
    public void deleteUser() {
        when(repository.findById("1")).thenReturn(Optional.ofNullable(userProfile));

        service.deleteUser("1");

        verify(repository, times(1)).delete(userProfile);
    }

    @Test
    public void upperName() {
        String name = "john";
        String expected = "JOHN";

        String result = service.upperName(name);

        assertEquals(expected, result);
    }
}
