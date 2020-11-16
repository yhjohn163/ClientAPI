package com.jadan.client.service;

import com.jadan.client.exception.InternalServerException;
import com.jadan.client.exception.NotFoundException;
import com.jadan.client.model.domain.Client;
import com.jadan.client.model.entity.ClientDTO;
import com.jadan.client.repository.ClientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.jadan.client.utils.CPFUtils.removeFormat;
import static org.apache.logging.log4j.util.Strings.isNotBlank;

@Service
@Slf4j
public class ClientService {

    @Autowired
    ClientRepository clientRepository;


    @Cacheable(value = "clients", key = "#clientId")
    public Client getClient(Long clientId) throws NotFoundException {
        try {
            Client client = getClientDTOById(clientId).toDomain();
            log.info("client found successfully, clientId:{}", clientId);
            return client;

        }catch (NotFoundException e){
            throw e;
        }
        catch (Exception e) {
            String message = MessageFormat.format("error while querying the database, clientId: {0}.",clientId);
            log.error(message , e);
            throw new InternalServerException(message, e);
        }
    }

    public Page<Client> listClients(String name, String cpf, Pageable pageable) {
        Page<ClientDTO> result;
        try {
            result = getResult(name, cpf, pageable);
            log.info("listing clients successfully with name : {}, cpf: {}", name, cpf);

        } catch (Exception e) {
            String message = MessageFormat.format("error while querying the database, name: {0}, cpf: {1}.", name, cpf);
            log.error(message , e);
            throw new InternalServerException(message, e);
        }
        List<Client> clients = result.stream().map(ClientDTO::toDomain).collect(Collectors.toList());
        return PageableExecutionUtils.getPage(clients, pageable, result::getTotalElements);
    }

    public Client create(Client client) {
        ClientDTO clientDTO;
        try {
            clientDTO = clientRepository.save(new ClientDTO(client));
            log.info("client created successfully, clientId: {}, cpf: {}",clientDTO.getId(), clientDTO.getCPF());
            return clientDTO.toDomain();
        }catch (Exception e) {
            String message = MessageFormat.format("error while saving client to database, clientId: {0}, cpf: {1}.", client.getId(), client.getCPF());
            log.error(message , e);
            throw new InternalServerException(message, e);
        }
    }

    @CacheEvict(value = "clients",key = "#client.getId()")
    public void update(Client client) throws NotFoundException {
        try {
            ClientDTO oldClientDTO = getClientDTOById(client.getId());
            oldClientDTO.update(client);
            clientRepository.save(oldClientDTO);
            log.info("client was updated successfully, clientId: {}", client.getId());

        }catch (NotFoundException e){
            throw e;
        } catch (Exception e) {
            String message = MessageFormat.format("error while updating client to database, clientId: {0}, cpf: {1}.", client.getId(), client.getCPF());
            log.error(message , e);
            throw new InternalServerException(message, e);
        }
    }

    @CacheEvict(value = "clients",key = "#client.getId()")
    public void patch(Client client) throws NotFoundException {
        try {
            ClientDTO oldClientDTO = getClientDTOById(client.getId());

            boolean changed = oldClientDTO.patch(client);
            if (changed) {
                clientRepository.save(oldClientDTO);
            }
            log.info("client was patched successfully, clientId: {}", client.getId());

        }catch (NotFoundException e){
            throw e;
        } catch (Exception e) {
            String message = MessageFormat.format("error while patching client to database, clientId: {0}, cpf: {1}.", client.getId(), client.getCPF());
            log.error(message , e);
            throw new InternalServerException(message, e);
        }

    }

    @CacheEvict(value = "clients",key = "#clientId")
    public void delete(Long clientId) throws NotFoundException {
        try {
            ClientDTO clientDTO = getClientDTOById(clientId);
            clientRepository.delete(clientDTO);

            log.info("client was deleted successfully, clientId: {}", clientId);

        }catch (NotFoundException e){
            throw e;
        } catch (Exception e) {
            String message = MessageFormat.format("error while deleting client from database, clientId: {0}.", clientId);
            log.error(message , e);
            throw new InternalServerException(message, e);
        }
    }

    private ClientDTO getClientDTOById(Long id) throws NotFoundException {
        Optional<ClientDTO> clientMySqlOpt = clientRepository.findById(id);

        return clientMySqlOpt.orElseThrow(() -> new NotFoundException(id));
    }

    private Page<ClientDTO> getResult(String name, String cpf, Pageable pageable) {

        name = name != null ? name.toLowerCase().trim() : null;
        cpf = cpf != null ? removeFormat(cpf) : null ;

        if (isNotBlank(name ) && isNotBlank( cpf)) {
            return clientRepository.findByNameContainingAndCPF(name, cpf, pageable);

        } else if (isNotBlank(name)) {
            return clientRepository.findByNameContaining(name, pageable);
        }

        return  clientRepository.findByCPF(cpf, pageable);
    }
}
