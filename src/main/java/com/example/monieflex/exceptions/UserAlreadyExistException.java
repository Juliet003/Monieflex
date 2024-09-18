package com.example.monieflex.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class UserAlreadyExistException extends RuntimeException{
    private String message;
    private HttpStatus httpStatus;

    public UserAlreadyExistException(String message) {
        super(message);
        this.message = message;
        this.httpStatus = HttpStatus.CONFLICT;
    }
}
