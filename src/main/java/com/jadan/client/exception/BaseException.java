package com.jadan.client.exception;

import com.jadan.client.validation.ValidationError;
import lombok.Getter;

import javax.servlet.ServletException;
import java.util.List;

@Getter
public class BaseException extends ServletException {

    private final String message ;
    private final int statusCode;
    private  List<ValidationError> validationErrors ;

    public BaseException(String message, int statusCode, List<ValidationError> validationErrors) {
        this.message = message;
        this.statusCode = statusCode;
        this.validationErrors = validationErrors;
    }

    public BaseException(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }
}
