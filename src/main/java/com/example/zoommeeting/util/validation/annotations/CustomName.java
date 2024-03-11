package com.example.zoommeeting.util.validation.annotations;

import com.example.zoommeeting.util.validation.validators.CustomNameValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CustomNameValidation.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomName {

    String message() default "Invalid name format";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
