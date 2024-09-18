package com.example.monieflex.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class JwtParsingException extends RuntimeException {
    public JwtParsingException(String message) {
        super(message);
        this.status = HttpStatus.BAD_REQUEST;
    }

    private final HttpStatus status;
}
