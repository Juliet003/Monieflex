package com.example.monieflex.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@Builder
public class SignupResponse {
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String bvn;
    private BigDecimal wallet;
}
