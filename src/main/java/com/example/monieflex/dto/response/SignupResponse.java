package com.example.monieflex.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Map;

@Setter
@Getter
@Builder
public class SignupResponse {
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Map<String, Object> virtualAccountDetails;

}
