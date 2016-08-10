package com.bitwise.model;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class LoginValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return UserCredentials.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		// UserCredentials credentials = (UserCredentials) obj;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userName", "userName.required", "Email is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "password.required", "Password is required.");

	}

}
