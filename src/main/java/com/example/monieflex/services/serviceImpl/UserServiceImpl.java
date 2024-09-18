package com.example.monieflex.services.serviceImpl;

import com.example.monieflex.dto.request.LoginRequest;
import com.example.monieflex.dto.request.UserSignupRequest;
import com.example.monieflex.dto.response.ApiResponse;
import com.example.monieflex.dto.response.LoginResponse;
import com.example.monieflex.dto.response.SignupResponse;
import com.example.monieflex.entities.User;
import com.example.monieflex.enums.Roles;
import com.example.monieflex.exceptions.InvalidCredentialException;
import com.example.monieflex.exceptions.UserAlreadyExistException;
import com.example.monieflex.repositories.UserRepository;
import com.example.monieflex.security.securityImpl.JwtService;
import com.example.monieflex.security.securityImpl.UserDetailsImpl;
import com.example.monieflex.services.UserService;
import com.example.monieflex.utils.DtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

import static org.springframework.http.HttpStatus.NETWORK_AUTHENTICATION_REQUIRED;
import static org.springframework.http.HttpStatus.OK;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final DtoMapper dtoMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public ApiResponse<SignupResponse> createUser(UserSignupRequest userSignupRequest) {
       Optional <User> user=  userRepository.findByEmail(userSignupRequest.getEmail());
       if (user.isPresent()){
           throw new UserAlreadyExistException("User already exist");
       }
       User newUser = dtoMapper.createNewUser(userSignupRequest);
       User savedUser = userRepository.save(newUser);
        SignupResponse signupResponse = dtoMapper.createSignupResponse(savedUser);
        return new ApiResponse<>("Account created","Successful", HttpStatus.CREATED,signupResponse);
    }
    @Override
    public ApiResponse<LoginResponse> login(LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword()));

            if (authentication.isAuthenticated()) {
                SecurityContextHolder.getContext().setAuthentication(authentication);
                UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

                Roles userRole = extractClientRole(userDetails);

                String token = jwtService.generateToken(authentication, userRole);
                LoginResponse loginResponse = dtoMapper.createLoginResponse(userDetails, token);

                return new ApiResponse<>("Successfully logged in", "Success", OK, loginResponse);
            } else
                return new ApiResponse<>("Logged in failed", null, NETWORK_AUTHENTICATION_REQUIRED);

        } catch (DisabledException e) {
            throw new RuntimeException("client is disabled");
        } catch (BadCredentialsException e) {
            throw new InvalidCredentialException("Invalid credentials: Enter email and password again");
        }
    }

    private Roles extractClientRole(UserDetails userDetails) {
        if (userDetails == null) {
            throw new IllegalArgumentException("Invalid entry: retry again");
        }

        if (userDetails instanceof UserDetailsImpl clientDetailsImpl) {
            return Roles.valueOf(
                    clientDetailsImpl.getAuthorities().stream().findFirst().map(GrantedAuthority::getAuthority).orElse(null)
            );
        } else {
            Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
            if (authorities.isEmpty()) {
                throw new IllegalStateException("client has no authorities");
            }
            GrantedAuthority authority = authorities.iterator().next();
            String roleString = authority.getAuthority();

            try {
                return Roles.valueOf(roleString);
            } catch (IllegalArgumentException e) {
                throw new IllegalStateException("Invalid client role: " + roleString);
            }
        }
    }

}
