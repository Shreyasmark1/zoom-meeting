package com.example.zoommeeting.util.validation.annotations;


import com.example.zoommeeting.util.validation.validators.CustomMobileNoValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CustomMobileNoValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomMobileNo {

    String message() default "Invalid mobile number";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
