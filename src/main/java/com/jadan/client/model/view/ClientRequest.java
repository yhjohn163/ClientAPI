package com.jadan.client.model.view;

import com.jadan.client.model.domain.Client;
import lombok.Data;

import javax.validation.constraints.NotBlank;

import static com.jadan.client.utils.DateUtils.toLocalDate;

@Data
public class ClientRequest {

    public static String PATTERN = "dd-MM-yyyy";


    @NotBlank(message = "{client.name.blank}")
    private String name;

    @NotBlank(message = "{client.cpf.blank}")
    private String CPF;

    @NotBlank(message = "{client.birthdate.blank}")
    private String birthdate ;


    public Client toDomainForCreate() {
        return  Client.builder()
                .withBirthDate(toLocalDate(birthdate))
                .withName(name)
                .withCPF(CPF)
                .build();
    }

    public Client toDomainForUpdate(Long clientId) {
        return  Client.builder()
                .withId(clientId)
                .withBirthDate(toLocalDate(birthdate))
                .withName(name)
                .withCPF(CPF)
                .build();
    }




}
