package com.example.monieflex.security.securityFilter;

import com.example.monieflex.security.securityImpl.JwtService;
import com.example.monieflex.security.securityImpl.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@RequiredArgsConstructor
@Component
public class JwtFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsServiceImpl   userDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String requestUri = request.getRequestURI();
        if (isRegistrationOrLoginRequest(requestUri)) {
            filterChain.doFilter(request, response);
            return;
        }

        final String requestTokenHeader = request.getHeader("Authorization");
        String jwtToken = requestTokenHeader != null &&
                requestTokenHeader.startsWith("Bearer ") ? requestTokenHeader.substring(7) : null;
        String currentUserEmail = jwtService.parseTokenClaims(jwtToken).get("email");
        UserDetails userDetails = userDetailsService.loadUserByUsername(currentUserEmail);

        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            UsernamePasswordAuthenticationToken authenticatedUserToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            authenticatedUserToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticatedUserToken);
        }
        filterChain.doFilter(request, response);
    }

    private boolean isRegistrationOrLoginRequest(String requestUri) {
        return requestUri.endsWith("/sign-up") || requestUri.endsWith("/login");
    }
    }

