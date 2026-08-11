package com.gla.app.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class GenderValidator implements ConstraintValidator<GenderValidation, String> {

	@Override
	public boolean isValid(String gender, ConstraintValidatorContext context) {

		return gender != null && (gender.equalsIgnoreCase("Male") || gender.equalsIgnoreCase("Female"));
	}
}