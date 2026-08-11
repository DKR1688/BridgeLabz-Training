package com.gla.app.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = GenderValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface GenderValidation {

    String message() default "Gender must be Male or Female";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}