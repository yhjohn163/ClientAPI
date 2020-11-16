package com.jadan.client.validation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;

@JsonSerialize
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@Data
public class ValidationError {
    private String fieldName;
    private String message;
}