package com.example.usermicroservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserProfileResponseDto {
    private String id;
    private Long authId;
    private String username;
    private String email;
    private LocalDate createdAt;
}
