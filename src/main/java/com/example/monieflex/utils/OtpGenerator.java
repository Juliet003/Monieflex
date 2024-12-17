package com.example.monieflex.utils;

import java.security.SecureRandom;

public class OtpGenerator {
    private static final String DIGITS = "0123456789";
    private static final int OTP_LENGTH = 6;
    private static final SecureRandom random = new SecureRandom();

    public static String generateOtp(){
        StringBuilder otp = new StringBuilder(OTP_LENGTH);
        for (int i = 0;i < OTP_LENGTH;i++){
            otp.append(DIGITS.charAt(random.nextInt(DIGITS.length())));
        }
        return otp.toString();
    }
}