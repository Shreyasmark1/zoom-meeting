package com.example.zoommeeting.util.validation.validators;

import com.example.zoommeeting.util.StringUtil;
import com.example.zoommeeting.util.Validator;
import com.example.zoommeeting.util.validation.annotations.CustomName;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CustomNameValidation implements ConstraintValidator<CustomName, String> {
    @Override
    public void initialize(CustomName constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(StringUtil.isEmpty(value)) return true;

        if(value.endsWith(" ") || value.startsWith(" ")){
            context.buildConstraintViolationWithTemplate("No trailing spaces allowed for name");
            return false;
        }

        try{
            Validator.name(value);
            return true;
        } catch (Exception e){
            context.buildConstraintViolationWithTemplate(e.getMessage());
            return false;
        }
    }
}
