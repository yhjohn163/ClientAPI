package com.jadan.client.validation;

import java.util.ArrayList;
import java.util.List;

public class Validation {
	
	private boolean invalid;
	private final List<ValidationError> validationErrors;

	public Validation() {
		validationErrors = new ArrayList<>();
	}

	public void addErrors(List<ValidationError> errors) {
		validationErrors.addAll(errors);
	}
	
	public void setError(String field, String reason, String ... args) {
		if (invalid) {
			validationErrors.add(new ValidationError(field, Messages.getMessage(reason, args)));
			invalid = false;
		}
	}

	public Validation setInvalid(boolean invalid) {
		this.invalid = invalid;
		return this;
	}

	public List<ValidationError> getValidationErrors() {
		return validationErrors;
	}

}
