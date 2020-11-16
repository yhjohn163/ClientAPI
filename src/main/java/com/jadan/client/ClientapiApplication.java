package com.jadan.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ClientapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClientapiApplication.class, args);
	}

}
