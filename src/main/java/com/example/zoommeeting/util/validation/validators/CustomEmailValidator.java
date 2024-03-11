package com.example.zoommeeting.util.validation.validators;

import com.example.zoommeeting.util.Validator;
import com.example.zoommeeting.util.validation.annotations.CustomEmail;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CustomEmailValidator implements ConstraintValidator<CustomEmail, String> {
    @Override
    public void initialize(CustomEmail constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return true;
        if (value.endsWith(" ") || value.startsWith(" ")) return false;

        try {
            Validator.email(value);
            return true;
        } catch (Exception e) {
            context.buildConstraintViolationWithTemplate(e.getMessage());
            return false;
        }

    }
}
