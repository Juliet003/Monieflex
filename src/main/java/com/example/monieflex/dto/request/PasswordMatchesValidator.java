package com.example.monieflex.dto.request;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, UserSignupRequest> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }

    @Override
    public boolean isValid(UserSignupRequest userSignupRequest, ConstraintValidatorContext context) {
        return userSignupRequest.getPassword().equals(userSignupRequest.getConfirmPassword());
    }
}
