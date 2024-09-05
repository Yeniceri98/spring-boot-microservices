package com.example.usermicroservice.controller;

import com.example.usermicroservice.config.RestApis;
import com.example.usermicroservice.dto.UserProfileRequestDto;
import com.example.usermicroservice.dto.UserProfileResponseDto;
import com.example.usermicroservice.service.UserProfileService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserProfileController.class)
public class UserProfileControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserProfileService service;

    private UserProfileRequestDto userProfileRequestDto;
    private UserProfileResponseDto userProfileResponseDto;

    @BeforeEach
    public void init() {
        userProfileRequestDto = UserProfileRequestDto.builder()
                .authId(1L)
                .username("user")
                .email("user@gmail.com")
                .build();
        userProfileResponseDto  = UserProfileResponseDto.builder()
                .id("1")
                .authId(1L)
                .username("user")
                .email("user@gmail.com")
                .createdAt(LocalDate.now())
                .build();
    }

    @Test
    public void createUser() throws Exception {
        // Arrange
        when(service.createUser(userProfileRequestDto)).thenReturn(userProfileRequestDto);

        // Act & Assert
        mockMvc.perform(
                post(RestApis.USERPROFILE + RestApis.CREATE_USER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userProfileRequestDto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.authId").value(userProfileRequestDto.getAuthId()))
                .andExpect(jsonPath("$.username").value(userProfileRequestDto.getUsername()))
                .andExpect(jsonPath("$.email").value(userProfileRequestDto.getEmail())
        );

        // Verify
        verify(service, times(1)).createUser(userProfileRequestDto);
    }

    @Test
    public void getAllUsers() throws Exception {
        when(service.getAllUsers()).thenReturn(List.of(userProfileResponseDto));

        mockMvc.perform(
                get(RestApis.USERPROFILE + RestApis.GET_ALL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(userProfileResponseDto.getId()))
                .andExpect(jsonPath("$[0].authId").value(userProfileResponseDto.getAuthId()))
                .andExpect(jsonPath("$[0].username").value(userProfileResponseDto.getUsername()))
                .andExpect(jsonPath("$[0].email").value(userProfileResponseDto.getEmail())
        );
    }

    @Test
    public void deleteUser() throws Exception {
        mockMvc.perform(
                delete("/dev/v1/user-profile/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("User is deleted with id 1")
        );
    }
}

