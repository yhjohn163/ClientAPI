package com.jadan.client;

import com.jadan.client.model.domain.Client;
import com.jadan.client.model.entity.ClientDTO;
import com.jadan.client.model.view.ClientRequest;

import java.time.LocalDate;

public class TestUtils {

    public static Client.ClientBuilder getValidClientBuilder(){
       return  Client.builder()
                .withBirthDate(LocalDate.now().minusYears(20))
                .withName("test one")
                .withCPF("93396928024")
                .withAge(22);
    }

    public static ClientDTO getValidClientMySql(){
        Client client = getValidClientBuilder()
                .withId(88L).build();
        return new ClientDTO(client);
    }

    public static ClientRequest getValidClientRequest(){
        ClientRequest client = new ClientRequest();
        client.setName("test");
        client.setCPF("121.314.040-42");
        client.setBirthdate("31-05-2001");
        return client;
    }
}
