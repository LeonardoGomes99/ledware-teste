package com.sistemachamados;

import java.time.LocalDateTime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ApiSistemaChamadosApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiSistemaChamadosApplication.class, args);
	}
	
	@GetMapping("/")
	public static void index() {
		LocalDateTime myObj = LocalDateTime.now();
	    System.out.println(myObj);
	}
}
