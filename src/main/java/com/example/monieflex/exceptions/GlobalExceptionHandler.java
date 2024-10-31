package com.example.monieflex.exceptions;

import com.example.monieflex.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
public class GlobalExceptionHandler {
        @ExceptionHandler(InvalidCredentialException.class)
        @ResponseStatus(BAD_REQUEST)
        public ResponseEntity<ApiResponse<String>> handleInvalidCredentialException(InvalidCredentialException e) {
            return new ResponseEntity<>(new ApiResponse<>(BAD_REQUEST, e.getLocalizedMessage()), BAD_REQUEST );
        }

}
