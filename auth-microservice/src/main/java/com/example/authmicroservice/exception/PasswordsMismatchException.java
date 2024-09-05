package com.example.authmicroservice.exception;

public class PasswordsMismatchException extends Throwable {
    public PasswordsMismatchException(String message) {
        super(message);
    }
}
