package com.jadan.client.integration;

import com.jadan.client.ClientapiApplication;
import com.jadan.client.TestUtils;
import com.jadan.client.model.domain.Client;
import com.jadan.client.model.entity.ClientDTO;
import com.jadan.client.repository.ClientRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {ClientapiApplication.class}
)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class AbstractIntegrationTest {

    protected MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext context;

    @Autowired
    protected ClientRepository clientRepository;


    protected void cleanUp(Long id) {
        clientRepository.deleteById(id);
    }

    protected ClientDTO persistNewClient() {
        Client clientToPatch = TestUtils.getValidClientBuilder().build();
        return clientRepository.save(new ClientDTO(clientToPatch));
    }
}
