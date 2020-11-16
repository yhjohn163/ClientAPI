package com.jadan.client.validation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jadan.client.exception.BaseException;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;



@Data
@JsonSerialize
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
public class ExceptionResponseJson implements Serializable {

    private final String errorCode;

    private final String message;

    @JsonProperty("validationErrors")
    private List<ValidationError> validationErrors;

    public ExceptionResponseJson(int statusCode, String message, BindingResult bindingResult) {
        this.errorCode = String.valueOf(statusCode);
        this.message = message;
        this.validationErrors = parseResult(bindingResult);
    }

    public ExceptionResponseJson(BaseException ex) {
        this.errorCode = String.valueOf(ex.getStatusCode());
        this.message = ex.getMessage();
        this.validationErrors = ex.getValidationErrors();
    }

    public ExceptionResponseJson(int statsCode , String message, List<ValidationError> validationErrors) {
        this.errorCode = String.valueOf(statsCode);
        this.message = message;
        this.validationErrors = validationErrors;
    }

    private List<ValidationError> parseResult(BindingResult bindingResult) {
        validationErrors = new ArrayList<>();
        bindingResult.getAllErrors().forEach((error) -> {
            validationErrors.add(new ValidationError(((FieldError) error).getField(), error.getDefaultMessage()));
        });
        return validationErrors;
    }

}





