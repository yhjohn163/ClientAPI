package com.jadan.client.controller;

import com.jadan.client.exception.BadRequestException;
import com.jadan.client.exception.NotFoundException;
import com.jadan.client.exception.UnprocessableEntityException;
import com.jadan.client.model.domain.Client;
import com.jadan.client.model.view.ClientPatchRequest;
import com.jadan.client.model.view.ClientRequest;
import com.jadan.client.model.view.ClientResponse;
import com.jadan.client.model.view.ListResponse;
import com.jadan.client.service.ClientService;
import com.jadan.client.validation.ClientPatchValidator;
import com.jadan.client.validation.ClientValidator;
import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.apache.logging.log4j.util.Strings.isBlank;

@SuppressWarnings("unchecked")
@RestController
@Api(value = "Client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private ClientValidator clientValidator;

    @Autowired
    private ClientPatchValidator clientPatchValidator;


    @ApiOperation("Gets the client record by Id.")
    @GetMapping(value = "/client/{clientId}")
    public ClientResponse get(@PathVariable("clientId") Long clientId) throws NotFoundException {
        Client client = clientService.getClient(clientId);
        return new ClientResponse(client);
    }

    @ApiOperation("List the client records by name or CPF or both.")
    @GetMapping(value = "/client" )
    public ListResponse list(String cpf, String name, Pageable pageable) throws BadRequestException {
        if(isBlank(cpf) && isBlank(name)){
            throw new BadRequestException("please provide 'name' or 'cpf' as a query parameter.");
        }
        Page<Client> clients = clientService.listClients(name, cpf, pageable);
        return new ListResponse(clients);
    }


    @ApiOperation("Add new client record.")
    @PostMapping(value = "/client")
    @ResponseStatus(HttpStatus.CREATED)
    public ClientResponse create(@Valid @RequestBody ClientRequest clientRequest) throws UnprocessableEntityException {
        clientValidator.validate(clientRequest);
        Client client = clientRequest.toDomainForCreate();

        Client newClient = clientService.create(client);
        return new ClientResponse(newClient);
    }

    @ApiOperation("Delete the client record by Id.")
    @DeleteMapping(value = "/client/{clientId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void delete(@PathVariable("clientId") Long clientId) throws NotFoundException {
        System.out.println("client was deleted: " + clientId);
        clientService.delete(clientId);
    }


    @ApiOperation("Update the client record by Id.")
    @PutMapping(value = "/client/{clientId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void update(@PathVariable("clientId") Long clientId, @Valid @RequestBody ClientRequest clientRequest  ) throws UnprocessableEntityException, NotFoundException {
        System.out.println("client was updated: " + clientId);
        System.out.println("body:" + clientRequest.toString());
        clientValidator.validate(clientRequest);
        Client client =clientRequest.toDomainForUpdate(clientId);
        clientService.update(client);
    }

    @ApiOperation("Update and Patch the client record by Id.")
    @PatchMapping(value = "/client/{clientId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void patch(@PathVariable("clientId") Long clientId, @RequestBody ClientPatchRequest clientRequest  ) throws UnprocessableEntityException, NotFoundException {
        clientPatchValidator.validate(clientRequest);
        Client client =clientRequest.toDomain(clientId);
        clientService.patch(client);
    }

}
