package com.jadan.client.integration;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.jadan.client.TestUtils;
import com.jadan.client.model.view.ClientRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ClientPostCreateShould extends AbstractIntegrationTest{

    @BeforeEach
    public void setUp(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void createNewClientSuccessfully() throws Exception {
        ClientRequest client = TestUtils.getValidClientRequest();
        client.setName("create new client");
        String jsonBody = new ObjectMapper().writeValueAsString(client);

        mockMvc.perform(
                post("/client")
                        .contentType(APPLICATION_JSON)
                        .content(jsonBody)
        )
                .andDo(print())

                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("name").value("create new client"))
                .andExpect(jsonPath("cpf").value("12131404042"))
                .andExpect(jsonPath("age").value(19));

    }

    @Test
    public void returnUnprocessableWhenNameOrCPFOrBirthdateisNull() throws Exception {
        ClientRequest client = new ClientRequest();

        String jsonBody = new ObjectMapper().writeValueAsString(client);

        mockMvc.perform(
                post("/client" )
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

    @Test
    public void returnUnprocessableWhenCPFisInvalid() throws Exception {
        ClientRequest client = TestUtils.getValidClientRequest();
        client.setCPF("93396928024x");

        String jsonBody = new ObjectMapper().writeValueAsString(client);

        mockMvc.perform(
                post("/client" )
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
    public void returnUnprocessableWhenBirthdateisInvalid() throws Exception {
        ClientRequest client = TestUtils.getValidClientRequest();
        client.setBirthdate("31-1991-00");

        String jsonBody = new ObjectMapper().writeValueAsString(client);

        mockMvc.perform(post("/client" )
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

}