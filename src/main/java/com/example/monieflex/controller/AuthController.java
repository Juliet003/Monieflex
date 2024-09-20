package com.example.monieflex.controller;

import com.example.monieflex.dto.request.LoginRequest;
import com.example.monieflex.dto.request.UserSignupRequest;
import com.example.monieflex.dto.response.ApiResponse;
import com.example.monieflex.dto.response.LoginResponse;
import com.example.monieflex.dto.response.SignupResponse;
import com.example.monieflex.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth/user")
public class AuthController {
    private final UserService userService;


    @PostMapping("/sign-up")
    ResponseEntity<ApiResponse<SignupResponse>> signUp(@Valid @RequestBody UserSignupRequest request) {
        ApiResponse<SignupResponse> response = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody LoginRequest loginRequest) {
        ApiResponse<LoginResponse> apiResponse = userService.login(loginRequest);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

  }
