package com.jadan.client.validation;

import com.jadan.client.TestUtils;
import com.jadan.client.exception.UnprocessableEntityException;
import com.jadan.client.model.view.ClientRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClientValidatorShould {

    private final ClientValidator clientValidator = new ClientValidator();


    @Test
    void throwUnprocessableEntityWhenBirthdateAndCPFAreInvalid() throws Exception {
        ClientRequest clientRequest = new ClientRequest();
        clientRequest.setBirthdate("01-09-30");
        clientRequest.setCPF("99999999999");
        try {
            clientValidator.validate(clientRequest);
        } catch (UnprocessableEntityException e) {
            assertEquals(e.getValidationErrors().size(), 2);
            assertEquals(e.getValidationErrors().get(0).getFieldName(), "birthdate");
            assertEquals(e.getValidationErrors().get(1).getFieldName(), "CPF");
        }
    }

    @Test
    void validateSuccessfully() throws Exception {
        ClientRequest validClientRequest = TestUtils.getValidClientRequest();
        clientValidator.validate(validClientRequest);
    }
}