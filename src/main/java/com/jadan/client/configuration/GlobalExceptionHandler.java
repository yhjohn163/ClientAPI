package com.jadan.client.configuration;

import com.jadan.client.exception.BaseException;
import com.jadan.client.exception.UnprocessableEntityException;
import com.jadan.client.validation.ExceptionResponseJson;
import com.jadan.client.validation.ValidationError;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private final String message = "Invalid request. Check the validation errors";

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponseJson> handleValidationExceptions( MethodArgumentNotValidException ex) {
        int httpStatus = HttpStatus.UNPROCESSABLE_ENTITY.value();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json");
        ExceptionResponseJson exceptionResponseJson = new ExceptionResponseJson( httpStatus, message, ex.getBindingResult());

        return new ResponseEntity<>(exceptionResponseJson, httpHeaders, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    private ResponseEntity<ExceptionResponseJson> handleConstraintValidationExceptions(ConstraintViolationException e) {
        List<ValidationError> validationErrors = new ArrayList<>();
        for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
            validationErrors.add(new ValidationError(
                    ((PathImpl)violation.getPropertyPath()).getLeafNode().getName(),
                    violation.getMessage()));
        }
        UnprocessableEntityException ex = new UnprocessableEntityException(message, validationErrors);
        ExceptionResponseJson exceptionResponseJson = new ExceptionResponseJson(ex);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json");
        return new ResponseEntity<>(exceptionResponseJson, httpHeaders, HttpStatus.valueOf(UnprocessableEntityException.ERROR_CODE));
    }

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ExceptionResponseJson> handleBaseException(BaseException e){
        ExceptionResponseJson exceptionResponseJson = new ExceptionResponseJson(e.getStatusCode(), e.getMessage(), e.getValidationErrors());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json");
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(exceptionResponseJson, httpHeaders, HttpStatus.valueOf(e.getStatusCode()));
    }
}
