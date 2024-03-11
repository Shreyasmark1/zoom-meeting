package com.example.zoommeeting.util.validation.validators;

import com.example.zoommeeting.util.Validator;
import com.example.zoommeeting.util.validation.annotations.CustomMobileNo;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CustomMobileNoValidator implements ConstraintValidator<CustomMobileNo, String> {


    @Override
    public void initialize(CustomMobileNo constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value.startsWith(" ") || value.endsWith(" ")) return true;

        try {
            Validator.mobile(value);
            return true;
        } catch (Exception e) {
            context.buildConstraintViolationWithTemplate(e.getMessage());
            return false;
        }
    }
}
