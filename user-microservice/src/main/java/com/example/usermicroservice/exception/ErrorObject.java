package com.example.usermicroservice.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ErrorObject {
    private String message;
    private String details;
    private LocalDateTime timeStamp;
}
