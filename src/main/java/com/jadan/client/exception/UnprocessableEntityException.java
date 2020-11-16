package com.jadan.client.exception;

import com.jadan.client.validation.ValidationError;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Getter
@Slf4j
public class UnprocessableEntityException extends BaseException {
    public static final int ERROR_CODE = 422;

    public UnprocessableEntityException(String message, List<ValidationError> validationErrors) {
        super(message, ERROR_CODE, validationErrors);
    }
}
