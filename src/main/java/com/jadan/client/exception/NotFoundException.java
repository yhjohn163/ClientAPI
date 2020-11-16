package com.jadan.client.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NotFoundException extends BaseException {

    public NotFoundException(Long clientId) {
        super("resource not found",404);
        log.warn("Client with Id [{}] was Not Found.", clientId);
    }

}
