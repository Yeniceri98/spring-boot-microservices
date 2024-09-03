package com.example.authmicroservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthResponseDto {
    private Long id;
    private String username;
    private String email;
    private LocalDate createdAt;
}
