package com.jadan.client.integration;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.jadan.client.TestUtils;
import com.jadan.client.model.domain.Client;
import com.jadan.client.model.entity.ClientDTO;
import com.jadan.client.model.view.ClientRequest;
import org.junit.jupiter.api.*;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ClientPutUpdateShould extends AbstractIntegrationTest{
    private ClientDTO persistedNewClient;
    private  Long id;

    @BeforeEach
    public void setUp(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        persistedNewClient = persistNewClient();
        this.id = persistedNewClient.toDomain().getId();
    }

    @AfterEach
    public void clean(){
        cleanUp(id);
    }

    @Test
    public void updateClientSuccessfully() throws Exception {

        ClientRequest client = TestUtils.getValidClientRequest();
        client.setName("update client Name");
        String jsonBody = new ObjectMapper().writeValueAsString(client);

        mockMvc.perform(
                put("/client/" + id)
                        .contentType(APPLICATION_JSON )
                        .content(jsonBody)
                )
                .andDo(print())

                .andExpect(status().isNoContent());

        Optional<ClientDTO> clientMySql = clientRepository.findById(id);
        Client updatedClient = clientMySql.get().toDomain();

        Client requestClient = client.toDomainForUpdate(id);
        assertEquals(requestClient.getName(), updatedClient.getName());
        assertEquals(requestClient.getBirthDate(), updatedClient.getBirthDate());
        assertEquals(requestClient.getCPF(), updatedClient.getCPF());

    }

    @Test
    public void returnNotFoundWhenIdDoesNotExists() throws Exception {

        ClientRequest client = TestUtils.getValidClientRequest();
        String jsonBody = new ObjectMapper().writeValueAsString(client);

        mockMvc.perform(
                put("/client/" + "199"  )
                        .header("Content-Type","application/json" )
                        .content(jsonBody)
                )
                .andDo(print())

                .andExpect(status().isNotFound())
                .andExpect(jsonPath("errorCode").value("404"))
                .andExpect(jsonPath("message").value("resource not found"));
    }

    @Test
    public void returnUnprocessableWhenCPFisInvalid() throws Exception {
        ClientRequest client = TestUtils.getValidClientRequest();
        client.setCPF("93396928024x");

        String jsonBody = new ObjectMapper().writeValueAsString(client);

        mockMvc.perform(
                put("/client/" + id  )
                        .header("Content-Type","application/json" )
                        .content(jsonBody)
                )
                .andDo(print())

                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("errorCode").value("422"))
                .andExpect(jsonPath("message").value("Invalid request. Check the validation errors"))
                .andExpect(jsonPath("validationErrors",hasSize(1)))
                .andExpect(jsonPath("validationErrors[0].fieldName").value("CPF"))
                .andExpect(jsonPath("validationErrors[0].message").value("CPF is invalid"));
    }

    @Test
    public void returnUnprocessableWhenBirthdateIsInvalid() throws Exception {
        ClientRequest client = TestUtils.getValidClientRequest();
        client.setBirthdate("31-1991-00");

        String jsonBody = new ObjectMapper().writeValueAsString(client);

        mockMvc.perform(
                put("/client/" + id  )
                        .header("Content-Type","application/json" )
                        .content(jsonBody)
                )

                .andDo(print())

                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("errorCode").value("422"))
                .andExpect(jsonPath("message").value("Invalid request. Check the validation errors"))
                .andExpect(jsonPath("validationErrors",hasSize(1)))
                .andExpect(jsonPath("validationErrors[0].fieldName").value("birthdate"))
                .andExpect(jsonPath("validationErrors[0].message").value("birthday should be in dd-mm-yyyy format"));
    }

    @Test
    public void returnUnprocessableWhenNameIsEmpty() throws Exception {
        ClientRequest client = TestUtils.getValidClientRequest();
        client.setName("");

        String jsonBody = new ObjectMapper().writeValueAsString(client);

        mockMvc.perform(
                put("/client/" + id  )
                        .header("Content-Type","application/json" )
                        .content(jsonBody)
                )
                .andDo(print())

                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("errorCode").value("422"))
                .andExpect(jsonPath("message").value("Invalid request. Check the validation errors"))
                .andExpect(jsonPath("validationErrors",hasSize(1)))
                .andExpect(jsonPath("validationErrors[0].fieldName").value("name"))
                .andExpect(jsonPath("validationErrors[0].message").value("name can not be empty"));
    }


    @Test
    public void returnUnprocessableWhenNameOrCPFOrBirthdateisNull() throws Exception {
        ClientRequest client = new ClientRequest();

        String jsonBody = new ObjectMapper().writeValueAsString(client);

        mockMvc.perform(
                put("/client/" + id  )
                        .header("Content-Type","application/json" )
                        .content(jsonBody)
        )

                .andDo(print())

                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("errorCode").value("422"))
                .andExpect(jsonPath("message").value("Invalid request. Check the validation errors"))
                .andExpect(jsonPath("validationErrors",hasSize(3)))
                .andExpect(jsonPath("validationErrors[*].fieldName", hasItem("name")))
                .andExpect(jsonPath("validationErrors[*].fieldName", hasItem("birthdate")))
                .andExpect(jsonPath("validationErrors[*].fieldName", hasItem("CPF")))
                .andExpect(jsonPath("validationErrors[*].message", hasItem("name can not be empty")))
                .andExpect(jsonPath("validationErrors[*].message", hasItem("name can not be empty")))
                .andExpect(jsonPath("validationErrors[*].message", hasItem("name can not be empty")));
    }

}