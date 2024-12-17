package com.example.monieflex.dto.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class OtpVerificationRequest {
    private String email;
    private String otp;
}
