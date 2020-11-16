package com.jadan.client.model.view;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jadan.client.model.domain.Client;
import com.jadan.client.utils.DateUtils;
import lombok.Data;
import lombok.extern.java.Log;


@Data
@JsonSerialize
public class ClientResponse {
    Long id;
    String name;
    String CPF;
    int age;

    public ClientResponse(Client client) {
        this.id = client.getId();
        this.name = client.getName();
        this.CPF = client.getCPF();
        this.age = DateUtils.calculateAge(client.getBirthDate());
    }


}
