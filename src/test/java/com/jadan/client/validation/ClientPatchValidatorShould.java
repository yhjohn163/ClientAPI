package com.jadan.client.validation;

import com.jadan.client.exception.UnprocessableEntityException;
import com.jadan.client.model.view.ClientPatchRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClientPatchValidatorShould {

    private final ClientPatchValidator patchValidator = new ClientPatchValidator();

    @Test
    void validateSuccessfullyWithEmptyObject() throws Exception{
        ClientPatchRequest clientPatchRequest = new ClientPatchRequest();
        patchValidator.validate(clientPatchRequest);
    }

    @Test
    void returnUnprocessableEntityWhenCPFIsInvalid() {
        ClientPatchRequest clientPatchRequest = new ClientPatchRequest();
        clientPatchRequest.setCPF("99999999999");
        try {
            patchValidator.validate(clientPatchRequest);
        } catch (UnprocessableEntityException e) {
            assertEquals(e.getValidationErrors().size(), 1);
            assertEquals(e.getValidationErrors().get(0).getFieldName(), "CPF");
        }

    }
    @Test
    void returnUnprocessableEntityWhenBirthDateIsInvalid() {
        ClientPatchRequest clientPatchRequest = new ClientPatchRequest();
        clientPatchRequest.setBirthdate("0-33-222");
        try {
            patchValidator.validate(clientPatchRequest);
        } catch (UnprocessableEntityException e) {
            assertEquals(e.getValidationErrors().size(), 1);
            assertEquals(e.getValidationErrors().get(0).getFieldName(), "birthdate");
        }

    }
    @Test
    void returnUnprocessableEntityWhenNameIsEmpty() {
        ClientPatchRequest clientPatchRequest = new ClientPatchRequest();
        clientPatchRequest.setName("");
        try {
            patchValidator.validate(clientPatchRequest);
        } catch (UnprocessableEntityException e) {
            assertEquals(e.getValidationErrors().size(), 1);
            assertEquals(e.getValidationErrors().get(0).getFieldName(), "name");
        }
    }
}