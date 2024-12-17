package com.example.monieflex.utils;

import com.example.monieflex.dto.request.UserSignupRequest;
import com.example.monieflex.dto.response.LoginResponse;
import com.example.monieflex.dto.response.SignupResponse;
import com.example.monieflex.entities.User;
import com.example.monieflex.enums.Roles;
import com.example.monieflex.security.securityImpl.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Service
public class DtoMapper {
    private final PasswordEncoder passwordEncoder;

    public User createNewUser(UserSignupRequest userSignupRequest){
        return User.builder()
                .firstName(userSignupRequest.getFirstName())
                .lastName(userSignupRequest.getLastName())
                .email(userSignupRequest.getEmail())
                .bvn(userSignupRequest.getBvn())
                .phoneNumber(userSignupRequest.getPhoneNumber())
                .roles(Roles.USER)
                .password(passwordEncoder.encode(userSignupRequest.getPassword()))
                .confirmPassword(passwordEncoder.encode(userSignupRequest.getConfirmPassword()))
                .build();
    }

    public SignupResponse createSignupResponse(User savedUser){
        return SignupResponse.builder()
                .firstName(savedUser.getFirstName())
                .lastName(savedUser.getLastName())
                .email(savedUser.getEmail())
                .phoneNumber(savedUser.getPhoneNumber())
                .build();
    }

    public LoginResponse createLoginResponse(UserDetailsImpl savedUser, String token){
        return LoginResponse.builder()
                .id(savedUser.getId())
                .fullName(savedUser.getFullName())
                .email(savedUser.getEmail())
                .jwtToken(token)
                .build();
    }
}
