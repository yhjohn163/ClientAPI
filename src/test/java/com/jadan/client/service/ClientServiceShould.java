package com.jadan.client.service;

import com.jadan.client.TestUtils;
import com.jadan.client.exception.NotFoundException;
import com.jadan.client.model.domain.Client;
import com.jadan.client.model.entity.ClientDTO;
import com.jadan.client.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static com.jadan.client.TestUtils.getValidClientBuilder;
import static com.jadan.client.TestUtils.getValidClientMySql;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class ClientServiceShould {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientService clientService;

    @BeforeEach
    public void setup(){
        openMocks(this);
    }
    @Test
    void createClientSuccessfully() {
        Client validClient = TestUtils.getValidClientBuilder().build();
        when(clientRepository.save(any(ClientDTO.class))).thenReturn(getValidClientMySql());
        Client client = clientService.create(validClient);
        assertNotNull(client);
    }

    @Test
    void deleteSuccessfully() throws Exception {
        Long id = 5L;
        when(clientRepository.findById(id)).thenReturn(Optional.of(getValidClientMySql()));
        clientService.delete(id);
        verify(clientRepository,times(1)).delete(any(ClientDTO.class));
    }
    @Test
    void notDeleteWhenIdDosNotExists() {
        Long id = 500L;
        when(clientRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> clientService.delete(id))  ;
        verify(clientRepository,never()).delete(any(ClientDTO.class));
    }

    @Test
    void getClientSuccessfully() throws Exception {
        Long id = 5L;
        when(clientRepository.findById(id)).thenReturn(Optional.of(getValidClientMySql()));
        Client client = clientService.getClient(id);
        assertNotNull(client);
    }

    @Test
    void throwNotFoundExceptionOnGetClientWhenIdNotFound() throws Exception {
        Long id = 5L;
        when(clientRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> clientService.getClient(id));

        verify(clientRepository,times(1)).findById(id);
    }

    @Test
    void patchClientNameSuccessfully() throws  Exception{
        Client validClient = getValidClientBuilder()
                .withId(5L)
                .withName("new patch name")
                .build();

        ClientDTO persistedClient = patchClientTest(validClient);

        assertEquals(persistedClient.toDomain().getName(), validClient.getName());
    }

    @Test
    void patchClientCPFSuccessfully() throws  Exception{
        Client validClient = getValidClientBuilder()
                .withId(5L)
                .withCPF("873.598.670-02")
                .build();

        ClientDTO persistedClient = patchClientTest(validClient);

        assertEquals(persistedClient.toDomain().getCPF(), validClient.getCPF());
    }

    @Test
    void patchClientBirthdateSuccessfully() throws  Exception{
        Client validClient = getValidClientBuilder()
                .withId(5L)
                .withBirthDate(LocalDate.now().minusYears(18))
                .build();

        ClientDTO persistedClient = patchClientTest(validClient);

        assertEquals(persistedClient.toDomain().getBirthDate(), validClient.getBirthDate());
    }

    @Test
    void donNotPatchWithNullValues() throws Exception {
        Long id = 5L;

        Client client = Client.builder().withId(id).build();
        ClientDTO validClientDTO = new ClientDTO(getValidClientBuilder().build());
        validClientDTO.setId(id);

        when(clientRepository.findById(id)).thenReturn(Optional.of(validClientDTO));

        clientService.patch(client);

        verify(clientRepository, never()).save(any(ClientDTO.class));
    }

    @Test
    void throwNotFoundExceptionOnPatchWhenIdNotFound() throws Exception {
        Long id = 5L;

        Client client = Client.builder().withId(id).build();

        when(clientRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> clientService.patch(client));

        verify(clientRepository, never()).save(any(ClientDTO.class));
    }
    private ClientDTO patchClientTest(Client validClient) throws NotFoundException {
        ClientDTO validClientDTO = getValidClientMySql();
        ArgumentCaptor<ClientDTO> argumentCaptor = ArgumentCaptor.forClass(ClientDTO.class);
        when(clientRepository.findById(5L)).thenReturn(Optional.of(validClientDTO));
        when(clientRepository.save(any(ClientDTO.class))).thenReturn(validClientDTO);

        clientService.patch(validClient);

        verify(clientRepository, times(1)).save(argumentCaptor.capture());
        return argumentCaptor.getValue();
    }

    @Test
    void updateClientSuccessfully() throws Exception {
        Client validClient = getValidClientBuilder()
                .withId(5L)
                .withBirthDate(LocalDate.now().minusYears(44))
                .withName("updated name")
                .build();

        ClientDTO validClientDTO = getValidClientMySql();
        ArgumentCaptor<ClientDTO> argumentCaptor = ArgumentCaptor.forClass(ClientDTO.class);
        when(clientRepository.findById(5L)).thenReturn(Optional.of(validClientDTO));
        when(clientRepository.save(any(ClientDTO.class))).thenReturn(validClientDTO);

        clientService.update(validClient);

        verify(clientRepository, times(1)).save(argumentCaptor.capture());
        ClientDTO persistedClient = argumentCaptor.getValue();
        assertEquals(persistedClient.toDomain().getBirthDate(), validClient.getBirthDate());
        assertEquals(persistedClient.toDomain().getName(), validClient.getName());

    }

    @Test
    void throwNotFoundExceptionOnUpdateWhenIdNotFound() {
        Client client = Client.builder().withId(5L).build();

        when(clientRepository.findById(5L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> clientService.update(client));

        verify(clientRepository, never()).save(any(ClientDTO.class));
    }

    @Test
    void listClientsByNameAndCPFSuccessfully() {

        ClientDTO validClientDTO = getValidClientMySql();
        PageImpl<ClientDTO> page = new PageImpl<>(Collections.singletonList(validClientDTO));
        when(clientRepository.findByNameContainingAndCPF(anyString(),anyString(), any(Pageable.class))).thenReturn(page);
        Page<Client> clientPage = clientService.listClients("new client", "87359867002", PageRequest.of(0, 2));

        assertEquals(clientPage.getTotalElements(), 1);
        assertEquals(clientPage.getContent().get(0).getName(), validClientDTO.toDomain().getName());
        assertEquals(clientPage.getContent().get(0).getCPF(), validClientDTO.toDomain().getCPF());
        assertEquals(clientPage.getContent().get(0).getBirthDate(), validClientDTO.toDomain().getBirthDate());

        verify(clientRepository, never()).findByCPF(any(),any(Pageable.class));
        verify(clientRepository, never()).findByNameContaining(any(),any(Pageable.class));
    }

    @Test
    void listClientsByCPFSuccessfully() {

        ClientDTO validClientDTO = getValidClientMySql();
        PageImpl<ClientDTO> page = new PageImpl<>(Collections.singletonList(validClientDTO));
        when(clientRepository.findByCPF(anyString(), any(Pageable.class))).thenReturn(page);
        Page<Client> clientPage = clientService.listClients(null, "87359867002", PageRequest.of(0, 2));

        assertEquals(clientPage.getTotalElements(), 1);
        assertEquals(clientPage.getContent().get(0).getName(), validClientDTO.toDomain().getName());
        assertEquals(clientPage.getContent().get(0).getCPF(), validClientDTO.toDomain().getCPF());
        assertEquals(clientPage.getContent().get(0).getBirthDate(), validClientDTO.toDomain().getBirthDate());

        verify(clientRepository, never()).findByNameContainingAndCPF(anyString(), anyString(), any(Pageable.class));
        verify(clientRepository, never()).findByNameContaining(any(),any(Pageable.class));
    }

    @Test
    void listClientsByNameSuccessfully() {

        ClientDTO validClientDTO = getValidClientMySql();
        PageImpl<ClientDTO> page = new PageImpl<>(Collections.singletonList(validClientDTO));
        when(clientRepository.findByNameContaining(anyString(), any(Pageable.class))).thenReturn(page);
        Page<Client> clientPage = clientService.listClients("new client", null, PageRequest.of(0, 2));

        assertEquals(clientPage.getTotalElements(), 1);
        assertEquals(clientPage.getContent().get(0).getName(), validClientDTO.toDomain().getName());
        assertEquals(clientPage.getContent().get(0).getCPF(), validClientDTO.toDomain().getCPF());
        assertEquals(clientPage.getContent().get(0).getBirthDate(), validClientDTO.toDomain().getBirthDate());

        verify(clientRepository, never()).findByNameContainingAndCPF(anyString(), anyString(), any(Pageable.class));
        verify(clientRepository, never()).findByCPF(any(),any(Pageable.class));
    }

    @Test
    void returnEmptyListWhenNothingFound() {

        PageImpl<ClientDTO> page = new PageImpl<>(Collections.emptyList());
        when(clientRepository.findByNameContaining(anyString(), any(Pageable.class))).thenReturn(page);
        Page<Client> clientPage = clientService.listClients("new client", null, PageRequest.of(0, 2));

        assertEquals(clientPage.getTotalElements(), 0);

        verify(clientRepository, never()).findByNameContainingAndCPF(anyString(), anyString(), any(Pageable.class));
        verify(clientRepository, never()).findByCPF(any(),any(Pageable.class));
    }
}
