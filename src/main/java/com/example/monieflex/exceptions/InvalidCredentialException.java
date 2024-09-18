package com.example.monieflex.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class InvalidCredentialException extends RuntimeException {
    public InvalidCredentialException(String message) {
        super(message);
        this.status = HttpStatus.BAD_REQUEST;
    }
    private final HttpStatus status;
}
