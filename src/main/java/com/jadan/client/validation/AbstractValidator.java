package com.jadan.client.validation;


import com.jadan.client.exception.UnprocessableEntityException;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractValidator<T> {

	public void validate(T validatable, AbstractValidator<T>... validators) throws UnprocessableEntityException  {
		Validation validation = new Validation();
		List<AbstractValidator<T>> extraValidators = Arrays.asList( validators );

		this.apply(validatable, validation);
		for( AbstractValidator<T> validator : extraValidators ) {
			validator.apply(validatable, validation);
		}

		if ( !validation.getValidationErrors().isEmpty() ) {
			throw new UnprocessableEntityException( "Invalid request. Check the validation errors", validation.getValidationErrors() );
		}
	}
	
	protected abstract void apply(T validatable, Validation validation);

}
