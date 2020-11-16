package com.jadan.client.model.view;

import com.jadan.client.model.domain.Client;
import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
public class ClientPatchRequest {
    String name;
    String CPF;
    String birthdate ;


    public Client toDomain(Long clientId) {
        return Client.builder()
                .withId(clientId)
                .withCPF(CPF)
                .withName(name)
                .withBirthDate(
                        birthdate != null
                                ? LocalDate.parse(birthdate, DateTimeFormatter.ofPattern(ClientRequest.PATTERN))
                                : null
                )
                .build();
    }
}
