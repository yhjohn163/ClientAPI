package com.jadan.client.integration;


import com.jadan.client.TestUtils;
import com.jadan.client.model.domain.Client;
import com.jadan.client.model.entity.ClientDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ClientGetShould extends AbstractIntegrationTest{

    @BeforeEach
    public void setUp(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void getClientByIdSuccessfully() throws Exception {
        Client getClient = TestUtils.getValidClientBuilder()
                .withName("Get Test").build();
        ClientDTO clientDTO = clientRepository.save(new ClientDTO(getClient));

        Long id = clientDTO.toDomain().getId();

        mockMvc.perform(get("/client/" + id )
                .contentType(APPLICATION_JSON )
                )
                .andDo(print())

                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(id))
                .andExpect(jsonPath("name").value("get test"))
                .andExpect(jsonPath("cpf").value("93396928024"))
                .andExpect(jsonPath("age").value(20));

    }

    @Test
    public void returnNotFoundWhenIdDoesNotExists() throws Exception {
        mockMvc.perform(get("/client/" + "199" )
                .contentType(APPLICATION_JSON )
                )
                .andDo(print())

                .andExpect(status().isNotFound())
                .andExpect(jsonPath("errorCode").value("404"))
                .andExpect(jsonPath("message").value("resource not found"));
    }
}