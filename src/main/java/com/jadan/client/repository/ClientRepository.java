package com.jadan.client.repository;

import com.jadan.client.model.entity.ClientDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends PagingAndSortingRepository<ClientDTO,Long> {
    @Query()
    Page<ClientDTO> findByNameContaining(String name, Pageable pageable);
    Page<ClientDTO> findByCPF(String cpf, Pageable pageable);
    Page<ClientDTO> findByNameContainingAndCPF(String name, String cpf, Pageable pageable);
}
