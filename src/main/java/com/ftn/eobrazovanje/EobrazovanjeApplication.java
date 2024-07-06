package com.ftn.eobrazovanje;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.ftn.eobrazovanje.*")
@EntityScan("com.ftn.eobrazovanje.*")
public class EobrazovanjeApplication {

	public static void main(String[] args) {
		SpringApplication.run(EobrazovanjeApplication.class, args);
	}

}
