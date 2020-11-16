package com.jadan.client.model.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

import static com.jadan.client.utils.CPFUtils.removeFormat;

@Getter
@Builder(setterPrefix = "with")
public class Client {

    private Long id;
    private String name;
    private String CPF;
    private LocalDate birthDate;
    private int age;



    public static class ClientBuilder{
        private String name;
        private String CPF;

        public ClientBuilder withName(String name ) {
            if (name != null) {
                this.name = name.toLowerCase().trim();
            }
            return this;
        }

        public ClientBuilder withCPF(String cpf ) {
            if (cpf != null) {
                this.CPF = removeFormat(cpf);
            }
            return this;
        }

    }


}
