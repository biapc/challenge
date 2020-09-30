package com.volanty.projetodesafio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("boot-application.xml")
public class GerenciamentoCavApplication {

	public static void main(String[] args) {
		SpringApplication.run(GerenciamentoCavApplication.class, args);
	}

}
