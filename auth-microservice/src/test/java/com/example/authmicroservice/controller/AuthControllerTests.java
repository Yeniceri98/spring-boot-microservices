package com.example.authmicroservice.controller;

import com.example.authmicroservice.config.RestApis;
import com.example.authmicroservice.dto.AuthResponseDto;
import com.example.authmicroservice.dto.LoginRequestDto;
import com.example.authmicroservice.dto.RegisterRequestDto;
import com.example.authmicroservice.exception.PasswordsMismatchException;
import com.example.authmicroservice.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
public class AuthControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    private AuthResponseDto authResponseDto;
    private RegisterRequestDto registerRequestDto;
    private LoginRequestDto loginRequestDto;

    @BeforeEach
    public void init() {
        authResponseDto = AuthResponseDto.builder()
                .id(1L)
                .username("user")
                .email("user@gmail.com")
                .createdAt(LocalDate.now())
                .build();
        registerRequestDto = RegisterRequestDto.builder()
                .username("user")
                .password("123")
                .repassword("123")
                .email("user@gmail.com")
                .build();
        loginRequestDto = LoginRequestDto.builder()
                .username("user")
                .password("123")
                .build();
    }

    @Test
    public void register_Success() throws Exception, PasswordsMismatchException {
        // Arrange
        when(authService.register(registerRequestDto)).thenReturn(authResponseDto);

        // Act & Assert
        mockMvc.perform(
                post(RestApis.AUTHSERVICE + RestApis.REGISTER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(registerRequestDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(authResponseDto.getId()))
                .andExpect(jsonPath("$.username").value(authResponseDto.getUsername()))
                .andExpect(jsonPath("$.email").value(authResponseDto.getEmail()))
                .andExpect(jsonPath("$.createdAt").value(authResponseDto.getCreatedAt().toString())
        );

        // Verify
        verify(authService, times(1)).register(registerRequestDto);
    }

    @Test
    public void register_Fail_PasswordsDoNotMatch() throws Exception, PasswordsMismatchException {
        when(authService.register(registerRequestDto)).thenThrow(new PasswordsMismatchException("Passwords do not match!"));

        mockMvc.perform(post(RestApis.AUTHSERVICE + RestApis.REGISTER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(registerRequestDto)))
                .andExpect(status().isBadRequest())                         // GlobalExceptionHandler > HttpStatus.BAD_REQUEST
                .andExpect(content().string("Passwords do not match!"));    // Message must be exact same

        verify(authService, times(1)).register(registerRequestDto);
    }

    @Test
    public void login() throws Exception {
        when(authService.login(loginRequestDto)).thenReturn(true);

        mockMvc.perform(post(RestApis.AUTHSERVICE + RestApis.LOGIN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(loginRequestDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("true"));
    }

}
