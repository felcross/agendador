package com.projetozero;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.util.TimeZone;

@SpringBootApplication
@EnableFeignClients
public class AgendadorApplication {

	public static void main(String[] args) {
		SpringApplication.run(AgendadorApplication.class, args);
	}


	@PostConstruct
	public void init() {
		// Define o fuso horário padrão da aplicação para Brasília
		TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"));
	}
}
