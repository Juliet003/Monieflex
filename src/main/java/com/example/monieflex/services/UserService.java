package com.example.monieflex.services;

import com.example.monieflex.dto.request.LoginRequest;
import com.example.monieflex.dto.request.OtpVerificationRequest;
import com.example.monieflex.dto.request.UserSignupRequest;
import com.example.monieflex.dto.response.ApiResponse;
import com.example.monieflex.dto.response.LoginResponse;
import com.example.monieflex.dto.response.SignupResponse;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    ApiResponse<SignupResponse> createUser(UserSignupRequest userSignupRequest);

    ApiResponse<LoginResponse> login(LoginRequest loginRequest);

    void saveOtpForUser(String email,String otp);

    String verifyOtp(OtpVerificationRequest otpVerificationRequest);
}
