package com.example.monieflex.controller;

import com.example.monieflex.dto.request.LoginRequest;
import com.example.monieflex.dto.request.OtpVerificationRequest;
import com.example.monieflex.dto.request.UserSignupRequest;
import com.example.monieflex.dto.response.ApiResponse;
import com.example.monieflex.dto.response.LoginResponse;
import com.example.monieflex.dto.response.SignupResponse;
import com.example.monieflex.services.EmailService;
import com.example.monieflex.services.UserService;
import com.example.monieflex.utils.OtpGenerator;
import jakarta.mail.MessagingException;
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
    private final EmailService emailService;


    @PostMapping("/sign-up")
    ResponseEntity<ApiResponse<SignupResponse>> signUp(@Valid @RequestBody UserSignupRequest request) {
        ApiResponse<SignupResponse> response = userService.createUser(request);
        String otp = OtpGenerator.generateOtp();
        userService.saveOtpForUser(request.getEmail(),otp);

        try{
            emailService.sendOtpEmail(request.getEmail(), otp);
        }catch(MessagingException ex){
            log.error("Failed to send OTP email: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(String.valueOf(false), "Failed to send OTP email. "));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody LoginRequest loginRequest) {
        ApiResponse<LoginResponse> apiResponse = userService.login(loginRequest);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(@RequestBody OtpVerificationRequest otpVerificationRequest){
        String verificationRequest = userService.verifyOtp(otpVerificationRequest);

        if ("OTP verification successful!".equals(verificationRequest)){
            return ResponseEntity.status(HttpStatus.OK).body(verificationRequest);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(verificationRequest);
        }
    }


  }
