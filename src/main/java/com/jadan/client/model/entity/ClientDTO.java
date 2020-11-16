package com.jadan.client.model.entity;

import com.jadan.client.model.domain.Client;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

import static com.jadan.client.utils.DateUtils.calculateAge;

@NoArgsConstructor
@Data
@Entity
@Table(name = "CLIENT")
public class ClientDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    Long id;

    @Column(name = "NAME", length = 50)
    String name;

    @Column(name = "CPF",length = 20)
    String CPF;

    @Column(name = "BIRTHDATE")
    LocalDate birthDate;

    public ClientDTO(Client client) {
        this.name = client.getName();
        this.CPF = client.getCPF();
        this.birthDate = client.getBirthDate();
    }

    public Client toDomain() {
        return Client.builder()
                .withId(this.id)
                .withBirthDate(this.birthDate)
                .withCPF(this.CPF)
                .withName(this.name)
                .withAge(calculateAge(this.birthDate))
                .build();
    }

    public void update(Client client) {
        this.name = client.getName();
        this.birthDate = client.getBirthDate();
        this.CPF = client.getCPF();
    }

    public boolean patch(Client client) {
        boolean changed = false;
        if (client.getName() != null) {
            this.name = client.getName();
            changed = true;
        }
        if (client.getBirthDate() != null) {
            this.birthDate = client.getBirthDate();
            changed = true;
        }
        if (client.getCPF() != null) {
            this.CPF = client.getCPF();
            changed = true;
        }
        return changed;
    }

}
