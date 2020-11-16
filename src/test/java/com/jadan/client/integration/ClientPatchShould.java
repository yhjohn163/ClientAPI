package com.jadan.client.integration;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.jadan.client.TestUtils;
import com.jadan.client.model.domain.Client;
import com.jadan.client.model.entity.ClientDTO;
import com.jadan.client.model.view.ClientRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static com.jadan.client.utils.CPFUtils.removeFormat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ClientPatchShould extends AbstractIntegrationTest{

    private ClientDTO persistedNewClient;
    private  Long id;

    @BeforeEach
    public void setUp(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        persistedNewClient = persistNewClient();
        this.id =  persistedNewClient.toDomain().getId();
    }

    @AfterEach
    public void clean(){
        cleanUp(id);
    }

    @Test
    public void patchClientNameSuccessfully() throws Exception {

        ClientRequest clientRequest = new ClientRequest();
        clientRequest.setName("new patch name");
        String jsonBody = new ObjectMapper().writeValueAsString(clientRequest);

        mockMvc.perform(
                patch("/client/" + id )
                        .contentType(APPLICATION_JSON )
                        .content(jsonBody)
                )
                .andDo(print())

                .andExpect(status().isNoContent());

        Optional<ClientDTO> patchedClientMySql = clientRepository.findById(id);
        Client patchedClient = patchedClientMySql.get().toDomain();

        assertEquals(clientRequest.getName(), patchedClient.getName());
        assertEquals(persistedNewClient.toDomain().getCPF(), patchedClient.getCPF());
        assertEquals(persistedNewClient.toDomain().getBirthDate(), patchedClient.getBirthDate());
    }

    @Test
    public void patchClientBirthdateSuccessfully() throws Exception {

        ClientRequest clientRequest = new ClientRequest();
        clientRequest.setBirthdate("01-08-1981");
        String jsonBody = new ObjectMapper().writeValueAsString(clientRequest);

        mockMvc.perform(
                patch("/client/" + id )
                        .contentType(APPLICATION_JSON )
                        .content(jsonBody)
        )
                .andDo(print())

                .andExpect(status().isNoContent());

        Optional<ClientDTO> patchedClientMySql = clientRepository.findById(id);
        Client patchedClient = patchedClientMySql.get().toDomain();

        Client requestClient = clientRequest.toDomainForUpdate(id);
        assertEquals(requestClient.getBirthDate(), patchedClient.getBirthDate());
        assertEquals(persistedNewClient.toDomain().getName(), patchedClient.getName());
        assertEquals(persistedNewClient.toDomain().getCPF(), patchedClient.getCPF());

    }

    @Test
    public void patchClientCPFSuccessfully() throws Exception {

        ClientRequest clientRequest = new ClientRequest();
        clientRequest.setCPF("622.585.620-07");
        String jsonBody = new ObjectMapper().writeValueAsString(clientRequest);

        mockMvc.perform(
                patch("/client/" + id )
                        .contentType(APPLICATION_JSON )
                        .content(jsonBody)
                )
                .andDo(print())

                .andExpect(status().isNoContent());

        Optional<ClientDTO> patchedClientMySql = clientRepository.findById(id);
        Client patchedClient = patchedClientMySql.get().toDomain();

        assertEquals(removeFormat(clientRequest.getCPF()), patchedClient.getCPF());
        assertEquals(persistedNewClient.toDomain().getBirthDate(), patchedClient.getBirthDate());
        assertEquals(persistedNewClient.toDomain().getName(), patchedClient.getName());

    }

    @Test
    public void returnNotFoundWhenIdDoesNotExists() throws Exception {
        ClientRequest clientRequest = new ClientRequest();
        clientRequest.setName("new patch name");
        String jsonBody = new ObjectMapper().writeValueAsString(clientRequest);

        mockMvc.perform(
                patch("/client/" + "199"  )
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

        ClientRequest clientRequest = new ClientRequest();
        clientRequest.setCPF("93396928024x");

        String jsonBody = new ObjectMapper().writeValueAsString(clientRequest);

        mockMvc.perform(
                patch("/client/" + id  )
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
        ClientRequest clientRequest = new ClientRequest();
        clientRequest.setBirthdate("31-1991-00");

        String jsonBody = new ObjectMapper().writeValueAsString(clientRequest);

        mockMvc.perform(
                patch("/client/" + id  )
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
                patch("/client/" + id  )
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
    public void returnSuccessWhenNameOrCPFOrBirthdateIsNull() throws Exception {
        ClientRequest client = new ClientRequest();

        String jsonBody = new ObjectMapper().writeValueAsString(client);

        mockMvc.perform(
                patch("/client/" + id  )
                        .header("Content-Type","application/json" )
                        .content(jsonBody)
        )

                .andDo(print())

                .andExpect(status().isNoContent());
    }
}