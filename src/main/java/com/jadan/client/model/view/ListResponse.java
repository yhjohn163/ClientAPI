package com.jadan.client.model.view;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jadan.client.model.domain.Client;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;


@Data
@JsonSerialize
public class ListResponse {

   private List<ClientResponse> clients;
   private String total;
   private String currentPage;
   private String pageSize;

    public ListResponse(Page<Client> clients) {
        this.clients = clients.stream().map(ClientResponse::new).collect(Collectors.toList());
        this.total =  String.valueOf(clients.getTotalElements());
        this.pageSize = String.valueOf(clients.getSize());
        this.currentPage = String.valueOf(clients.getNumber());
    }
}
